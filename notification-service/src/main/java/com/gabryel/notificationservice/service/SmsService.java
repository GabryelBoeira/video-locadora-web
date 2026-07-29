package com.gabryel.notificationservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SmsService {

    private final WebClient smsWebClient;

    public SmsService( WebClient smsWebClient) {
        this.smsWebClient = smsWebClient;
    }

    public void sendSms(String phoneNumber, String message) {
        // Monta o DTO exigido pelo provedor de SMS
        SmsRequest request = new SmsRequest(phoneNumber, message);

        this.smsWebClient.post()
                .uri("/v1/send")
                .header("Authorization", "Bearer SEU_TOKEN_AQUI")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .subscribe(); // Disparo assíncrono para não travar a thread do Kafka
    }

    private record SmsRequest(String number, String text) {
    }

}
