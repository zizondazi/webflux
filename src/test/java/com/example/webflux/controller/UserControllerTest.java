package com.example.webflux.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(controllers = UserController.class)
@AutoConfigureWebTestClient
class UserControllerTest {
    private WebTestClient client;

    @Test
    void createUser() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void updateUser() {
    }
}