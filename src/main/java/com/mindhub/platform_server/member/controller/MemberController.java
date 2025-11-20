package com.mindhub.platform_server.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @PostMapping("/register")
    public String register() {
        // 회원 가입 로직 (Mock)
        return "회원가입 성공!";
    }

    @GetMapping("/error-test")
    public void triggerError() {
        throw new RuntimeException("테스트용 500 에러 발생!");
    }
}
