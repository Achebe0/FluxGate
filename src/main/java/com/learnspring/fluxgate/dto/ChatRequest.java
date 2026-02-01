package com.learnspring.fluxgate.dto;

import java.util.List;

public record ChatRequest(
    String model,
    List<Message> messages,
    double temperature, // Renamed from 'temp' to match API
    int max_tokens,
    boolean stream
) {}