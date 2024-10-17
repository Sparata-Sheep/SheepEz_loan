package com.sheep.ezloan.contact.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/feign/users/exist/{username}")
    Boolean existsByUsername(@PathVariable("username") String username);

}
