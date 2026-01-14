package com.learnspring.fluxgate.ollama;

import org.springframework.web.client.RestTemplate;

// Removed @Component because we are creating beans manually in OllamaConfig
public class OllamaClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ModelType modelType;

    public OllamaClient(ModelType modelType) {
        this.modelType = modelType;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public void generate(String prompt) {
        // call Ollama with modelType
    }
}
