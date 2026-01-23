import React, { FormEvent } from 'react';

interface PromptFormProps {
  prompt: string;
  setPrompt: (prompt: string) => void;
  handleSubmit: (e: FormEvent) => void;
  loading: boolean;
}

export const PromptForm: React.FC<PromptFormProps> = ({ prompt, setPrompt, handleSubmit, loading }) => {
  return (
    <div className="bg-gray-800/40 backdrop-blur-xl rounded-2xl shadow-2xl p-8 mb-10 border border-gray-700/50 transition-all duration-300 hover:border-gray-600/50">
      <form onSubmit={handleSubmit}>
        <label htmlFor="prompt" className="block text-sm font-semibold text-gray-300 mb-3 uppercase tracking-wider">
          Input Prompt
        </label>
        <div className="relative group">
          <div className="absolute -inset-0.5 bg-gradient-to-r from-blue-500 to-purple-600 rounded-xl blur opacity-20 group-hover:opacity-40 transition duration-500"></div>
          <textarea
            id="prompt"
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            placeholder="Describe your task in detail..."
            className="relative w-full h-48 bg-gray-900/90 text-gray-100 rounded-xl border border-gray-700 p-5 focus:ring-2 focus:ring-purple-500 focus:border-transparent transition duration-200 resize-none placeholder-gray-600 text-lg leading-relaxed shadow-inner"
          />
        </div>
        
        <div className="mt-6 flex justify-end">
          <button
            type="submit"
            disabled={loading || !prompt.trim()}
            className={`relative px-8 py-4 rounded-xl font-bold text-white shadow-xl transition-all duration-300 transform hover:-translate-y-1
              ${loading || !prompt.trim() 
                ? 'bg-gray-700 cursor-not-allowed opacity-50' 
                : 'bg-gradient-to-r from-blue-600 via-purple-600 to-pink-600 hover:shadow-purple-500/40'
              }`}
          >
            {loading ? (
              <span className="flex items-center space-x-3">
                <svg className="animate-spin h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                <span>Processing...</span>
              </span>
            ) : (
              <span className="flex items-center space-x-2">
                <span>Optimize & Route</span>
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z" clipRule="evenodd" />
                </svg>
              </span>
            )}
          </button>
        </div>
      </form>
    </div>
  );
};