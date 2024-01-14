package com.realo.apiservice.services;

import com.realo.apiservice.exception.NotSavedException;
import com.realo.apiservice.models.User;
import com.realo.apiservice.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> save(User user) {
        return userRepository.save(user)
                .onErrorMap(originalException ->
                        new NotSavedException("Failed to save project. Details: " + originalException.getMessage(), originalException)
                );
    }

    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    public Mono<User> getById(Long id) {
        return userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Can't found project id: " + id)));
    }

    public Mono<Void> delete(Long id) {
        return userRepository.deleteById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Project not found")));
    }


    public Mono<User> update(Long id, User updatedUser) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User Not found")))
                .map(user -> {
                    user.setFullName(updatedUser.getFullName());
                    user.setLoginName(updatedUser.getLoginName());
                    user.setPassword(updatedUser.getPassword());

                    return user;
                })
                .flatMap(this::save);
    }

}

