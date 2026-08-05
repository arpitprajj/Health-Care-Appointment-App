import React, { useState } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";

export const AuthPage = () => {
  const { login, isAuthenticated, role, userId, logout } = useAuth();
  const [mode, setMode] = useState("login"); // 'login' or 'register'
  
  // Register state
  const [regForm, setRegForm] = useState({
    email: "",
    password: "",
    phoneNumber: "9876543210",
    role: "PATIENT",
  });

  // Login state
  const [loginForm, setLoginForm] = useState({
    email: "",
    password: "",
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  const handleRegister = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      const res = await api.auth.register(regForm);
      setMessage("Registration Successful!");
      if (res && res.token) {
        login(res);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      const res = await api.auth.login(loginForm);
      setMessage("Login Successful!");
      if (res && res.token) {
        login(res);
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h2>Authentication Service (`/auth`)</h2>

      {isAuthenticated && (
        <div className="alert alert-success">
          <p>
            You are logged in as <strong>{role}</strong> (User ID:{" "}
            <code>{userId}</code>).
          </p>
          <button className="btn-secondary" onClick={logout}>
            Logout
          </button>
        </div>
      )}

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="tab-buttons">
        <button
          className={`btn ${mode === "login" ? "btn-primary" : "btn-secondary"}`}
          onClick={() => setMode("login")}
        >
          Login
        </button>
        <button
          className={`btn ${mode === "register" ? "btn-primary" : "btn-secondary"}`}
          onClick={() => setMode("register")}
        >
          Register
        </button>
      </div>

      {mode === "login" ? (
        <form onSubmit={handleLogin} className="form">
          <h3>Login Form (`POST /auth/login`)</h3>
          <div className="form-group">
            <label>Email Address</label>
            <input
              type="email"
              required
              value={loginForm.email}
              onChange={(e) =>
                setLoginForm({ ...loginForm, email: e.target.value })
              }
              placeholder="patient@example.com"
            />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              required
              value={loginForm.password}
              onChange={(e) =>
                setLoginForm({ ...loginForm, password: e.target.value })
              }
              placeholder="••••••••"
            />
          </div>
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>
      ) : (
        <form onSubmit={handleRegister} className="form">
          <h3>Register Form (`POST /auth/register`)</h3>
          <div className="form-group">
            <label>Email Address</label>
            <input
              type="email"
              required
              value={regForm.email}
              onChange={(e) =>
                setRegForm({ ...regForm, email: e.target.value })
              }
              placeholder="newuser@example.com"
            />
          </div>
          <div className="form-group">
            <label>Phone Number (10 digits starting with 6-9)</label>
            <input
              type="text"
              required
              value={regForm.phoneNumber}
              onChange={(e) =>
                setRegForm({ ...regForm, phoneNumber: e.target.value })
              }
              placeholder="9876543210"
            />
          </div>
          <div className="form-group">
            <label>Password (min 6 chars)</label>
            <input
              type="password"
              required
              value={regForm.password}
              onChange={(e) =>
                setRegForm({ ...regForm, password: e.target.value })
              }
              placeholder="••••••••"
            />
          </div>
          <div className="form-group">
            <label>Role</label>
            <select
              value={regForm.role}
              onChange={(e) =>
                setRegForm({ ...regForm, role: e.target.value })
              }
            >
              <option value="PATIENT">PATIENT</option>
              <option value="DOCTOR">DOCTOR</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </div>
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? "Registering..." : "Register"}
          </button>
        </form>
      )}
    </div>
  );
};
