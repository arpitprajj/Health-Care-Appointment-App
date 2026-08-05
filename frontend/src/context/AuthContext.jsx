import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem("token") || "");
  const [role, setRole] = useState(localStorage.getItem("role") || "");
  const [userId, setUserId] = useState(localStorage.getItem("userId") || "");
  const [email, setEmail] = useState(localStorage.getItem("email") || "");

  const login = (authData) => {
    const { token, role, userId, email } = authData;
    if (token) {
      localStorage.setItem("token", token);
      setToken(token);
    }
    if (role) {
      localStorage.setItem("role", role);
      setRole(role);
    }
    if (userId) {
      localStorage.setItem("userId", userId);
      setUserId(userId);
    }
    if (email) {
      localStorage.setItem("email", email);
      setEmail(email);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("userId");
    localStorage.removeItem("email");
    setToken("");
    setRole("");
    setUserId("");
    setEmail("");
  };

  return (
    <AuthContext.Provider
      value={{
        token,
        role,
        userId,
        email,
        isAuthenticated: !!token,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
