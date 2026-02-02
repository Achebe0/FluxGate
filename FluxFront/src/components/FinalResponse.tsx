import React from 'react';
import ReactMarkdown from 'react-markdown';
import remarkGfm from 'remark-gfm';

interface FinalResponseProps {
  response: string;
}

export const FinalResponse: React.FC<FinalResponseProps> = ({ response }) => {
  return (
    <div className="w-full">
      <h2 className="text-2xl font-bold text-center mb-6">Final Response</h2>
      <div className="prose prose-invert w-full max-w-full bg-gray-800/50 rounded-2xl p-6 ring-1 ring-gray-700/50 overflow-x-auto">
        <ReactMarkdown remarkPlugins={[remarkGfm]}>
          {response}
        </ReactMarkdown>
      </div>
    </div>
  );
};