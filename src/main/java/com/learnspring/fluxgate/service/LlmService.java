package com.learnspring.fluxgate.service;

import com.learnspring.fluxgate.LlmClients.ChimeraClient;
import com.learnspring.fluxgate.LlmClients.DeepSeekClient;
import com.learnspring.fluxgate.LlmClients.LlmProvider;
import com.learnspring.fluxgate.LlmClients.NvidiaClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LlmService {
    private final ChimeraClient chimeraClient;
    private final NvidiaClient nvidiaClient;
    private final DeepSeekClient deepSeekClient;

    private static final String SYSTEM_INSTRUCTION = 
        "You are an expert Prompt Engineer. Your task is to OPTIMIZE the following prompt to be clearer, more specific, and better structured for an LLM.\n" +
        "CRITICAL INSTRUCTIONS:\n" +
        "1. Output ONLY the rewritten, optimized prompt.\n" +
        "2. PRESERVE the original subject matter and intent. If the user asks about 'ethics', the optimized prompt MUST be about 'ethics'.\n" +
        "3. Do NOT create a generic template (e.g., 'Summarize the text below'). Make it specific to the user's request.\n" +
        "4. Do NOT use any markdown formatting (like `**` or `*`). Output plain text only.\n" +
        "5. Do NOT add any introductory text or quotes.\n" +
        "6. Do NOT answer the prompt itself.\n\n" +
        "Original Prompt:\n";

    public LlmService(ChimeraClient chimeraClient, 
                      NvidiaClient nvidiaClient, 
                      DeepSeekClient deepSeekClient) {
        this.chimeraClient = chimeraClient;
        this.nvidiaClient = nvidiaClient;
        this.deepSeekClient = deepSeekClient;
    }

    public enum ModelType{
        SMALL,
        FAST,
        FAST_AND_REASONING
    }

    public String generateResponse(String prompt, ModelType type){
        LlmProvider provider = getProvider(type);
        return provider.generate(prompt);
    }

    public Flux<String> generateStream(String prompt, ModelType type) {
        LlmProvider provider = getProvider(type);
        return provider.generateStream(prompt);
    }

    public String optimizePrompt(String userPrompt, ModelType type) {
        String finalPrompt = SYSTEM_INSTRUCTION + userPrompt;
        LlmProvider provider = getProvider(type);
        return provider.generate(finalPrompt);
    }
    
    public String getModelName(ModelType type) {
        return getProvider(type).getName();
    }
    
    private LlmProvider getProvider(ModelType type){
        switch (type){
            case FAST:
                return deepSeekClient;
            case FAST_AND_REASONING:
                return chimeraClient;
            case SMALL:
            default:
                return nvidiaClient;
        }
    }
}