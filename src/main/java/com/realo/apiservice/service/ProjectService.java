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
                .retrieve()
                .bodyToMono(Project.class);
    }

    public Flux<Project> getAll() {
        return webClient.get()
                .uri("project")
                .retrieve()
                .bodyToFlux(Project.class);
    }

    public Mono<Project> getById(Long id) {
        return webClient.get()
                .uri("project/{id}", id)
                .retrieve()
                .bodyToMono(Project.class);
    }

    public Mono<Project> update(Long id, Project updatedProject) {
        return webClient.put()
                .uri("project", id)
                .body(Mono.just(updatedProject), Project.class)
                .retrieve()
                .bodyToMono(Project.class);
    }

    public Mono<Void> delete(Long id) {
        return webClient.delete()
                .uri("project/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Project> addUser(Long projectId, Long userId) {
        return webClient.put()
                .uri("project/{projectId}/add-user/{userId}", projectId, userId)
                .retrieve()
                .bodyToMono(Project.class);
    }

    public Mono<Project> removeUser(Long projectId, Long userId) {
        return webClient.put()
                .uri("project/{projectId}/remove-user/{userId}", projectId, userId)
                .retrieve()

                .bodyToMono(Project.class);
    }
}

