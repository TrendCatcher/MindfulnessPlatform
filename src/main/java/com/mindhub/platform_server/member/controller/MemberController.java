package com.mindhub.platform_server.member.controller;

import com.mindhub.platform_server.member.domain.Member;
import com.mindhub.platform_server.member.service.MemberService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public Member register(@RequestBody MemberRegistrationRequest request) {
        return memberService.register(request.getEmail(), request.getName());
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @PutMapping("/{id}")
    public Member updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequest request) {
        return memberService.update(id, request.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.delete(id);
    }

    @Data
    @NoArgsConstructor
    public static class MemberRegistrationRequest {
        private String email;
        private String name;
    }

    @Data
    @NoArgsConstructor
    public static class MemberUpdateRequest {
        private String name;
    }
}
