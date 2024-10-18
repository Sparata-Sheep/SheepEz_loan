package com.sheep.ezloan.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheep.ezloan.user.api.dto.UserRequestDto;
import com.sheep.ezloan.user.api.dto.UserResponseDto;
import com.sheep.ezloan.user.domain.UserService;
import com.sheep.ezloan.user.storage.entity.RoleType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feign/users")
public class ClientController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto request) {
        UserResponseDto userResponse = userService.createUser(request);
        return ResponseEntity.ok(userResponse);
    }

    // 사용자명으로 사용자 조회
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> findUserByUsername(@PathVariable(name = "username") String username) {
        UserResponseDto userResponse = userService.findUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/exist/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @PatchMapping("approve/{userId}")
    public String approveLawyer(@PathVariable Long userId) {
        return userService.changeUserRole(userId, RoleType.LAWYER);
    }

}
