package com.sheep.ezloan.user.domain;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sheep.ezloan.user.api.dto.UserRequestDto;
import com.sheep.ezloan.user.api.dto.UserResponseDto;
import com.sheep.ezloan.user.storage.entity.RoleType;
import com.sheep.ezloan.user.storage.entity.UserEntity;
import com.sheep.ezloan.user.storage.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userRepository;

    // 회원가입 비즈니스 로직 처리 (이미 암호화된 비밀번호를 저장)
    public UserResponseDto createUser(UserRequestDto request) {
        // 암호화된 비밀번호를 그대로 사용
        UserEntity user = new UserEntity(request.username(), request.password(), RoleType.USER);
        userRepository.save(user);

        // 응답 객체로 변환하여 반환
        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public String getUserInfo(Long userId) {
        UserEntity user = getUserFromId(userId);
        return "User ID: " + user.getUserId() + ", Username: " + user.getUsername() + ", Role: " + user.getRole();

    }

    // 사용자명으로 사용자 조회
    public UserResponseDto findUserByUsername(String username) {
        // 사용자 조회
        UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 응답 객체로 변환하여 반환 (암호화된 비밀번호 포함)
        return new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public String deleteUser(Long userId) {
        UserEntity user = getUserFromId(userId);
        user.delete();
        return "User ID: " + user.getUserId() + " Soft Delete is complete";
    }

    // 관리자 전용: 모든 사용자 조회
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAllByIsDeletedFalse();
        return users.stream()
            .map(user -> new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole()))
            .collect(Collectors.toList());
    }

    // 관리자 전용: 사용자 권한 변경
    @Transactional
    public String changeUserRole(Long userId, RoleType newRole) {
        UserEntity user = getUserFromId(userId);
        user.setRole(newRole);
        return "User ID: " + user.getUserId() + " Role changed to " + newRole;
    }

    // 관리자 전용: 사용자 검색
    public List<UserResponseDto> searchUsers(String username) {
        List<UserEntity> users = userRepository.findByUsernameContainingAndIsDeletedFalse(username);
        return users.stream()
            .map(user -> new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole()))
            .collect(Collectors.toList());
    }

    private UserEntity getUserFromId(Long userId) {
        return userRepository.findByUserIdAndIsDeletedFalse(userId)
            .orElseThrow(() -> new IllegalArgumentException("Not Found User or User is Deleted"));
    }

}
