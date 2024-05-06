package com.example.webflux.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserR2dbcRepository extends ReactiveCrudRepository<User, Long> {

    Flux<User> findByName(String name);

    //<User> findByNameOrderByDesc(String name);

    @Modifying
    @Query("DELETE FROM users WHERE name = :name")
    Mono<Void> deleteByName(String Name);
}
