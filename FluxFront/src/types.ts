export interface OptimizationResponse {
  originalPrompt: string;
  optimizedPrompt: string;
  originalTokens: number;
  optimizedTokens: number;
  tokenSavingsPercentage: number;
  finalResponse: string;
  selectedModel: string;
}