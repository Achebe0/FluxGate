package com.learnspring.fluxgate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnspring.fluxgate.dto.OptimizationResponse;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.util.List;

@Service
public class LlmService {

    private final OpenAiService ollamaClient;
    private final Encoding tokenEncoder;

    // Threshold for switching to a "complex" model
    private static final int COMPLEXITY_THRESHOLD_TOKENS = 50;

    private static final String OPTIMIZATION_SYSTEM_PROMPT = """
        You are an expert LLM Prompt Optimizer. Your goal is to rewrite the user's prompt to be shorter, clearer, and more effective while preserving original intent.
        
        Rules:
        - Reduce tokens by 30-50% by removing fluff, repetition, politeness.
        - Make it direct and structured.
        - STRICTLY PRESERVE all constraints (word count, format, style, JSON output, etc.).
        - Add "Answer step-by-step" only for complex reasoning/math/analysis tasks.
        - For simple facts/definitions, keep concise — no extras.
        - Never add examples unless requested.
        - Output ONLY the optimized prompt. No explanations, no quotes.
        
        Examples:
        Original: "Hey, can you please explain in a simple way what quantum computing is?"
        Optimized: Explain quantum computing simply for beginners.
        
        Original: "Write a 500-word essay about cats."
        Optimized: Write a 500-word essay about cats.
        
        Original: "Think carefully: A bat and ball cost $1.10. The bat costs $1 more than the ball. How much is the ball?"
        Optimized: Answer step-by-step: A bat and ball cost $1.10 total. The bat costs $1.00 more than the ball. How much does the ball cost?
        
        Now optimize this prompt:
        """;

    public LlmService() {
        // Configure for Ollama (local)
        String token = "ollama";
        Duration timeout = Duration.ofSeconds(90);
        
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        OkHttpClient client = OpenAiService.defaultClient(token, timeout);
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:11434/v1/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
                
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        this.ollamaClient = new OpenAiService(api);
        
        this.tokenEncoder = Encodings.newDefaultEncodingRegistry().getEncodingForModel("gpt-4")
                .orElse(Encodings.newDefaultEncodingRegistry().getEncoding("cl100k_base").orElseThrow());
    }

    public OptimizationResponse optimizeAndGenerate(String userPrompt) {
        int originalTokens = tokenEncoder.countTokensOrdinary(userPrompt);

        // 1. Optimize the prompt
        ChatCompletionRequest optRequest = ChatCompletionRequest.builder()
                .model("llama3.1:8b") // Always use a fast model for optimization itself
                .messages(List.of(
                        new ChatMessage("system", OPTIMIZATION_SYSTEM_PROMPT),
                        new ChatMessage("user", userPrompt)
                ))
                .temperature(0.3)
                .maxTokens(512)
                .build();

        String optimizedPrompt = ollamaClient.createChatCompletion(optRequest)
                .getChoices().get(0).getMessage().getContent().trim();

        int optimizedTokens = tokenEncoder.countTokensOrdinary(optimizedPrompt);
        int savings = originalTokens > 0 ? 100 - (optimizedTokens * 100 / originalTokens) : 0;

        // 2. Route based on complexity (using optimized token count)
        String selectedModel;
        String modelId;

        if (optimizedTokens >= COMPLEXITY_THRESHOLD_TOKENS) {
            // Complex task
            modelId = "llama3.1:8b"; 
            selectedModel = "High-Performance Model (" + modelId + ")";
        } else {
            // Simple task
            modelId = "llama3.1:8b"; 
            selectedModel = "Fast Model (" + modelId + ")";
        }

        // 3. Generate Final Response using the selected model
        ChatCompletionRequest finalRequest = ChatCompletionRequest.builder()
                .model(modelId)
                .messages(List.of(new ChatMessage("user", optimizedPrompt)))
                .temperature(0.7)
                .maxTokens(10240) // Increased from 1024 to 4096
                .build();

        String finalResponse = ollamaClient.createChatCompletion(finalRequest)
                .getChoices().get(0).getMessage().getContent();

        return new OptimizationResponse(
                userPrompt,
                optimizedPrompt,
                originalTokens,
                optimizedTokens,
                savings,
                finalResponse,
                selectedModel // Return the descriptive name
        );
    }
}