package com.sheep.ezloan.auth.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sheep.ezloan.auth.api.controller.dto.request.UserLoginRequest;
import com.sheep.ezloan.auth.api.controller.dto.request.UserSignupRequest;
import com.sheep.ezloan.client.user.UserClient;
import com.sheep.ezloan.client.user.model.UserClientResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserClient userClient;

    private final PasswordEncoder passwordEncoder;

    public String signup(UserSignupRequest request) {
        validateUsername(request.getUsername());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userClient.signup(request.getUsername(), encodedPassword);
        return request.getUsername();
    }

    public UserClientResult login(UserLoginRequest request) throws IllegalAccessException {
        UserClientResult userModel = userClient.findUserByUsername(request.getUsername());
        verifyPassword(request.getPassword(), userModel.password());
        return userModel;
    }

    private void validateUsername(String username) {
        if (userClient.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
    }

    private void verifyPassword(String rawPassword, String encodedPassword) throws IllegalAccessException {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
    }

}
