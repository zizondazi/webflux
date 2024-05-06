package com.example.webflux.repository;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private final UserRepository repository = new UserRepositoryImpl();

    @Test
    void save() {
        var user = User.builder().name("ggg").email("ggg@gmail.com").build();
        StepVerifier.create(repository.save(user))
                .assertNext(u -> {
                    assertEquals("ggg", u.getName());
                    assertEquals("ggg@gmail.com", u.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void findAll() {
        repository.save(User.builder().name("ggg").email("ggg@gmail.com").build());
        repository.save(User.builder().name("ggg2").email("ggg2@gmail.com").build());
        repository.save(User.builder().name("ggg3").email("ggg3@gmail.com").build());

        StepVerifier.create(repository.findAll())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        repository.save(User.builder().name("ggg").email("ggg@gmail.com").build());

        StepVerifier.create(repository.findById(1L))
                .assertNext(u -> {
                    assertEquals("ggg", u.getName());
                    assertEquals("ggg@gmail.com", u.getEmail());
                })
                .verifyComplete();
    }

    @Test
    void deleteById() {
        repository.save(User.builder().name("ggg").email("ggg@gmail.com").build());
        repository.save(User.builder().name("ggg2").email("ggg2@gmail.com").build());
        repository.save(User.builder().name("ggg3").email("ggg3@gmail.com").build());

        StepVerifier.create(repository.deleteById(1L))
                .expectNextCount(1)
                .verifyComplete();
    }
}