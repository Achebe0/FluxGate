package com.learnspring.fluxgate.dto;

public record Message(
    String role,
    String content
) {}