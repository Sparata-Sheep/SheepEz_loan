package com.sheep.ezloan.auth.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Object> health() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/api/v1/health/role")
    public ResponseEntity<Object> healthRole(@RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-Role") String role) {
        System.out.println("User ID: " + userId);
        System.out.println("Role: " + role);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
