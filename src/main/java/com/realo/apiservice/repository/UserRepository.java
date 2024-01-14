package com.realo.apiservice.repository;

import com.realo.apiservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}