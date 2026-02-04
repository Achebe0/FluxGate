package com.learnspring.fluxgate.dto;

public record OptimizationMetadata(
    String originalPrompt,
    String optimizedPrompt,
    int originalTokens,
    int optimizedTokens,
    String modelInfo,
    String routingReason
) {}
