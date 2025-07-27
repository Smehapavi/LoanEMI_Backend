import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { loanAPI } from '../services/api';
import { LoanData } from '../types';
import EMICalculator from './EMICalculator';
import LoanList from './LoanList';
import './Dashboard.css';

const Dashboard: React.FC = () => {
  const { user, logout } = useAuth();
  const [loans, setLoans] = useState<LoanData[]>([]);
  const [loading, setLoading] = useState(false);
  const [activeTab, setActiveTab] = useState('calculator');

  const fetchLoans = async () => {
    try {
      setLoading(true);
      const userLoans = await loanAPI.getMyLoans();
      setLoans(userLoans);
    } catch (error) {
      console.error('Failed to fetch loans:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (activeTab === 'loans') {
      fetchLoans();
    }
  }, [activeTab]);

  const handleLoanApplied = () => {
    if (activeTab === 'loans') {
      fetchLoans();
    }
  };

  const handleLoanDeleted = async (id: number) => {
    try {
      await loanAPI.deleteLoan(id);
      setLoans(loans.filter(loan => loan.id !== id));
    } catch (error) {
      console.error('Failed to delete loan:', error);
    }
  };

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="header-content">
          <h1>Loan EMI Calculator</h1>
          <div className="user-info">
            <span>Welcome, {user?.username}!</span>
            <button onClick={logout} className="logout-btn">
              Logout
            </button>
          </div>
        </div>
      </header>

      <nav className="dashboard-nav">
        <button
          className={`nav-btn ${activeTab === 'calculator' ? 'active' : ''}`}
          onClick={() => setActiveTab('calculator')}
        >
          EMI Calculator
        </button>
        <button
          className={`nav-btn ${activeTab === 'loans' ? 'active' : ''}`}
          onClick={() => setActiveTab('loans')}
        >
          My Loans
        </button>
      </nav>

      <main className="dashboard-content">
        {activeTab === 'calculator' && (
          <EMICalculator onLoanApplied={handleLoanApplied} />
        )}
        {activeTab === 'loans' && (
          <LoanList 
            loans={loans} 
            loading={loading} 
            onDeleteLoan={handleLoanDeleted}
          />
        )}
      </main>
    </div>
  );
};

export default Dashboard;