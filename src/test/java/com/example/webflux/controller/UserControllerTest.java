package com.example.webflux.controller;

import com.example.webflux.dto.UserCreateRequest;
import com.example.webflux.dto.UserResponse;
import com.example.webflux.repository.User;
import com.example.webflux.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(controllers = UserController.class)
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private UserService userService;

    @Test
    void createUser() {
        Mockito.when(userService.create("dddd", "ddd@gmail.com")).thenReturn(
                Mono.just(new User(1L,"dddd", "ddd@gmail.com", LocalDateTime.now(), LocalDateTime.now()))
        );

        client.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("dddd", "ddd@gmail.com"))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("dddd", res.getName());
                    assertEquals("ddd@gmail.com", res.getEmail());
                });
    }

    @Test
    void findAllUsers() {

        Mockito.when(userService.findAll()).thenReturn(
                Flux.just(
                        new User(1L,"dddd", "ddd@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
                        new User(2L,"dddd", "ddd@gmail.com", LocalDateTime.now(), LocalDateTime.now()),
                        new User(3L,"dddd", "ddd@gmail.com", LocalDateTime.now(), LocalDateTime.now())
                )
        );

        client.get().uri("/users")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(UserResponse.class);
    }

    @Test
    void findUserById() {

        Mockito.when(userService.findById(1L)).thenReturn(
                Mono.just(new User(1L,"dddd", "ddd@gmail.com", LocalDateTime.now(), LocalDateTime.now()))
        );

        client.get().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("dddd", res.getName());
                    assertEquals("ddd@gmail.com", res.getEmail());
                });
    }

    @Test
    void notFoundUserById() {

        Mockito.when(userService.findById(1L)).thenReturn(Mono.empty());

        client.get().uri("/users/1")
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void deleteUserById() {
        Mockito.when(userService.deleteById(1L)).thenReturn(Mono.empty());

        client.delete().uri("/users/1")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void updateUser() {
        Mockito.when(userService.update(1L,"dddd", "ddd@gmail.com")).thenReturn(
                Mono.just(new User(1L,"dddd", "ddd@gmail.com", LocalDateTime.now(), LocalDateTime.now()))
        );

        client.put().uri("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateRequest("dddd", "ddd@gmail.com"))
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserResponse.class)
                .value(res -> {
                    assertEquals("dddd", res.getName());
                    assertEquals("ddd@gmail.com", res.getEmail());
                });
    }
}