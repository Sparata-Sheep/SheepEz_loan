package com.sheep.ezloan.client.user;

import org.springframework.stereotype.Component;

import com.sheep.ezloan.client.user.model.UserClientResult;

@Component
public class UserClient {

    private final UserApi userApi;

    public UserClient(UserApi userApi) {
        this.userApi = userApi;
    }

    // 회원가입 요청을 처리하고 UserModel로 변환
    public UserClientResult signup(String username, String rawPassword) {
        UserRequestDto request = new UserRequestDto(username, rawPassword);
        UserResponseDto user = userApi.createUser(request);
        return toUserModel(user);
    }

    // 사용자 정보를 조회하고 UserModel로 변환
    public UserClientResult findUserByUsername(String username) {
        UserResponseDto user = userApi.findUserByUsername(username);
        return toUserModel(user);
    }

    // User를 UserModel로 변환하는 메소드
    private UserClientResult toUserModel(UserResponseDto user) {
        return new UserClientResult(user.userId(), user.username(), user.password(), user.role());
    }

    public boolean existsByUsername(String username) {
        return userApi.existsByUsername(username);
    }

}
