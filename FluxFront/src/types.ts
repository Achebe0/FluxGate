export interface OptimizationResponse {
    originalPrompt: string;
    optimizedPrompt: string;
    originalTokens: number;
    optimizedTokens: number;
    // tokenSavingsPercentage removed
    finalResponse: string;
    selectedModel: string;
  }