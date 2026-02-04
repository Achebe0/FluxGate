package com.learnspring.fluxgate.LlmClients;

import reactor.core.publisher.Flux;

public interface LlmProvider {
    String getName();
    String generate(String prompt); // Keep for blocking calls (like optimization)
    Flux<String> generateStream(String prompt); // New streaming method
}