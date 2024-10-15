package com.sheep.ezloan.contact.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-api", url = "${user.api.url}")
public interface UserClient {

    @GetMapping("/feign/users/exist/{username}")
    Boolean existsByUsername(@PathVariable("username") String username);

}
