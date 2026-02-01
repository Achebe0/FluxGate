package com.learnspring.fluxgate.LlmClients;

import com.learnspring.fluxgate.dto.ChatRequest;
import com.learnspring.fluxgate.dto.ChatResponse;
import com.learnspring.fluxgate.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class NvidiaClient implements LlmProvider {

    private final WebClient client;
    // OFFICIAL NVIDIA API: Llama 3.1 8B (Small & Fast)
    private final String modelName = "meta/llama-3.1-8b-instruct";

    public NvidiaClient(
            @Value("${nvidia.api-key}") String apiKey,
            WebClient.Builder builder
    ) {
        this.client = builder
                // GUARANTEED OFFICIAL NVIDIA ENDPOINT
                .baseUrl("https://integrate.api.nvidia.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public String getName() {
        return "Nvidia Llama 3.1 8B";
    }

    @Override
    public String generate(String prompt) {
        ChatRequest request = new ChatRequest(
              modelName,
              List.of(new Message("user", prompt)),
                0.3,
                1024,
                false
        );

        try {
            ChatResponse response = client
                    .post()
                    .uri("/chat/completions")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();

            if(response != null && !response.choices().isEmpty()) {
                return response.choices().get(0).message().content();
            }
        } catch(Exception e){
            return "Error calling Nvidia API: "+ e.getMessage();
        }
        return "No response from Nvidia API";
    }
}