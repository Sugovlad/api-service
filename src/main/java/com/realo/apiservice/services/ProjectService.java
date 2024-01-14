package com.realo.apiservice.services;

import com.realo.apiservice.exception.NotSavedException;
import com.realo.apiservice.models.Project;
import com.realo.apiservice.models.User;
import com.realo.apiservice.repository.ProjectRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public Mono<Project> save(Project project) {
        return projectRepository.save(project)
                .onErrorMap(originalException ->
                        new NotSavedException("Failed to save project. Details: " + originalException.getMessage(), originalException)
                );
    }

    public Flux<Project> getAll() {
        return projectRepository.findAll();
    }

    public Mono<Project> getById(Long id) {
        return projectRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Can't found project id: " + id)));
    }

    public Mono<Project> update(Long id, Project updatedProject) {
        return projectRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found")))
                .map(existedProject -> {
                    existedProject.setProjectName(updatedProject.getProjectName());
                    existedProject.setStatus(updatedProject.getStatus());

                    return existedProject;
                })
                .flatMap(projectRepository::save).onErrorMap(originalException ->
                        new NotSavedException("Failed to save project. Details: " + originalException.getMessage(), originalException)
                );
    }

    public Mono<Void> delete(Long id) {
        return projectRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found")));
    }

    public Mono<Project> addUser(Long projectId, Long userId) {
        var userMono = userService.getById(userId);
        var projectMono = getById(projectId);

        return Mono
                .zip(projectMono, userMono)
                .flatMap(tuple -> addUser(tuple.getT1(), tuple.getT2()));
    }

    private Mono<Project> addUser(Project project, User user) {
        if (project.getAssignedUsers().contains(user)) {
            return Mono.just(project);
        }

        project.getAssignedUsers().add(user);

        return save(project);
    }

    public Mono<Project> removeUser(Long projectId, Long userId) {
        var userMono = userService.getById(userId);
        var projectMono = getById(projectId);

        return Mono
                .zip(projectMono, userMono)
                .flatMap(tuple -> removeUser(tuple.getT1(), tuple.getT2()));
    }

    private Mono<Project> removeUser(Project project, User user) {
        if (!project.getAssignedUsers().contains(user)) {
            return Mono.just(project);
        }

        project.getAssignedUsers().remove(user);

        return save(project);
    }
}
