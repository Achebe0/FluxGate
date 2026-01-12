package com.learnspring.fluxgate.config;

import com.learnspring.fluxgate.ollama.ModelType;
import com.learnspring.fluxgate.ollama.OllamaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

    @Bean
    public OllamaClient llama3Client() {
        return new OllamaClient(ModelType.LLAMA3);
    }

    @Bean
    public OllamaClient mistralClient() {
        return new OllamaClient(ModelType.MISTRAL);
    }

    @Bean
    public OllamaClient deepseekClient() {
        return new OllamaClient(ModelType.DEEPSEEK_CODER);
    }
}
