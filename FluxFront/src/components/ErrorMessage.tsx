import React from 'react';

interface ErrorMessageProps {
  error: string;
}

export const ErrorMessage: React.FC<ErrorMessageProps> = ({ error }) => {
  return (
    <div className="bg-red-500/10 border border-red-500/50 text-red-200 px-6 py-4 rounded-xl mb-10 flex items-center space-x-3 animate-fade-in">
      <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
      <div>
        <strong className="font-bold block">Optimization Failed</strong>
        <span className="text-sm opacity-90">{error}</span>
      </div>
    </div>
  );
};