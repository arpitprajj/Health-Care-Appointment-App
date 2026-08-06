import React, { useState } from "react";
import { AuthProvider } from "./context/AuthContext";
import { PatientProvider } from "./context/PatientContext";
import { Navbar } from "./components/Navbar";
import { AuthPage } from "./pages/AuthPage";
import { PatientPage } from "./pages/PatientPage";
import { DoctorPage } from "./pages/DoctorPage";
import { BookingPage } from "./pages/BookingPage";
import { AppointmentsPage } from "./pages/AppointmentsPage";
import { PaymentPage } from "./pages/PaymentPage";

export default function App() {
  const [activeTab, setActiveTab] = useState("auth");

  // Holds the appointmentId returned from BookingPage after a successful booking.
  // Passed down to PaymentPage so it auto-fills and the user just clicks Pay Now.
  const [bookedAppointmentId, setBookedAppointmentId] = useState(null);

  // Called by BookingPage when a slot is booked successfully.
  // Switches to the payment tab and carries the appointmentId.
  const handleBooked = (appointmentId) => {
    setBookedAppointmentId(appointmentId);
    setActiveTab("payment");
  };

  const renderContent = () => {
    switch (activeTab) {
      case "auth":
        return <AuthPage />;
      case "patient":
        return <PatientPage />;
      case "doctor":
        return <DoctorPage />;
      case "booking":
        return <BookingPage onBooked={handleBooked} />;
      case "appointments":
        return <AppointmentsPage />;
      case "payment":
        return <PaymentPage appointmentId={bookedAppointmentId} />;
      default:
        return <AuthPage />;
    }
  };

  return (
    <AuthProvider>
      <PatientProvider>
        <div className="app-container">
          <Navbar activeTab={activeTab} setActiveTab={setActiveTab} />
          <main className="main-content">{renderContent()}</main>
        </div>
      </PatientProvider>
    </AuthProvider>
  );
}
