package com.mindhub.platform_server.common.notification;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
@RequiredArgsConstructor
public class DiscordNotificationAspect {

    private final DiscordWebhookClient discordWebhookClient;

    @AfterReturning(pointcut = "execution(* com.mindhub.platform_server.member.controller.MemberController.register(..)) && args(request)", argNames = "request")
    public void notifyMemberRegistration(
            com.mindhub.platform_server.member.controller.MemberController.MemberRegistrationRequest request) {
        String message = String.format(
                "🎉 **[새로운 회원 가입]**\n- 가입 시간: %s\n- 이름: %s\n- 이메일: %s\n- 내용: MindfulnessPlatform에 새로운 가족이 생겼습니다!",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getName(),
                request.getEmail());
        discordWebhookClient.sendNotification(message);
    }
}
