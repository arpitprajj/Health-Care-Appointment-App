import React, { useState, useEffect } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";
import { formatTime, formatDate } from "./DoctorPage";

// onBooked(appointmentId) is called by App.jsx to redirect to payment
export const BookingPage = ({ onBooked }) => {
  const { isAuthenticated } = useAuth();

  const [doctors, setDoctors] = useState([]);
  const [selectedDoctor, setSelectedDoctor] = useState("");
  const [searchMode, setSearchMode] = useState("date"); // 'date' or 'status'
  const [selectedDate, setSelectedDate] = useState(
    new Date().toISOString().split("T")[0]
  );
  const [selectedStatus, setSelectedStatus] = useState("AVAILABLE");

  const [slots, setSlots] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);
  const [bookingInProgress, setBookingInProgress] = useState(null); // slotId being booked

  useEffect(() => {
    fetchDoctors();
  }, []);

  const fetchDoctors = async () => {
    try {
      const res = await api.doctor.getAll();
      if (res && Array.isArray(res) && res.length > 0) {
        setDoctors(res);
        const firstDocId = res[0].id || res[0].doctorId;
        setSelectedDoctor(firstDocId);
      }
    } catch (err) {
      console.error("Error fetching doctors:", err.message);
    }
  };

  const handleSearchSlots = async (e) => {
    if (e) e.preventDefault();
    if (!selectedDoctor) {
      setError("Please select or enter a Doctor ID.");
      return;
    }

    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      let res;
      if (searchMode === "date") {
        res = await api.slot.getByDoctorAndDate(selectedDoctor, selectedDate);
      } else {
        res = await api.slot.getByDoctorAndStatus(selectedDoctor, selectedStatus);
      }

      const slotsList = Array.isArray(res) ? res : [];
      setSlots(slotsList);

      if (slotsList.length === 0) {
        setMessage(
          `No ${searchMode === "date" ? `slots on ${selectedDate}` : `${selectedStatus} slots`} found for Doctor ID ${selectedDoctor}.`
        );
      }
    } catch (err) {
      setError(err.message);
      setSlots([]);
    } finally {
      setLoading(false);
    }
  };

  const handleBookSlot = async (slotId) => {
    if (!isAuthenticated) {
      setError("Please login first on Authentication tab to book an appointment.");
      return;
    }

    setBookingInProgress(slotId);
    setMessage(null);
    setError(null);
    try {
      // POST /api/appointments/slots/{slotId} → returns appointment with id
      const appointment = await api.appointment.create(slotId);
      const aptId = appointment.id || appointment.appointmentId;

      setMessage(`Appointment booked! Redirecting to Payment for Appointment ID: ${aptId}`);

      // Give a short moment for the user to see the message, then redirect
      setTimeout(() => {
        if (onBooked) {
          onBooked(aptId); // tells App.jsx to switch to payment tab with this appointmentId
        }
      }, 1200);
    } catch (err) {
      setError(err.message);
    } finally {
      setBookingInProgress(null);
    }
  };

  return (
    <div className="card">
      <h2>Search & Book Slots</h2>
      <p style={{ color: "#6b7280", marginBottom: "1rem", fontSize: "0.9rem" }}>
        Find available slots, book one, and you'll be automatically redirected to the Payment page.
      </p>

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={handleSearchSlots} className="form-inline">
        <div className="form-group">
          <label>Select / Enter Doctor</label>
          {doctors.length > 0 ? (
            <select
              value={selectedDoctor}
              onChange={(e) => setSelectedDoctor(e.target.value)}
            >
              {doctors.map((d) => {
                const id = d.id || d.doctorId;
                return (
                  <option key={id} value={id}>
                    {d.name} ({d.specialization}) - ID: {id}
                  </option>
                );
              })}
            </select>
          ) : (
            <input
              type="text"
              placeholder="Doctor ID"
              value={selectedDoctor}
              onChange={(e) => setSelectedDoctor(e.target.value)}
            />
          )}
        </div>

        <div className="form-group">
          <label>Search By</label>
          <select
            value={searchMode}
            onChange={(e) => setSearchMode(e.target.value)}
          >
            <option value="date">Date</option>
            <option value="status">Status</option>
          </select>
        </div>

        {searchMode === "date" ? (
          <div className="form-group">
            <label>Date</label>
            <input
              type="date"
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
            />
          </div>
        ) : (
          <div className="form-group">
            <label>Status</label>
            <select
              value={selectedStatus}
              onChange={(e) => setSelectedStatus(e.target.value)}
            >
              <option value="AVAILABLE">AVAILABLE</option>
              <option value="RESERVED">RESERVED</option>
              <option value="BOOKED">BOOKED</option>
            </select>
          </div>
        )}

        <button type="submit" className="btn-primary" disabled={loading}>
          {loading ? "Searching..." : "Find Slots"}
        </button>
      </form>

      <div style={{ marginTop: "30px" }}>
        <h3>Slots ({slots.length})</h3>
        {slots.length === 0 ? (
          <p style={{ marginTop: "10px" }}>
            No slots to display. Make sure slots have been generated for Doctor ID{" "}
            <code>{selectedDoctor || "N/A"}</code> under the{" "}
            <strong>Doctor Management</strong> tab.
          </p>
        ) : (
          <div className="slots-grid">
            {slots.map((slot) => {
              const isAvailable = slot.status === "AVAILABLE";
              const isBookingThis = bookingInProgress === slot.id;

              return (
                <div
                  key={slot.id}
                  className={`slot-card ${isAvailable ? "slot-available" : "slot-unavailable"}`}
                >
                  <p>
                    <strong>Date:</strong> {formatDate(slot.slotDate)}
                  </p>
                  <p className="slot-time">
                    ⏱ <strong>{formatTime(slot.startTime)} - {formatTime(slot.endTime)}</strong>
                  </p>
                  <p>
                    Status:{" "}
                    <span
                      className={`badge ${
                        isAvailable
                          ? "badge-success"
                          : slot.status === "BOOKED"
                          ? "badge-danger"
                          : "badge-warning"
                      }`}
                    >
                      {slot.status}
                    </span>
                  </p>
                  {isAvailable && (
                    <button
                      className="btn-primary btn-sm"
                      onClick={() => handleBookSlot(slot.id)}
                      disabled={bookingInProgress !== null}
                    >
                      {isBookingThis ? "Booking..." : "Book & Pay →"}
                    </button>
                  )}
                </div>
              );
            })}
          </div>
        )}
      </div>
    </div>
  );
};
