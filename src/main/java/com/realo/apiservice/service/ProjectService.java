package com.realo.apiservice.service;

import com.realo.apiservice.model.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProjectService {
    private final WebClient webClient;

    public ProjectService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Project> save(Project project) {
        return webClient.post()
                .uri("project")
                .body(Mono.just(project), Project.class)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(Project.class);
                });
    }

    public Flux<Project> getAll() {
        return webClient.get()
                .uri("project")
                .exchangeToFlux((response) -> {
                    if (response.statusCode().isError()) {
                        return response.bodyToFlux(String.class)
                                .flatMap(errorBody -> Flux.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToFlux(Project.class);
                });
    }

    public Mono<Project> getById(Long id) {
        return webClient.get()
                .uri("project/{id}", id)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));

                    }

                    return response.bodyToMono(Project.class);
                });
    }

    public Mono<Project> update(Long id, Project updatedProject) {
        return webClient.put()
                .uri("project", id)
                .body(Mono.just(updatedProject), Project.class)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));

                    }return response.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                });
    }

    public Mono<Void> delete(Long id) {
        return webClient.delete()
                .uri("project/{id}", id)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()) {

                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(Void.class);
                });
    }

    public Mono<Project> addUser(Long projectId, Long userId) {
        return webClient.put()
                .uri("project/{projectId}/add-user/{userId}", projectId, userId)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()) {
                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(Project.class);
                });
    }

    public Mono<Project> removeUser(Long projectId, Long userId) {
        return webClient.put()
                .uri("project/{projectId}/remove-user/{userId}", projectId, userId)
                .exchangeToMono((response) -> {
                    if (response.statusCode().isError()) {

                        return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody)));
                    }

                    return response.bodyToMono(Project.class);
                });
    }
}
