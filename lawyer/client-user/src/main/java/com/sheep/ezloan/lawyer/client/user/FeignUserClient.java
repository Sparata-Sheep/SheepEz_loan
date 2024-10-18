package com.sheep.ezloan.lawyer.client.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service", url = "${user.api.url}")
public interface FeignUserClient {

    @PatchMapping("/feign/users/approve/{userId}")
    String approveLawyer(@PathVariable Long userId);

}
