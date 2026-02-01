package com.learnspring.fluxgate.LlmClients;

import com.learnspring.fluxgate.dto.ChatRequest;
import com.learnspring.fluxgate.dto.ChatResponse;
import com.learnspring.fluxgate.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class DeepSeekClient implements LlmProvider {

    private final WebClient webClient;
    // OFFICIAL NVIDIA API: Using Google Gemma 2 9B (Hosted by Nvidia)
    private final String modelName = "google/gemma-2-9b-it";

    public DeepSeekClient(
            // Reusing the NVIDIA key
            @Value("${nvidia.api-key}") String apiKey,
            WebClient.Builder builder
    ) {
        this.webClient = builder
                // GUARANTEED OFFICIAL NVIDIA ENDPOINT
                .baseUrl("https://integrate.api.nvidia.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public String getName() {
        return "Nvidia Gemma 2 9B";
    }

    @Override
    public String generate(String prompt) {
        ChatRequest request = new ChatRequest(
            modelName,
            List.of(new Message("user", prompt)),
            0.5,
            1024,
            false
        );

        try {
            ChatResponse response = webClient
                    .post()
                    .uri("/chat/completions")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();

            if (response != null && !response.choices().isEmpty()) {
                return response.choices().get(0).message().content();
            }
        } catch (Exception e) {
            return "Error calling Nvidia API (Gemma): " + e.getMessage();
        }
        return "Error: No response from Nvidia API";
    }
}