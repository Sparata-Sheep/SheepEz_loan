package com.sheep.ezloan.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-api", url = "${user.api.url}")
public interface UserApi {

    @PostMapping("/feign/users")
    UserResponseDto createUser(@RequestBody UserRequestDto request);

    @GetMapping("/feign/users/{username}")
    UserResponseDto findUserByUsername(@PathVariable("username") String username);

    @GetMapping("/feign/users/exist/{username}")
    Boolean existsByUsername(@PathVariable("username") String username);

}
