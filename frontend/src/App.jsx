import { useState } from 'react'
import './App.css'

function App() {
  const [prompt, setPrompt] = useState('')
  const [result, setResult] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const handleSubmit = async (e) => {
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

      const data = await response.json()
      setResult(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <>
      <h1>FluxGate</h1>
      <h2>Smart Prompt Router & Optimizer</h2>
      
      <form onSubmit={handleSubmit} style={{ maxWidth: '600px', margin: '0 auto' }}>
        <textarea
          value={prompt}
          onChange={(e) => setPrompt(e.target.value)}
          placeholder="Enter your verbose prompt here..."
          rows={5}
        />
        <div style={{ marginTop: '10px' }}>
          <button type="submit" disabled={loading}>
            {loading ? 'Routing & Optimizing...' : 'Submit'}
          </button>
        </div>
      </form>

      {error && <div style={{ color: 'red', marginTop: '20px' }}>Error: {error}</div>}

      {result && (
        <div className="card">
          <div className="stats">
            <span>Original Tokens: {result.originalTokens}</span>
            <span>Optimized Tokens: {result.optimizedTokens}</span>
            <span style={{ color: '#4caf50' }}>Savings: {result.tokenSavingsPercentage}%</span>
          </div>
          
          <div className="model-badge" style={{ 
            backgroundColor: result.selectedModel.includes('High') ? '#673ab7' : '#2196f3',
            color: 'white',
            padding: '8px',
            borderRadius: '4px',
            margin: '10px 0',
            fontWeight: 'bold',
            display: 'inline-block'
          }}>
            Routed To: {result.selectedModel}
          </div>

          <div className="result-section">
            <h3>Optimized Prompt:</h3>
            <p>{result.optimizedPrompt}</p>
          </div>

          <div className="result-section">
            <h3>Final Response:</h3>
            <p style={{ whiteSpace: 'pre-wrap' }}>{result.finalResponse}</p>
          </div>
        </div>
      )}
    </>
  )
}

export default App