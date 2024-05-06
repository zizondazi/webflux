package com.example.webflux.controller;

import com.example.webflux.dto.PostResponse;
import com.example.webflux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public Mono<PostResponse> getPostById(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/search")
    public Flux<PostResponse> getMultiplePostContent(@RequestParam(name = "ids")List<Long> ids) {
        return postService.getMultiplePostContent(ids);
    }
}
