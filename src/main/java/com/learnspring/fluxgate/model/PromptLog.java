package com.learnspring.fluxgate.model;

// This is a Plain Old Java Object (POJO).
// For the database to see it, we need to add @Entity and other JPA annotations.
public class PromptLog {

    private int originalTokens;
    private int optimizedTokens;
    private String originalPrompt;
    private String optimizedPrompt;
    private String selectedModel;

    public PromptLog(int originalTokens,
                     int optimizedTokens,
                     String originalPrompt,
                     String optimizedPrompt,
                     String selectedModel) {
        this.originalTokens = originalTokens;
        this.optimizedTokens = optimizedTokens;
        this.originalPrompt = originalPrompt;
        this.optimizedPrompt = optimizedPrompt;
        this.selectedModel = selectedModel;
    }

    // Getters are needed for the application to function correctly
    public int getOriginalTokens() { return originalTokens; }
    public int getOptimizedTokens() { return optimizedTokens; }
    public String getOriginalPrompt() { return originalPrompt; }
    public String getOptimizedPrompt() { return optimizedPrompt; }
    public String getSelectedModel() { return selectedModel; }
}