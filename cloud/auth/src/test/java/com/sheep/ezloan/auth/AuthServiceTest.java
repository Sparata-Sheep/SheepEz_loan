package com.sheep.ezloan.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sheep.ezloan.auth.api.controller.dto.request.UserLoginRequest;
import com.sheep.ezloan.auth.api.controller.dto.request.UserSignupRequest;
import com.sheep.ezloan.auth.domain.AuthService;
import com.sheep.ezloan.client.user.RoleType;
import com.sheep.ezloan.client.user.UserClient;
import com.sheep.ezloan.client.user.model.UserClientResult;

class AuthServiceTest {

    @Mock
    private UserClient userClient; // Feign 클라이언트 모킹

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // UserClientResult 생성자에서 요구하는 Long userId, String username, String password, RoleType
    // role을 맞춰서 전달
    // 예시에서는 임의의 userId 1L과 RoleType.USER를 사용

    @Test
    void 회원가입_성공_존재하지_않는_사용자명() {
        // given
        UserSignupRequest request = new UserSignupRequest();
        request.setUsername("newUser");
        request.setPassword("password123");

        // when: 존재하지 않는 사용자 이름일 때
        when(userClient.existsByUsername("newUser")).thenReturn(false); // existsByUsername
                                                                        // 사용
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");

        // FeignClient signup 호출 시 응답
        when(userClient.signup("newUser", "encodedPassword123"))
            .thenReturn(new UserClientResult(1L, "newUser", "encodedPassword123", RoleType.USER));

        // when: 서비스 호출
        String username = authService.signup(request);

        // then: 결과 검증
        assertEquals("newUser", username);

        // 각 메소드 호출 검증
        verify(userClient, times(1)).existsByUsername("newUser"); // existsByUsername 호출
                                                                  // 검증
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userClient, times(1)).signup("newUser", "encodedPassword123");
    }

    @Test
    void 로그인_성공() throws IllegalAccessException {
        // given
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testUser");
        request.setPassword("password123");

        // FeignClient에서 사용자 정보 조회
        UserClientResult userResult = new UserClientResult(1L, "testUser", "encodedPassword", RoleType.USER);
        when(userClient.findUserByUsername("testUser")).thenReturn(userResult);
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // when: 서비스 호출
        UserClientResult loginedUser = authService.login(request);

        // then: 검증
        assertNotNull(loginedUser);
        assertEquals("testUser", loginedUser.username());

        verify(userClient, times(1)).findUserByUsername("testUser");
        verify(passwordEncoder, times(1)).matches("password123", "encodedPassword");
    }

    @Test
    void 로그인_실패_비밀번호_불일치() {
        // given
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("testUser");
        request.setPassword("wrongPassword");

        // 실제 사용자와 비밀번호
        UserClientResult userResult = new UserClientResult(1L, "testUser", "encodedPassword", RoleType.USER);
        when(userClient.findUserByUsername("testUser")).thenReturn(userResult);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // then: 비밀번호 불일치 시 예외 발생 확인
        IllegalAccessException exception = assertThrows(IllegalAccessException.class, () -> {
            authService.login(request);
        });
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());

        verify(userClient, times(1)).findUserByUsername("testUser");
        verify(passwordEncoder, times(1)).matches("wrongPassword", "encodedPassword");
    }

}
