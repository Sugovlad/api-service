package com.realo.apiservice.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record User(

        Long id,

        @NotBlank(message = "Field cannot be blank")
        @Size(min = 3, max = 50, message = "Field must be between 3 and 50 characters")
        String fullName,

        @NotBlank(message = "Field cannot be blank")
        @Size(min = 3, max = 50, message = "Field must be between 3 and 50 characters")
        String loginName,

        @NotBlank(message = "Field cannot be blank")
        @Size(min = 6, max = 16, message = "Field must be between 6 and 16 characters")
        String password) {
}


