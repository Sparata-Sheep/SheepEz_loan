package com.sheep.ezloan.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sheep.ezloan.auth.api.config.jwt.JwtUtil;
import com.sheep.ezloan.auth.api.controller.AuthController;

import io.jsonwebtoken.Claims;

public class AuthControllerTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private Claims mockClaims; // Claims 객체 Mock

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateToken_ValidToken_ReturnsClaims() {
        // Arrange
        String token = "Bearer valid-token";

        // Mock Claims 객체에 필요한 값 설정
        when(mockClaims.getSubject()).thenReturn("1");
        when(mockClaims.get("username")).thenReturn("testuser");
        when(mockClaims.get("role")).thenReturn("USER");
        when(mockClaims.getExpiration()).thenReturn(new Date(1726193501L * 1000)); // 만료
                                                                                   // 시간
        when(mockClaims.getIssuedAt()).thenReturn(new Date(1726189901L * 1000)); // 발급 시간

        when(jwtUtil.extractClaims(anyString())).thenReturn(mockClaims);

        // Act
        ResponseEntity<?> response = authController.validateToken(token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", (((Map<String, Object>) response.getBody()).get("sub")));
        assertEquals("testuser", (((Map<String, Object>) response.getBody()).get("username")));
        assertEquals("USER", (((Map<String, Object>) response.getBody()).get("role")));
        verify(jwtUtil, times(1)).extractClaims("valid-token");
    }

    @Test
    void validateToken_InvalidToken_ReturnsUnauthorized() {
        // Arrange
        String token = "Bearer invalid-token";
        when(jwtUtil.extractClaims(anyString())).thenThrow(new RuntimeException("Invalid Token"));

        // Act
        ResponseEntity<?> response = authController.validateToken(token);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(jwtUtil, times(1)).extractClaims("invalid-token");
    }

}
