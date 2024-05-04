package com.example.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

    @GetMapping("/sample/hellow")
    public Mono<String> getHellow() {
        return Mono.just("Hello World");
    }
}
