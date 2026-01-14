package com.learnspring.fluxgate.controller;

import com.learnspring.fluxgate.dto.OptimizationResponse;
import com.learnspring.fluxgate.service.LlmService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prompt")
@CrossOrigin(origins = "http://localhost:3000") // Explicitly allow CORS on the controller as well
public class PromptController {

    private final LlmService llmService;

    public PromptController(LlmService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/optimize")
    public OptimizationResponse optimizePrompt(@RequestBody String prompt) {
        return llmService.optimizeAndGenerate(prompt);
    }
}