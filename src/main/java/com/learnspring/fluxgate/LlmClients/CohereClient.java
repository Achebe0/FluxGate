package com.learnspring.fluxgate.LlmClients;

import com.learnspring.fluxgate.dto.ChatRequest;
import com.learnspring.fluxgate.dto.ChatResponse;
import com.learnspring.fluxgate.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Component
public class CohereClient implements LlmProvider {

    private final WebClient webClient;
    // UPDATED: Using the official Cohere model name.
    private final String modelName = "command-r";

    public CohereClient(
            // This key will now be your Cohere API key
            @Value("${chimera_deepseek.api-key}") String apiKey,
            WebClient.Builder builder
    ) {
        this.webClient = builder
                // UPDATED: Using the official Cohere API endpoint.
                .baseUrl("https://api.cohere.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Override
    public String getName() {
        return "Cohere Command R (Reasoning)";
    }

    @Override
    public String generate(String prompt) {
        // NOTE: Cohere's API structure is slightly different. 
        // We might need to adjust the DTOs if this fails, but let's try the standard first.
        ChatRequest request = new ChatRequest(
            modelName,
            List.of(new Message("user", prompt)),
            0.7,
            4096,
            false
        );

        try {
            ChatResponse response = webClient
                    .post()
                    .uri("/chat") // Cohere's endpoint is /chat, not /chat/completions
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatResponse.class)
                    .block();

            if (response != null && !response.choices().isEmpty()) {
                return response.choices().get(0).message().content();
            }
        } catch (Exception e) {
            return "Error calling Cohere API: " + e.getMessage();
        }
        return "Error: No response from Cohere API";
    }
}