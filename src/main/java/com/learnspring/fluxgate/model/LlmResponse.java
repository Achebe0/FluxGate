package com.learnspring.fluxgate.model;

public record LlmResponse(
        String optimizedPrompt,
        String modelSelected,
        String optimizedToken,
        String model
) {
};
