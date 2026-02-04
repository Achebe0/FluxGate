package com.learnspring.fluxgate.LlmClients;

import com.learnspring.fluxgate.dto.ChatRequest;
import com.learnspring.fluxgate.dto.ChatResponse;
import com.learnspring.fluxgate.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class DeepSeekClient implements LlmProvider {

    private final WebClient webClient;
    private final boolean enabled;
    // OFFICIAL NVIDIA API: Using Google Gemma 2 9B (Hosted by Nvidia)
    private final String modelName = "google/gemma-2-9b-it";

    public DeepSeekClient(
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
            return "Error calling Nvidia API (Gemma): " + e.getMessage();
        }
        return "Error: No response from Nvidia API";
    }

    @Override
    public Flux<String> generateStream(String prompt) {
        if (!enabled) {
            return Flux.just("DeepSeekClient not configured.");
        }

        ChatRequest request = new ChatRequest(
                modelName,
                List.of(new Message("user", prompt)),
                0.5,
                1024,
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
