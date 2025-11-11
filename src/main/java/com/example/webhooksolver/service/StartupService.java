package com.example.webhooksolver.service;

import com.example.webhooksolver.dto.GenerateWebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class StartupService {
    private final WebClient webClient;
    private final Logger log = LoggerFactory.getLogger(StartupService.class);

    @Value("${hiring.base-url:https://bfhldevapigw.healthrx.co.in/hiring}")
    private String baseUrl;

    // Replace these with your actual details before final submission
    private final String NAME = "shuklav reddy";
    private final String REG_NO = "PES2UG22CS557";
    private final String EMAIL = "shuklavreddysirigireddy@gmail.com";

    public StartupService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void executeFlow() {
        try {
            log.info("1) Sending generateWebhook request...");
            Map<String, String> request = Map.of(
                "name", NAME,
                "regNo", REG_NO,
                "email", EMAIL
            );

            GenerateWebhookResponse resp = webClient.post()
                .uri(baseUrl + "/generateWebhook/JAVA")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GenerateWebhookResponse.class)
                .block();

            if (resp == null) {
                log.error("No response from generateWebhook");
                return;
            }

            String webhookUrl = resp.getWebhook();
            String accessToken = resp.getAccessToken();
            log.info("Received webhook: {}", webhookUrl);

            int lastTwo = extractLastTwoDigits(REG_NO);
            boolean odd = (lastTwo % 2) == 1;
            log.info("regNo last two digits = {}, odd = {}", lastTwo, odd);

            String finalQuery = craftFinalQueryForProblem(odd);

            log.info("2) Submitting finalQuery to testWebhook...");
            Map<String, String> payload = Map.of("finalQuery", finalQuery);

            String submissionResponse = webClient.post()
                .uri(baseUrl + "/testWebhook/JAVA")
                .header("Authorization", accessToken) // if server expects Bearer, change to "Bearer " + accessToken
                .header("Content-Type", "application/json")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            log.info("Submission response: {}", submissionResponse);

        } catch (Exception e) {
            log.error("Error in startup flow", e);
        }
    }

    private int extractLastTwoDigits(String regNo) {
        String digits = regNo.replaceAll("\\D+", "");
        if (digits.length() >= 2) return Integer.parseInt(digits.substring(digits.length() - 2));
        if (digits.length() == 1) return Integer.parseInt(digits);
        return 0;
    }

    private String craftFinalQueryForProblem(boolean odd) {
    if (odd) {
        return "SELECT p.AMOUNT AS SALARY, "
             + "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, "
             + "TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, "
             + "d.DEPARTMENT_NAME "
             + "FROM PAYMENTS p "
             + "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID "
             + "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID "
             + "WHERE DAY(p.PAYMENT_TIME) != 1 "
             + "ORDER BY p.AMOUNT DESC LIMIT 1";
    } else {
        return "/* SQL for Question 2 — replace if needed */";
    }
}

}

