package com.example.webflux.service;

import com.example.webflux.client.PostClient;
import com.example.webflux.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostClient postClient;

    public Mono<PostResponse> getPost(Long id) {
        return postClient.getPost(id)
                .onErrorResume(error -> Mono.just(new PostResponse(id.toString(),  "Callback data %d".formatted(id.toString()))));
    }

    public Flux<PostResponse> getMultiplePostContent(List<Long> ids) {
        return Flux.fromIterable(ids)
                .flatMap(this::getPost)
                .log();
    }

    public Flux<PostResponse> getParallelMultiplePostContent(List<Long> ids) {
        return Flux.fromIterable(ids)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::getPost)
                .log()
                .sequential();
    }

}
