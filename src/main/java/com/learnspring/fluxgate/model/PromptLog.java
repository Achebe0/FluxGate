package com.learnspring.fluxgate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "prompt_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String originalPrompt;

    @Column(columnDefinition = "TEXT")
    private String optimizedPrompt;

    private int originalTokens;
    private int optimizedTokens;
    private int tokenSavingsPercentage;

    private String selectedModel;

    @Column(columnDefinition = "TEXT")
    private String finalResponse;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
