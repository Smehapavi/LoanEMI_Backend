import React, { useState, useEffect } from 'react';
import { loanAPI } from '../services/api';
import { LoanData } from '../types';
import './EMICalculator.css';

interface EMICalculatorProps {
  onLoanApplied: () => void;
}

const EMICalculator: React.FC<EMICalculatorProps> = ({ onLoanApplied }) => {
  const [loanData, setLoanData] = useState<LoanData>({
    principal: 100000,
    rate: 10,
    tenure: 12,
  });
  const [calculatedData, setCalculatedData] = useState<LoanData | null>(null);
  const [loading, setLoading] = useState(false);
  const [applying, setApplying] = useState(false);
  const [message, setMessage] = useState('');

  // Calculate EMI whenever inputs change
  useEffect(() => {
    const calculateEMI = async () => {
      if (loanData.principal > 0 && loanData.rate >= 0 && loanData.tenure > 0) {
        try {
          setLoading(true);
          const result = await loanAPI.calculateEMI(loanData);
          setCalculatedData(result);
        } catch (error) {
          console.error('Failed to calculate EMI:', error);
        } finally {
          setLoading(false);
        }
      }
    };

    const debounceTimer = setTimeout(calculateEMI, 500);
    return () => clearTimeout(debounceTimer);
  }, [loanData]);

  const handleInputChange = (field: keyof LoanData, value: number) => {
    setLoanData(prev => ({
      ...prev,
      [field]: value,
    }));
    setMessage('');
  };

  const handleApplyLoan = async () => {
    if (!calculatedData) return;

    try {
      setApplying(true);
      await loanAPI.applyLoan(calculatedData);
      setMessage('Loan application submitted successfully!');
      onLoanApplied();
    } catch (error) {
      setMessage('Failed to apply for loan. Please try again.');
    } finally {
      setApplying(false);
    }
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(amount);
  };

  return (
    <div className="emi-calculator">
      <div className="calculator-card">
        <h2>Calculate Your Loan EMI</h2>
        
        <div className="input-section">
          <div className="input-group">
            <label htmlFor="principal">Loan Amount</label>
            <input
              type="number"
              id="principal"
              value={loanData.principal}
              onChange={(e) => handleInputChange('principal', Number(e.target.value))}
              min="1000"
              max="10000000"
              step="1000"
            />
            <span className="input-suffix">₹</span>
          </div>

          <div className="input-group">
            <label htmlFor="rate">Interest Rate (Annual)</label>
            <input
              type="number"
              id="rate"
              value={loanData.rate}
              onChange={(e) => handleInputChange('rate', Number(e.target.value))}
              min="0"
              max="50"
              step="0.1"
            />
            <span className="input-suffix">%</span>
          </div>

          <div className="input-group">
            <label htmlFor="tenure">Loan Tenure</label>
            <input
              type="number"
              id="tenure"
              value={loanData.tenure}
              onChange={(e) => handleInputChange('tenure', Number(e.target.value))}
              min="1"
              max="360"
              step="1"
            />
            <span className="input-suffix">months</span>
          </div>
        </div>

        {loading && (
          <div className="loading">Calculating...</div>
        )}

        {calculatedData && !loading && (
          <div className="results-section">
            <h3>EMI Calculation Results</h3>
            
            <div className="results-grid">
              <div className="result-item primary">
                <span className="label">Monthly EMI</span>
                <span className="value">{formatCurrency(calculatedData.emi || 0)}</span>
              </div>
              
              <div className="result-item">
                <span className="label">Total Amount</span>
                <span className="value">{formatCurrency(calculatedData.totalAmount || 0)}</span>
              </div>
              
              <div className="result-item">
                <span className="label">Total Interest</span>
                <span className="value">{formatCurrency(calculatedData.totalInterest || 0)}</span>
              </div>
              
              <div className="result-item">
                <span className="label">Principal Amount</span>
                <span className="value">{formatCurrency(calculatedData.principal)}</span>
              </div>
            </div>

            <div className="action-section">
              <button
                onClick={handleApplyLoan}
                disabled={applying}
                className="apply-loan-btn"
              >
                {applying ? 'Applying...' : 'Apply for This Loan'}
              </button>
            </div>
          </div>
        )}

        {message && (
          <div className={`message ${message.includes('success') ? 'success' : 'error'}`}>
            {message}
          </div>
        )}
      </div>
    </div>
  );
};

export default EMICalculator;