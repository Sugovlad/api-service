package com.realo.apiservice.model;


import java.util.Set;

public record Project(Long id, String projectName, int status, Set<User> assignedUsers) {
}
