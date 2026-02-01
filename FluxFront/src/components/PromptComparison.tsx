import React from 'react';

interface PromptComparisonProps {
  original: string;
  optimized: string;
}

export const PromptComparison: React.FC<PromptComparisonProps> = ({ original, optimized }) => {
  return (
    <div>
      <h2 className="text-2xl font-bold text-center mb-6">Prompt Comparison</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {/* Original Prompt */}
        <div className="bg-gray-800/50 rounded-2xl p-6 ring-1 ring-gray-700/50">
          <h3 className="text-lg font-semibold mb-3 text-gray-300">Original Prompt</h3>
          <p className="text-gray-400 whitespace-pre-wrap font-mono text-sm">{original}</p>
        </div>
        {/* Optimized Prompt */}
        <div className="bg-gray-800/50 rounded-2xl p-6 ring-1 ring-purple-500/50">
          <h3 className="text-lg font-semibold mb-3 text-purple-400">Optimized Prompt</h3>
          <p className="text-gray-200 whitespace-pre-wrap font-mono text-sm">{optimized}</p>
        </div>
      </div>
    </div>
  );
};