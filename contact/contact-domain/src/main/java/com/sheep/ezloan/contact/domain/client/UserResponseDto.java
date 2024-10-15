package com.sheep.ezloan.contact.domain.client;

public record UserResponseDto(Long userId, String username, String password, RoleType role) {

}
