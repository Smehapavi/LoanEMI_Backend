import axios from 'axios';
import { LoanData, AuthData } from '../types';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add auth token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Auth API
export const authAPI = {
  login: async (data: AuthData): Promise<string> => {
    const response = await api.post('/auth/login', data);
    return response.data;
  },

  signup: async (data: AuthData): Promise<string> => {
    const response = await api.post('/auth/signup', data);
    return response.data;
  },
};

// Loan API
export const loanAPI = {
  calculateEMI: async (data: LoanData): Promise<LoanData> => {
    const response = await api.post('/loans/calculate', data);
    return response.data;
  },

  applyLoan: async (data: LoanData): Promise<string> => {
    const response = await api.post('/loans', data);
    return response.data;
  },

  getMyLoans: async (): Promise<LoanData[]> => {
    const response = await api.get('/loans');
    return response.data;
  },

  deleteLoan: async (id: number): Promise<string> => {
    const response = await api.delete(`/loans/${id}`);
    return response.data;
  },
};

export default api;