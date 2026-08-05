import React, { useState } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";

export const PaymentPage = () => {
  const { isAuthenticated, token } = useAuth();

  const [appointmentId, setAppointmentId] = useState("");
  const [paymentMethod, setPaymentMethod] = useState("UPI");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

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
      // 1. Call Payment Service to create Razorpay Order
      const order = await api.payment.createOrder(appointmentId, paymentMethod);

      if (!order || !order.razorpayOrderId) {
        throw new Error("Failed to create order from Payment Service.");
      }

      setMessage("Razorpay Order Created! Opening Checkout window...");

      // 2. Open Razorpay Checkout standard popup if Razorpay SDK is loaded
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
              // 3. Verify Payment
              const verifyRes = await api.payment.verify({
                appointmentId: order.appointmentId || appointmentId,
                razorpayOrderId: response.razorpay_order_id,
                razorpayPaymentId: response.razorpay_payment_id,
                razorpaySignature: response.razorpay_signature,
              });
              setMessage("Payment Verified Successfully! Appointment Confirmed.");
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
        setError("Razorpay SDK not loaded in browser. Check index.html script tag.");
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
            placeholder="Enter UUID of booked appointment"
          />
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
