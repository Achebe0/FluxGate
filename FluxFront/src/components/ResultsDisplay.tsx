import React from 'react';
import { OptimizationResponse } from '../types';

interface ResultsDisplayProps {
  result: OptimizationResponse;
}

export const ResultsDisplay: React.FC<ResultsDisplayProps> = ({ result }) => {
  return (
    <div className="space-y-8 animate-fade-in-up">
      
      {/* Routing Badge */}
      <div className="flex justify-center">
        <div className={`inline-flex items-center space-x-2 px-6 py-3 rounded-full text-sm font-bold shadow-lg border backdrop-blur-md transition-all duration-500 ${
          result.selectedModel.includes('High') 
            ? 'bg-purple-500/20 border-purple-500/50 text-purple-200 shadow-purple-500/20' 
            : 'bg-blue-500/20 border-blue-500/50 text-blue-200 shadow-blue-500/20'
        }`}>
          <span className="relative flex h-3 w-3">
            <span className={`animate-ping absolute inline-flex h-full w-full rounded-full opacity-75 ${result.selectedModel.includes('High') ? 'bg-purple-400' : 'bg-blue-400'}`}></span>
            <span className={`relative inline-flex rounded-full h-3 w-3 ${result.selectedModel.includes('High') ? 'bg-purple-500' : 'bg-blue-500'}`}></span>
          </span>
          <span>Routed To: {result.selectedModel}</span>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-gray-800/50 p-6 rounded-2xl border border-gray-700/50 text-center backdrop-blur-sm hover:bg-gray-800/70 transition">
          <p className="text-gray-400 text-xs font-bold uppercase tracking-widest mb-2">Original Tokens</p>
          <p className="text-4xl font-black text-white">{result.originalTokens}</p>
        </div>
        <div className="bg-gray-800/50 p-6 rounded-2xl border border-gray-700/50 text-center backdrop-blur-sm hover:bg-gray-800/70 transition">
          <p className="text-gray-400 text-xs font-bold uppercase tracking-widest mb-2">Optimized Tokens</p>
          <p className="text-4xl font-black text-blue-400">{result.optimizedTokens}</p>
        </div>
        <div className="bg-gray-800/50 p-6 rounded-2xl border border-gray-700/50 text-center backdrop-blur-sm hover:bg-gray-800/70 transition relative overflow-hidden group">
          <div className="absolute inset-0 bg-green-500/10 opacity-0 group-hover:opacity-100 transition duration-500"></div>
          <p className="text-gray-400 text-xs font-bold uppercase tracking-widest mb-2">Efficiency Gain</p>
          <p className="text-4xl font-black text-green-400">+{result.tokenSavingsPercentage}%</p>
        </div>
      </div>

      {/* Comparison Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div className="bg-gray-800/40 rounded-2xl p-8 border border-gray-700/50 backdrop-blur-sm">
          <h3 className="text-lg font-bold text-gray-300 mb-4 flex items-center">
            <span className="w-2 h-8 bg-gray-600 rounded-full mr-3"></span>
            Original Prompt
          </h3>
          <div className="bg-gray-900/50 rounded-xl p-5 text-gray-400 text-sm leading-relaxed whitespace-pre-wrap font-mono border border-gray-800">
            {result.originalPrompt}
          </div>
        </div>
        
        <div className="bg-gray-800/40 rounded-2xl p-8 border border-blue-500/20 backdrop-blur-sm relative overflow-hidden">
          <div className="absolute top-0 right-0 w-32 h-32 bg-blue-500/10 rounded-full blur-3xl -mr-16 -mt-16 pointer-events-none"></div>
          <h3 className="text-lg font-bold text-blue-400 mb-4 flex items-center">
            <span className="w-2 h-8 bg-blue-500 rounded-full mr-3 shadow-[0_0_10px_rgba(59,130,246,0.5)]"></span>
            Optimized Prompt
          </h3>
          <div className="bg-gray-900/80 rounded-xl p-5 text-gray-200 text-sm leading-relaxed whitespace-pre-wrap font-mono border border-blue-500/20 shadow-inner">
            {result.optimizedPrompt}
          </div>
        </div>
      </div>

      {/* Final Response */}
      <div className="bg-gray-800/60 rounded-2xl p-8 border border-gray-700/50 shadow-2xl backdrop-blur-md">
        <h3 className="text-2xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-green-400 to-emerald-600 mb-6 flex items-center">
          <span className="mr-3 text-3xl">✨</span> Final Response
        </h3>
        <div className="bg-[#0b1120] rounded-xl p-8 font-mono text-sm text-gray-300 leading-7 whitespace-pre-wrap border border-gray-700/50 shadow-inner overflow-x-auto">
          {result.finalResponse}
        </div>
      </div>
    </div>
  );
};