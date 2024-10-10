package com.sheep.ezloan.auth.api.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheep.ezloan.auth.api.config.jwt.JwtUtil;
import com.sheep.ezloan.auth.api.controller.dto.request.UserLoginRequest;
import com.sheep.ezloan.auth.api.controller.dto.request.UserSignupRequest;
import com.sheep.ezloan.auth.domain.AuthService;
import com.sheep.ezloan.client.user.model.UserClientResult;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    // 추후 Auth 서비스로 분리

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid UserSignupRequest request) {
        System.out.println("working");
        return ResponseEntity.ok(authService.signup(request) + " registered successfully");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserLoginRequest request, HttpServletResponse response)
            throws IllegalAccessException {

        UserClientResult loginedUser = authService.login(request);
        String token = jwtUtil.createToken(loginedUser.id(), loginedUser.username(), loginedUser.role());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        return ResponseEntity.ok().body(loginedUser.role().toString());
    }

    // 토큰 검증
    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // "Bearer " 부분 제거
            String jwtToken = token.substring(7);

            // JWT 토큰을 검증하고 Claims 반환
            Claims claims = jwtUtil.extractClaims(jwtToken);

            // Claims 내용을 Map으로 변환하여 반환
            Map<String, Object> response = new HashMap<>();
            response.put("sub", claims.getSubject());
            response.put("username", claims.get("username"));
            response.put("role", claims.get("role"));
            response.put("exp", claims.getExpiration());
            response.put("iat", claims.getIssuedAt());

            // 검증된 Claims 반환
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            // 검증 실패 시 401 Unauthorized 반환
            return ResponseEntity.status(401).build();
        }
    }

}