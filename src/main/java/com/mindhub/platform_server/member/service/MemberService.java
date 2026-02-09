package com.mindhub.platform_server.member.service;

import com.mindhub.platform_server.member.domain.Member;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MemberService {
    private final Map<Long, Member> repository = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    public Member register(String email, String name) {
        Long id = sequence.getAndIncrement();
        Member member = Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .createdAt(LocalDateTime.now())
                .build();
        repository.put(id, member);
        return member;
    }

    public List<Member> findAll() {
        return new ArrayList<>(repository.values());
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    public Member update(Long id, String name) {
        Member member = repository.get(id);
        if (member != null) {
            member.setName(name);
            repository.put(id, member);
        }
        return member;
    }

    public void delete(Long id) {
        repository.remove(id);
    }
}
