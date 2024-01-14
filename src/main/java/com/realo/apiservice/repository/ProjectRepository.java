package com.realo.apiservice.repository;

import com.realo.apiservice.models.Project;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProjectRepository extends ReactiveCrudRepository<Project, Long> {

}