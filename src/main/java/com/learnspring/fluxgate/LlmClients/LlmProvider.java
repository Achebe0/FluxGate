package com.learnspring.fluxgate.LlmClients;

public interface LlmProvider {
    String getName();
    String generate(String prompt);
}