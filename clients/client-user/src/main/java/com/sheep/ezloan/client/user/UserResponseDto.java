package com.sheep.ezloan.client.user;

public record UserResponseDto(Long userId, String username, String password, RoleType role) {

}
