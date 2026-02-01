import React from 'react';

interface PromptFormProps {
  prompt: string;
  setPrompt: (prompt: string) => void;
  handleSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
  loading: boolean;
}

export const PromptForm: React.FC<PromptFormProps> = ({ prompt, setPrompt, handleSubmit, loading }) => {
  return (
    <form onSubmit={handleSubmit} className="mb-12 relative z-10">
      <div className="relative group">
        <div className="absolute -inset-1 bg-gradient-to-r from-purple-600 to-blue-600 rounded-2xl blur opacity-25 group-hover:opacity-50 transition duration-1000 group-hover:duration-200"></div>
        <div className="relative bg-gray-800 rounded-2xl p-2 ring-1 ring-gray-700/50 shadow-2xl">
          <textarea
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            placeholder="Enter your prompt here to optimize..."
            className="w-full bg-transparent text-lg text-gray-100 placeholder-gray-500 p-6 rounded-xl focus:outline-none min-h-[160px] resize-none font-medium"
          />
          <div className="flex justify-between items-center px-4 pb-2">
            <span className="text-xs text-gray-500 font-medium uppercase tracking-wider">
              {prompt.length} Characters
            </span>
            <button
              type="submit"
              disabled={loading || !prompt.trim()}
              className={`
                px-8 py-3 rounded-xl font-bold text-sm uppercase tracking-wider transition-all duration-300
                ${loading || !prompt.trim()
                  ? 'bg-gray-700 text-gray-500 cursor-not-allowed'
                  : 'bg-gradient-to-r from-blue-600 to-purple-600 text-white hover:shadow-[0_0_20px_rgba(79,70,229,0.5)] hover:scale-[1.02] active:scale-[0.98]'}
              `}
            >
              {loading ? (
                <span className="flex items-center space-x-2">
                  <svg className="animate-spin h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  <span>Processing...</span>
                </span>
              ) : (
                'Optimize Prompt'
              )}
            </button>
          </div>
        </div>
      </div>
    </form>
  );
};