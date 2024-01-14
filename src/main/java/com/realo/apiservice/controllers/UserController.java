package com.realo.apiservice.controllers;


import com.realo.apiservice.models.User;
import com.realo.apiservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/apiservice/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createProject(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public Flux<User> getAllProjects() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<User> getProjectById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public Mono<User> updateProject(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProject(@PathVariable Long id) {
        return userService.delete(id);
    }
}
