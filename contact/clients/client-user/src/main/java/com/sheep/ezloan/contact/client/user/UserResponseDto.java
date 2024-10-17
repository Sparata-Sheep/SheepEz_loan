package com.sheep.ezloan.contact.client.user;

public record UserResponseDto(Long userId, String username, String password, RoleType role) {

}
