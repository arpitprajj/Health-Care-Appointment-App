import React from "react";
import { useAuth } from "../context/AuthContext";

export const Navbar = ({ activeTab, setActiveTab }) => {
  const { isAuthenticated, role, email, logout } = useAuth();

  const tabs = [
    { id: "auth", label: "Authentication" },
    { id: "patient", label: "Patient Profile" },
    { id: "doctor", label: "Doctor Management" },
    { id: "booking", label: "Book Slots" },
    { id: "appointments", label: "My Appointments" },
    { id: "payment", label: "Payments" },
  ];

  return (
    <header className="navbar">
      <div className="nav-brand">
        <h2>Healthcare Appointment Portal</h2>
      </div>

      <nav className="nav-tabs">
        {tabs.map((tab) => (
          <button
            key={tab.id}
            className={`nav-tab ${activeTab === tab.id ? "active" : ""}`}
            onClick={() => setActiveTab(tab.id)}
          >
            {tab.label}
          </button>
        ))}
      </nav>

      <div className="nav-user">
        {isAuthenticated ? (
          <div className="user-info">
            <span>
              <strong>{role}</strong> ({email || "Logged In"})
            </span>
            <button className="btn-secondary" onClick={logout}>
              Logout
            </button>
          </div>
        ) : (
          <span className="badge badge-warning">Not Logged In</span>
        )}
      </div>
    </header>
  );
};
