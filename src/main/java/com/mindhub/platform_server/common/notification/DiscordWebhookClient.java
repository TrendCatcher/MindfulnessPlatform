package com.mindhub.platform_server.common.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class DiscordWebhookClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${discord.webhook.url:https://discord.com/api/webhooks/placeholder}")
    private String webhookUrl;

    public void sendNotification(String message) {
        try {
            if (webhookUrl.contains("placeholder")) {
                log.warn("Discord Webhook URL이 설정되지 않았습니다. 알림 내용: {}", message);
                return;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("content", message);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(webhookUrl, request, String.class);
            log.info("Discord 알림 전송 성공: {}", message);
        } catch (Exception e) {
            log.error("Discord 알림 전송 실패: {}", e.getMessage());
        }
    }
}
