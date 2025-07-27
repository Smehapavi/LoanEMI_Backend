export interface LoanData {
  id?: number;
  principal: number;
  rate: number;
  tenure: number;
  emi?: number;
  totalAmount?: number;
  totalInterest?: number;
}

export interface AuthData {
  username: string;
  password: string;
}

export interface User {
  id: number;
  username: string;
}

export interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (username: string, password: string) => Promise<boolean>;
  signup: (username: string, password: string) => Promise<boolean>;
  logout: () => void;
  loading: boolean;
}