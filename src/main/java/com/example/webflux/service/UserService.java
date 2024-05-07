package com.example.webflux.service;

import com.example.webflux.repository.User;
import com.example.webflux.repository.UserR2dbcRepository;
import com.example.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserR2dbcRepository userR2dbcRepository;
    private final ReactiveRedisTemplate<String, User> redisTemplate;

    public Mono<User> create(String name, String email) {
        return userR2dbcRepository.save(User.builder().name(name).email(email).build());
    }

    public Flux<User> findAll() {
        return userR2dbcRepository.findAll();
    }

    public Mono<User> findById(Long id) {
        return redisTemplate.opsForValue()
                .get("user:%d".formatted(id))
                .switchIfEmpty(userR2dbcRepository.findById(id)
                        .flatMap(user -> redisTemplate.opsForValue()
                                .set("user:%d".formatted(id), user, Duration.ofSeconds(30))
                                .then(Mono.just(user))
                        )
                );
    }

    public Mono<Void> deleteById(Long id) {
        return userR2dbcRepository.deleteById(id)
                .then(redisTemplate.unlink("user:%d".formatted(id)))
                .then(Mono.empty());
    }

    public Mono<Void> deleteByName(String name) {
        return userR2dbcRepository.deleteByName(name);
    }

    public Mono<User> update(Long id, String name, String email) {
        return userR2dbcRepository.findById(id)
                .flatMap(u -> {
                    u.setName(name);
                    u.setEmail(email);
                    return userR2dbcRepository.save(u);
                })
                .flatMap(u -> redisTemplate.unlink("user:%d".formatted(id)).then(Mono.just(u)));
    }
}
