package com.learnspring.fluxgate.LlmClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class ChimeraClient implements LlmProvider {
// webclient to take the API
    private final WebClient webClient;
    private final String modelName = "neversleep/chimera";

    //takes the API key
    public ChimeraClient(
            @Value("${chimera_deepseek.api-key}") String apiKey,
            WebClient.Builder builder
    ) {
        this.webClient = builder
                .baseUrl("https://openrouter.ai/api/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("HTTP-Referer", "http://localhost:5173")
                .defaultHeader("LLM_Prompter", "FluxGate")
                .build();
    }


    @Override
    public String getName() {
        return "";
    }

    @Override
    public String generate(String prompt) {
        return "";
    }
}