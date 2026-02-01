package com.learnspring.fluxgate.dto;

public record PromptLogDTO(
    int originalTokens,
    int optimizedTokens,
    String originalPrompt,
    String optimizedPrompt,
    String selectedModel
) {}