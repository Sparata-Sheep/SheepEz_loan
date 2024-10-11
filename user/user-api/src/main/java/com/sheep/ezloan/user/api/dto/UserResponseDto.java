package com.sheep.ezloan.user.api.dto;

import com.sheep.ezloan.user.storage.entity.RoleType;

public record UserResponseDto(Long userId, String username, String password, RoleType role) {

}
