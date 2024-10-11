package com.sheep.ezloan.user.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleBasedController {

    /*
     * 권한 사용 방법 예시 코드입니다.
     *
     * ROLE_MASTER > ROLE_USER
     *
     * ROLE_MASTER > ROLE_LAWYER
     *
     */

    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/master")
    public String manageSystem() {
        return "시스템 관리 페이지 (MASTER)";
    }

    @PreAuthorize("hasRole('LAWYER')")
    @GetMapping("/lawyer")
    public String manageHub() {
        return "변호사 페이지 (LAWYER)";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String manageDelivery() {
        return "유저 페이지 (USER)";
    }

}
