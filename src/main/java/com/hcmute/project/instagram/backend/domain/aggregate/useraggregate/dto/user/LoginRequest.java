package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
