package com.sheep.ezloan;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheep.ezloan.user.api.dto.UserResponseDto;
import com.sheep.ezloan.user.domain.UserService;
import com.sheep.ezloan.user.storage.entity.RoleType;
import com.sheep.ezloan.user.storage.entity.UserEntity;
import com.sheep.ezloan.user.storage.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserJpaRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 유저정보_조회_성공() throws Exception {
        // given: 유저 엔티티 설정
        UserEntity user = UserEntity.builder().username("testUser").role(RoleType.USER).build();

        // Reflection을 사용해 userId 필드를 수동으로 설정
        setField(user, "userId", 1L);

        // when: findByUserIdAndIsDeleteFalse가 호출되면 유저 반환
        when(userRepository.findByUserIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(user));

        // when: 서비스 호출
        String userInfo = userService.getUserInfo(1L);

        // then: 결과 검증
        assertEquals("User ID: 1, Username: testUser, Role: USER", userInfo);

        // findByUserIdAndIsDeleteFalse가 호출되었는지 확인
        verify(userRepository, times(1)).findByUserIdAndIsDeletedFalse(1L);
    }

    @Test
    void 유저정보_조회_실패_존재하지_않는_유저() {
        // when: findByUserIdAndIsDeleteFalse가 호출되면 Optional.empty() 반환
        when(userRepository.findByUserIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty());

        // then: 유저가 없을 때 예외 발생 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserInfo(1L);
        });

        assertEquals("Not Found User or User is Deleted", exception.getMessage());

        // findByUserIdAndIsDeleteFalse가 호출되었는지 확인
        verify(userRepository, times(1)).findByUserIdAndIsDeletedFalse(1L);
    }

    @Test
    void 유저_삭제_성공() throws Exception {
        // given: 유저 엔티티 설정
        UserEntity user = UserEntity.builder()
            .username("testUser")
            .password("testPassword")
            .role(RoleType.USER)
            .build();

        // Reflection을 사용해 userId 필드를 수동으로 설정
        setField(user, "userId", 1L);

        // when: findByUserIdAndIsDeleteFalse가 호출되면 유저 반환
        when(userRepository.findByUserIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(user));

        // when: 서비스 호출
        String result = userService.deleteUser(1L);

        // then: 결과 검증
        assertEquals("User ID: 1 Soft Delete is complete", result);

        // findByUserIdAndIsDeleteFalse가 호출되었는지 확인
        verify(userRepository, times(1)).findByUserIdAndIsDeletedFalse(1L);
    }

    @Test
    void 유저_삭제_실패_존재하지_않는_유저() {
        // when: findByUserIdAndIsDeleteFalse가 호출되면 Optional.empty() 반환
        when(userRepository.findByUserIdAndIsDeletedFalse(1L)).thenReturn(Optional.empty());

        // then: 유저가 없을 때 예외 발생 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1L);
        });

        // 예외 메시지를 실제 코드와 일치시키기
        assertEquals("Not Found User or User is Deleted", exception.getMessage());

        // findByUserIdAndIsDeleteFalse가 호출되었는지 확인
        verify(userRepository, times(1)).findByUserIdAndIsDeletedFalse(1L);
    }

    @Test
    void 모든_사용자_조회_성공() throws Exception {
        // given: 유저 엔티티 리스트 설정
        UserEntity user1 = UserEntity.builder().username("user1").role(RoleType.USER).build();
        UserEntity user2 = UserEntity.builder().username("user2").role(RoleType.USER).build();

        setField(user1, "userId", 1L);
        setField(user2, "userId", 2L);

        List<UserEntity> users = List.of(user1, user2);

        // when: findAllByIsDeleteFalse가 호출되면 사용자 리스트 반환
        when(userRepository.findAllByIsDeletedFalse()).thenReturn(users);

        // when: 서비스 호출
        List<UserResponseDto> result = userService.getAllUsers();

        // then: 결과 검증
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).username());
        assertEquals("user2", result.get(1).username());

        // findAllByIsDeleteFalse가 호출되었는지 확인
        verify(userRepository, times(1)).findAllByIsDeletedFalse();
    }

    @Test
    void 사용자_권한_변경_성공() throws Exception {
        // given: 유저 엔티티 설정
        UserEntity user = UserEntity.builder().username("testUser").role(RoleType.USER).build();
        setField(user, "userId", 1L);

        // when: findByUserIdAndIsDeleteFalse가 호출되면 유저 반환
        when(userRepository.findByUserIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(user));

        // when: 서비스 호출
        String result = userService.changeUserRole(1L, RoleType.MASTER);

        // then: 결과 검증
        assertEquals("User ID: 1 Role changed to MASTER", result);
        assertEquals(RoleType.MASTER, user.getRole());

        // findByUserIdAndIsDeleteFalse가 호출되었는지 확인
        verify(userRepository, times(1)).findByUserIdAndIsDeletedFalse(1L);
    }

    @Test
    void 사용자_검색_성공() throws Exception {
        // given: 유저 엔티티 리스트 설정
        UserEntity user1 = UserEntity.builder().username("testUser1").role(RoleType.USER).build();
        UserEntity user2 = UserEntity.builder().username("testUser2").role(RoleType.USER).build();

        setField(user1, "userId", 1L);
        setField(user2, "userId", 2L);

        List<UserEntity> users = List.of(user1, user2);

        // when: findByUsernameContainingAndIsDeleteFalse가 호출되면 사용자 리스트 반환
        when(userRepository.findByUsernameContainingAndIsDeletedFalse("testUser")).thenReturn(users);

        // when: 서비스 호출
        List<UserResponseDto> result = userService.searchUsers("testUser");

        // then: 결과 검증
        assertEquals(2, result.size());
        assertEquals("testUser1", result.get(0).username());
        assertEquals("testUser2", result.get(1).username());

        // findByUsernameContainingAndIsDeleteFalse가 호출되었는지 확인
        verify(userRepository, times(1)).findByUsernameContainingAndIsDeletedFalse("testUser");
    }

    // Reflection을 사용해 필드를 설정하는 메서드
    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    // Reflection을 사용해 필드를 가져오는 메서드
    private Object getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

}
