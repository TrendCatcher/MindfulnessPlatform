package com.mindhub.platform_server.common.exception;

import com.mindhub.platform_server.common.notification.DiscordWebhookClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final DiscordWebhookClient discordWebhookClient;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        log.error("서버 내부 장애 발생: ", e);

        String errorMessage = String.format(
                "🚨 **[서버 장애 알림]**\n- 발생 시간: %s\n- 에러 메시지: %s\n- 상세 내용: %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                e.getClass().getSimpleName(),
                e.getMessage());

        discordWebhookClient.sendNotification(errorMessage);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 내부 장애가 발생했습니다. 담당자에게 알림이 전송되었습니다.");
    }
}
