package com.learnspring.fluxgate.model;

public record OptimizationResponse(
        String originalPrompt,
        String optimizedPrompt,
        int originalTokens,
        int optimizedTokens,
        int tokenSavingsPercentage,
        String finalResponse,
        String selectedModel
) {
};
