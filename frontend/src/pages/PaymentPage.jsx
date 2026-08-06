import React, { useState, useEffect } from "react";
import { api } from "../services/api";

// appointmentId prop is passed in by App.jsx when redirected from BookingPage
export const PaymentPage = ({ appointmentId: initialAppointmentId }) => {
  const [appointmentId, setAppointmentId] = useState(initialAppointmentId || "");
  const [paymentMethod, setPaymentMethod] = useState("UPI");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  // If parent passes a new appointmentId (after redirect from booking), update it
  useEffect(() => {
    if (initialAppointmentId) {
      setAppointmentId(initialAppointmentId);
      setMessage(null);
      setError(null);
    }
  }, [initialAppointmentId]);

  const handlePayNow = async (e) => {
    e.preventDefault();
    if (!appointmentId) {
      setError("Please enter a valid Appointment ID.");
      return;
    }

    setLoading(true);
    setMessage(null);
    setError(null);

    try {
      // 1. Create Razorpay order
      const order = await api.payment.createOrder(appointmentId, paymentMethod);

      if (!order || !order.razorpayOrderId) {
        throw new Error("Failed to create payment order. Check that the appointment exists and is in CREATED status.");
      }

      setMessage("Razorpay Order created! Opening checkout window...");

      // 2. Open Razorpay Checkout modal
      if (window.Razorpay) {
        const options = {
          key: order.key,
          amount: order.amount,
          currency: order.currency || "INR",
          name: "Healthcare Appointment",
          description: "Doctor Consultation Fee",
          order_id: order.razorpayOrderId,
          handler: async function (response) {
            try {
              // 3. Verify payment with backend
              await api.payment.verify({
                appointmentId: order.appointmentId || appointmentId,
                razorpayOrderId: response.razorpay_order_id,
                razorpayPaymentId: response.razorpay_payment_id,
                razorpaySignature: response.razorpay_signature,
              });
              setMessage("✅ Payment Verified! Appointment is now CONFIRMED.");
            } catch (err) {
              setError("Payment verification failed: " + err.message);
            }
          },
          prefill: {
            name: "Patient",
            email: "patient@example.com",
            contact: "9876543210",
          },
          theme: {
            color: "#0066cc",
          },
        };

        const rzp = new window.Razorpay(options);
        rzp.open();
      } else {
        setError("Razorpay SDK not loaded. Ensure the script tag is present in index.html.");
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h2>Payment Service (`/api/payments`)</h2>

      {/* Show banner when auto-navigated from booking */}
      {initialAppointmentId && (
        <div className="alert alert-success" style={{ marginBottom: "1rem" }}>
          Appointment booked! Appointment ID <code><strong>{initialAppointmentId}</strong></code> has been auto-filled below. Select a payment method and click Pay Now.
        </div>
      )}

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={handlePayNow} className="form">
        <h3>Make Appointment Payment</h3>

        <div className="form-group">
          <label>Appointment ID</label>
          <input
            type="text"
            required
            value={appointmentId}
            onChange={(e) => setAppointmentId(e.target.value)}
            placeholder="UUID of booked appointment"
          />
          {initialAppointmentId && (
            <small style={{ color: "#6b7280" }}>Auto-filled from your slot booking.</small>
          )}
        </div>

        <div className="form-group">
          <label>Payment Method</label>
          <select
            value={paymentMethod}
            onChange={(e) => setPaymentMethod(e.target.value)}
          >
            <option value="UPI">UPI</option>
            <option value="CARD">Credit / Debit Card</option>
            <option value="NETBANKING">Netbanking</option>
          </select>
        </div>

        <button type="submit" className="btn-primary" disabled={loading}>
          {loading ? "Processing..." : "Pay Now (Razorpay Checkout)"}
        </button>
      </form>
    </div>
  );
};
