package com.learnspring.fluxgate.dto;

public record OptimizationResponse(
    String originalPrompt,
    String optimizedPrompt,
    int originalTokens,
    int optimizedTokens,
    // tokenSavingsPercentage field removed
    String finalResponse,
    String selectedModel
) {}