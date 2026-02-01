package com.learnspring.fluxgate.dto;

import java.util.List;

public record ChatResponse(
    List<Choice> choices
) {}