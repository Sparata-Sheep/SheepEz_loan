package com.sheep.ezloan.support.redis;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.sheep.ezloan.support.redis.service.RedisServiceImpl;

public class RedisServiceImplTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisServiceImpl redisService;

    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);

        // RedisTemplate이 valueOperations를 반환하도록 설정
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void 값_저장_테스트() {
        String key = "testKey";
        String value = "testValue";

        // 메서드 실행
        redisService.setValue(key, value);

        // set 메서드가 한 번 호출되었는지 확인
        verify(valueOperations, times(1)).set(key, value);
    }

    @Test
    void 값_조회_테스트() {
        String key = "testKey";
        String expectedValue = "testValue";

        // Redis에서 해당 키로 값을 가져올 때 expectedValue가 반환되도록 설정
        when(valueOperations.get(key)).thenReturn(expectedValue);

        // 메서드 실행
        Object result = redisService.getValue(key);

        // 결과가 expectedValue와 같은지 확인
        assertEquals(expectedValue, result);

        // get 메서드가 한 번 호출되었는지 확인
        verify(valueOperations, times(1)).get(key);
    }

    @Test
    void 값_삭제_테스트() {
        String key = "testKey";

        // 메서드 실행
        redisService.deleteValue(key);

        // delete 메서드가 한 번 호출되었는지 확인
        verify(redisTemplate, times(1)).delete(key);
    }

    @Test
    void 키_존재_여부_확인_테스트() {
        String key = "testKey";
        Boolean expectedValue = true;

        // Redis에 해당 키가 있는지 확인할 때 true 반환되도록 설정
        when(redisTemplate.hasKey(key)).thenReturn(expectedValue);

        // 메서드 실행
        Boolean result = redisService.hasKey(key);

        // 결과가 true인지 확인
        assertEquals(expectedValue, result);

        // hasKey 메서드가 한 번 호출되었는지 확인
        verify(redisTemplate, times(1)).hasKey(key);
    }

    @Test
    void 만료시간과_함께_값_저장_테스트() {
        String key = "testKey";
        String value = "testValue";
        long timeout = 60L; // 60초
        TimeUnit unit = TimeUnit.SECONDS;

        // 메서드 실행
        redisService.setValueWithExpiry(key, value, timeout, unit);

        // set 메서드가 timeout과 함께 호출되었는지 확인
        verify(valueOperations, times(1)).set(key, value, timeout, unit);
    }

    @Test
    void 키_만료시간_조회_테스트() {
        String key = "testKey";
        Long expectedExpireTime = 120L; // 남은 TTL 시간 120초

        // Redis에서 TTL이 120초 남아 있다고 가정
        when(redisTemplate.getExpire(key)).thenReturn(expectedExpireTime);

        // 메서드 실행
        Long result = redisService.getExpire(key);

        // 결과가 예상한 TTL 값인지 확인
        assertEquals(expectedExpireTime, result);

        // getExpire 메서드가 한 번 호출되었는지 확인
        verify(redisTemplate, times(1)).getExpire(key);
    }

    @Test
    void 키_만료시간_설정_테스트() {
        String key = "testKey";
        long timeout = 120L; // 120초
        TimeUnit unit = TimeUnit.SECONDS;

        // Redis에서 TTL 설정이 성공적으로 되었다고 가정 (true 반환)
        when(redisTemplate.expire(key, timeout, unit)).thenReturn(true);

        // 메서드 실행
        Boolean result = redisService.setExpire(key, timeout, unit);

        // 결과가 true인지 확인
        assertTrue(result);

        // expire 메서드가 한 번 호출되었는지 확인
        verify(redisTemplate, times(1)).expire(key, timeout, unit);
    }

}
