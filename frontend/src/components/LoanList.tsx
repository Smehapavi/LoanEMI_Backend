import React from 'react';
import { LoanData } from '../types';
import './LoanList.css';

interface LoanListProps {
  loans: LoanData[];
  loading: boolean;
  onDeleteLoan: (id: number) => void;
}

const LoanList: React.FC<LoanListProps> = ({ loans, loading, onDeleteLoan }) => {
  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      maximumFractionDigits: 0,
    }).format(amount);
  };

  const formatTenure = (months: number) => {
    if (months >= 12) {
      const years = Math.floor(months / 12);
      const remainingMonths = months % 12;
      if (remainingMonths === 0) {
        return `${years} year${years > 1 ? 's' : ''}`;
      }
      return `${years} year${years > 1 ? 's' : ''} ${remainingMonths} month${remainingMonths > 1 ? 's' : ''}`;
    }
    return `${months} month${months > 1 ? 's' : ''}`;
  };

  if (loading) {
    return (
      <div className="loan-list">
        <div className="loading-container">
          <div className="loading-spinner"></div>
          <p>Loading your loans...</p>
        </div>
      </div>
    );
  }

  if (loans.length === 0) {
    return (
      <div className="loan-list">
        <div className="empty-state">
          <div className="empty-icon">📋</div>
          <h3>No Loans Found</h3>
          <p>You haven't applied for any loans yet. Use the EMI calculator to calculate and apply for a loan.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="loan-list">
      <h2>My Loan Applications</h2>
      <div className="loans-grid">
        {loans.map((loan) => (
          <div key={loan.id} className="loan-card">
            <div className="loan-header">
              <h3>Loan #{loan.id}</h3>
              <button
                onClick={() => loan.id && onDeleteLoan(loan.id)}
                className="delete-btn"
                title="Delete loan"
              >
                ✕
              </button>
            </div>
            
            <div className="loan-details">
              <div className="detail-row">
                <span className="label">Principal Amount:</span>
                <span className="value">{formatCurrency(loan.principal)}</span>
              </div>
              
              <div className="detail-row">
                <span className="label">Interest Rate:</span>
                <span className="value">{loan.rate}% per annum</span>
              </div>
              
              <div className="detail-row">
                <span className="label">Tenure:</span>
                <span className="value">{formatTenure(loan.tenure)}</span>
              </div>
              
              <div className="detail-row highlight">
                <span className="label">Monthly EMI:</span>
                <span className="value">{formatCurrency(loan.emi || 0)}</span>
              </div>
              
              <div className="detail-row">
                <span className="label">Total Amount:</span>
                <span className="value">{formatCurrency(loan.totalAmount || 0)}</span>
              </div>
              
              <div className="detail-row">
                <span className="label">Total Interest:</span>
                <span className="value">{formatCurrency(loan.totalInterest || 0)}</span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default LoanList;