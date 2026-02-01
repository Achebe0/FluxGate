package com.learnspring.fluxgate.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prompt_logs")
public class PromptLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_tokens")
    private int originalTokens;

    @Column(name = "optimized_tokens")
    private int optimizedTokens;

    @Column(name = "original_prompt", columnDefinition = "TEXT")
    private String originalPrompt;

    @Column(name = "optimized_prompt", columnDefinition = "TEXT")
    private String optimizedPrompt;

    @Column(name = "selected_model")
    private String selectedModel;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Default constructor required by JPA
    public PromptLog() {

    }

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
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getOriginalTokens() { return originalTokens; }
    public void setOriginalTokens(int originalTokens) { this.originalTokens = originalTokens; }

    public int getOptimizedTokens() { return optimizedTokens; }
    public void setOptimizedTokens(int optimizedTokens) { this.optimizedTokens = optimizedTokens; }

    public String getOriginalPrompt() { return originalPrompt; }
    public void setOriginalPrompt(String originalPrompt) { this.originalPrompt = originalPrompt; }

    public String getOptimizedPrompt() { return optimizedPrompt; }
    public void setOptimizedPrompt(String optimizedPrompt) { this.optimizedPrompt = optimizedPrompt; }

    public String getSelectedModel() { return selectedModel; }
    public void setSelectedModel(String selectedModel) { this.selectedModel = selectedModel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}