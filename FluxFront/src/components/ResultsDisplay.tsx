import React from 'react';
import type { OptimizationResponse } from '../types';
import { StatCard } from './StatCard';
import { PromptComparison } from './PromptComparison';
import { FinalResponse } from './FinalResponse';
import { ModelBadge } from './ModelBadge';

interface ResultsDisplayProps {
  result: OptimizationResponse;
}

export const ResultsDisplay: React.FC<ResultsDisplayProps> = ({ result }) => {
  return (
    <div className="space-y-12 animate-fade-in">
      {/* Model Badge */}
      <ModelBadge modelName={result.selectedModel} />

      {/* Stats Section */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6 text-center">
        <StatCard title="Original Tokens" value={result.originalTokens} />
        <StatCard title="Optimized Tokens" value={result.optimizedTokens} />
        {/* Efficiency Gain Card Removed */}
      </div>

      {/* Prompt Comparison Section */}
      <PromptComparison 
        original={result.originalPrompt} 
        optimized={result.optimizedPrompt} 
      />

      {/* Final Response Section */}
      <FinalResponse response={result.finalResponse} />
    </div>
  );
};