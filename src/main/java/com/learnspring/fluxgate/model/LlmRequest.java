package com.learnspring.fluxgate.model;

public record LlmRequest(
        String originalPrompt,
        String latency,
        int tokenSize
) {
};
