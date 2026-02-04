package com.learnspring.fluxgate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Choice(
    Message message,
    Message delta
) {}