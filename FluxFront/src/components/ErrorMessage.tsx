import React from 'react';

interface ErrorMessageProps {
  error: string;
}

export const ErrorMessage: React.FC<ErrorMessageProps> = ({ error }) => {
  return (
    <div className="bg-red-500/10 border border-red-500/20 rounded-xl p-6 mb-8 text-center animate-fade-in">
      <p className="text-red-400 font-medium flex items-center justify-center space-x-2">
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        <span>{error}</span>
      </p>
    </div>
  );
};