package com.learnspring.fluxgate.LlmClients;

import com.learnspring.fluxgate.dto.ChatRequest;
import com.learnspring.fluxgate.dto.ChatResponse;
import com.learnspring.fluxgate.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ChimeraClient implements LlmProvider {

    private final WebClient webClient;
    private final String modelName = "meta/llama-3.1-405b-instruct";
    private final boolean enabled;

    public ChimeraClient(
            @Value("${nvidia.api-key:}") String apiKey, // default empty string
            WebClient.Builder builder
    ) {
        this.enabled = !apiKey.isEmpty();

        if (enabled) {
            this.webClient = builder
                    .baseUrl("https://integrate.api.nvidia.com/v1")
                    .defaultHeader("Authorization", "Bearer " + apiKey)
                    .defaultHeader("Content-Type", "application/json")
                    .build();
        } else {
            this.webClient = null; // won’t be used
            System.out.println("Warning: Nvidia API key not set. ChimeraClient disabled.");
        }
    }

    @Override
    public String getName() {
        return "Nvidia Llama 3.1 405B (Reasoning)";
    }

    @Override
    public String generate(String prompt) {
        if (!enabled) {
            return "ChimeraClient not configured.";
        }

        ChatRequest request = new ChatRequest(
                modelName,
                List.of(new Message("user", prompt)),
                0.7,
                2048,
                false
        );

        try {
            // Expect an array of ChatResponse and take the first element.
            ChatResponse[] responseArray = webClient
                    .post()
                    .uri("/chat/completions")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatResponse[].class)
                    .block();

            if (responseArray != null && responseArray.length > 0) {
                ChatResponse response = responseArray[0];
                if (response != null && !response.choices().isEmpty()) {
                    return response.choices().get(0).message().content();
                }
            }
        } catch (Exception e) {
            return "Error calling Nvidia API (405B): " + e.getMessage();
        }
        return "Error: No response from Nvidia API";
    }

    @Override
    public Flux<String> generateStream(String prompt) {
        if (!enabled) {
            return Flux.just("ChimeraClient not configured.");
        }

        ChatRequest request = new ChatRequest(
                modelName,
                List.of(new Message("user", prompt)),
                0.7,
                2048,
                true // Enable streaming
        );

        return webClient
                .post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(ChatResponse.class)
                .map(response -> {
                    if (response.choices() != null && !response.choices().isEmpty()) {
                        String content = response.choices().get(0).delta().content();
                        return content != null ? content : "";
                    }
                    return "";
                })
                .onErrorResume(e -> Flux.just("Error: " + e.getMessage()));
    }
}
