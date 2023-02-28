package com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.dto.user;

import com.hcmute.project.instagram.backend.domain.base.SuccessfulResponse;

import java.util.HashMap;
import java.util.Map;

public class LoginResponse extends SuccessfulResponse {
    public LoginResponse(UserResponse userResponse, String accessToken) {
        super();
        Map<String, Object> result = new HashMap<>();

        result.put("user", userResponse);
        result.put("accessToken", accessToken);

        this.setData(result);
    }
}
