package com.sheep.ezloan.client.user.model;

import com.sheep.ezloan.client.user.RoleType;

public record UserClientResult(Long id, String username, String password, RoleType role) {

}
