package com.realo.apiservice.service;

import com.realo.apiservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {
    private final WebClient webClient;

    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<User> save(User user) {
        return webClient.post()
                .uri("user")
                .body(Mono.just(user), User.class)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()){
                        return  response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(User.class);
                });
    }

    public Flux<User> getAll() {
        return webClient.get()
                .uri("user")
                .exchangeToFlux((response) -> {
                    if (response.statusCode().isError()){
                        return response.bodyToFlux(String.class)
                                .flatMap(errorBody -> Flux.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToFlux(User.class);
                });
    }

    public Mono<User> getById(Long id) {
        return webClient.get()
                .uri("user/{id}", id)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()){
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(User.class);
                });
    }

    public Mono<Void> delete(Long id) {
        return webClient.delete()
                .uri("user/{id}", id)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()){
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(Void.class);
                });
    }


    public Mono<User> update(Long id, User updatedUser) {
        return webClient.put()
                .uri("user/{id}", id)
                .body(Mono.just(updatedUser), User.class)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()){
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(User.class);
                });
    }
}

