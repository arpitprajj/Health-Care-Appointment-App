import React, { useState } from "react";
import { AuthProvider } from "./context/AuthContext";
import { Navbar } from "./components/Navbar";
import { AuthPage } from "./pages/AuthPage";
import { PatientPage } from "./pages/PatientPage";
import { DoctorPage } from "./pages/DoctorPage";
import { BookingPage } from "./pages/BookingPage";
import { AppointmentsPage } from "./pages/AppointmentsPage";
import { PaymentPage } from "./pages/PaymentPage";

export default function App() {
  const [activeTab, setActiveTab] = useState("auth");

  const renderContent = () => {
    switch (activeTab) {
      case "auth":
        return <AuthPage />;
      case "patient":
        return <PatientPage />;
      case "doctor":
        return <DoctorPage />;
      case "booking":
        return <BookingPage />;
      case "appointments":
        return <AppointmentsPage />;
      case "payment":
        return <PaymentPage />;
      default:
        return <AuthPage />;
    }
  };

  return (
    <AuthProvider>
      <div className="app-container">
        <Navbar activeTab={activeTab} setActiveTab={setActiveTab} />
        <main className="main-content">{renderContent()}</main>
      </div>
    </AuthProvider>
  );
}
