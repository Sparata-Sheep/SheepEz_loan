package com.sheep.ezloan.support.redis.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    // 값을 Redis에 저장하는 메서드
    void setValue(String key, Object value);

    // 값과 함께 TTL(만료시간)을 설정해서 저장하는 메서드
    void setValueWithExpiry(String key, Object value, long timeout, TimeUnit unit);

    // Redis에서 값을 가져오는 메서드
    Object getValue(String key);

    // Redis에서 키를 삭제하는 메서드
    void deleteValue(String key);

    // Redis에서 특정 키의 TTL(남은 만료시간)을 조회하는 메서드
    Long getExpire(String key);

    // 특정 키의 만료시간을 갱신하는 메서드
    Boolean setExpire(String key, long timeout, TimeUnit unit);

    // Redis에 키가 존재하는지 확인하는 메서드
    Boolean hasKey(String key);

}
