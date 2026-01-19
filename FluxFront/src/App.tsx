import  {useState, FormEvent} from 'react';
import './App.css'

interface OptimizationResponse {
  originalPrompt: string;
  optimizedPrompt: string;
  originalTokens: number;
  optimizedTokens: number;
  tokenSavingsPercentage: number;
  finalResponse: string;
  selectedModel: string;
}

function App() {
  const [prompt, setPrompt] = useState<string>('')
  const [result, setResult] = useState<OptimizationResponse | null>(null)
  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    if (!prompt.trim()) return

    setLoading(true)
    setError(null)
    setResult(null)

    try {
      const response = await fetch('http://localhost:8080/api/prompt/optimize', {
        method: 'POST',
        headers: {
          'Content-Type': 'text/plain',
        },
        body: prompt,
      })

      if (!response.ok) {
        throw new Error('Failed to optimize prompt')
      }

      const data: OptimizationResponse = await response.json()
      setResult(data)
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message)
      } else {
        setError('An unknown error occurred')
      }
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-[#0f172a] text-gray-100 py-12 px-4 selection:bg-purple-500 selection:text-white font-sans">
      {/* Background Glow Effects */}
      <div className="fixed top-0 left-0 w-full h-full overflow-hidden -z-10 pointer-events-none">
        <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-purple-600/20 rounded-full blur-[120px] animate-pulse-slow"></div>
        <div className="absolute bottom-[-10%] right-[-10%] w-[40%] h-[40%] bg-blue-600/20 rounded-full blur-[120px] animate-pulse-slow delay-1000"></div>
      </div>

      <div className="max-w-5xl mx-auto">
        {/* Header */}
        <div className="text-center mb-12 space-y-4">
          <h1 className="text-6xl font-black tracking-tight text-transparent bg-clip-text bg-gradient-to-r from-blue-400 via-purple-500 to-pink-500 drop-shadow-lg">
            FluxGate
          </h1>
          <p className="text-xl text-gray-400 font-light max-w-2xl mx-auto">
            Intelligent Prompt Engineering & Dynamic Model Routing
          </p>
        </div>

        {/* Input Form */}
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

        {/* Error Message */}
        {error && (
          <div className="bg-red-500/10 border border-red-500/50 text-red-200 px-6 py-4 rounded-xl mb-10 flex items-center space-x-3 animate-fade-in">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <div>
              <strong className="font-bold block">Optimization Failed</strong>
              <span className="text-sm opacity-90">{error}</span>
            </div>
          </div>
        )}

        {/* Results Section */}
        {result && (
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
        )}
      </div>
    </div>
  )
}

export default App