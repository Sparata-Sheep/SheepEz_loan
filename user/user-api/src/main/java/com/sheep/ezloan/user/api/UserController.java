package com.sheep.ezloan.user.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sheep.ezloan.support.authentication.security.SecurityUtil;
import com.sheep.ezloan.user.api.dto.UserResponseDto;
import com.sheep.ezloan.user.domain.UserService;
import com.sheep.ezloan.user.storage.entity.RoleType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String getUserInfo() {
        Long userId = SecurityUtil.getCurrentUserId();
        return userService.getUserInfo(userId);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public String deleteUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        return userService.deleteUser(userId);
    }

    // 관리자 전용: 모든 사용자 조회
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // 관리자 전용: 사용자 권한 변경
    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/{userId}/role")
    public String changeUserRole(@PathVariable Long userId, @RequestBody RoleType newRole) {
        return userService.changeUserRole(userId, newRole);
    }

    // 관리자 전용: 사용자 검색
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/search")
    public List<UserResponseDto> searchUsers(@RequestParam String username) {
        return userService.searchUsers(username);
    }

}
