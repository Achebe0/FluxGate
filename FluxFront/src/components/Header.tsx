import React from 'react';

export const Header: React.FC = () => {
  return (
    <div className="text-center mb-12 space-y-4">
      <h1 className="text-6xl font-black tracking-tight text-transparent bg-clip-text bg-gradient-to-r from-red-400 via-yellow-500 to-pink-500 drop-shadow-lg">
        LLM Optimizer
      </h1>
      <p className="text-xl text-gray-400 font-light max-w-2xl mx-auto">
        Intelligent Prompt Engineering & Dynamic Model Routing
      </p>
    </div>
  );
};