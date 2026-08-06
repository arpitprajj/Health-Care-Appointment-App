import React, { useState, useEffect } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";
import { usePatient } from "../context/PatientContext";

export const AppointmentsPage = () => {
  const { isAuthenticated, role } = useAuth();
  // patientId from PatientContext (fetched right after login)
  const { patientId } = usePatient();

  const [lookupId, setLookupId] = useState("");
  const [lookupType, setLookupType] = useState("patient"); // 'patient' or 'doctor'
  const [appointments, setAppointments] = useState([]);

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  // Auto-populate lookupId with patientId when it becomes available
  useEffect(() => {
    if (patientId && lookupType === "patient") {
      setLookupId(patientId);
    }
  }, [patientId, lookupType]);

  const fetchAppointments = async (e) => {
    if (e) e.preventDefault();
    if (!lookupId) {
      setError("Please enter a Patient ID or Doctor ID to fetch appointments.");
      return;
    }

    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      let res;
      if (lookupType === "patient") {
        res = await api.appointment.getByPatientId(lookupId);
      } else {
        res = await api.appointment.getByDoctorId(lookupId);
      }
      setAppointments(Array.isArray(res) ? res : []);
    } catch (err) {
      setError(err.message);
      setAppointments([]);
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = async (appointmentId) => {
    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      await api.appointment.cancel(appointmentId);
      setMessage(`Appointment ${appointmentId} Cancelled Successfully.`);
      fetchAppointments();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h2>Appointment History (`/api/appointments`)</h2>

      {!isAuthenticated && (
        <div className="alert alert-warning">
          Please login on the <strong>Authentication</strong> tab to view appointments.
        </div>
      )}

      {/* Show resolved patientId info */}
      {patientId && (
        <div className="alert alert-success" style={{ marginBottom: "1rem" }}>
          Your Patient ID: <code><strong>{patientId}</strong></code> — loaded automatically.
        </div>
      )}

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <form onSubmit={fetchAppointments} className="form-inline">
        <div className="form-group">
          <label>View History For</label>
          <select
            value={lookupType}
            onChange={(e) => {
              setLookupType(e.target.value);
              // Auto-fill patientId when switching to patient mode
              if (e.target.value === "patient" && patientId) {
                setLookupId(patientId);
              } else if (e.target.value === "doctor") {
                setLookupId("");
              }
            }}
          >
            <option value="patient">Patient ID</option>
            <option value="doctor">Doctor ID</option>
          </select>
        </div>

        <div className="form-group">
          <label>{lookupType === "patient" ? "Patient ID" : "Doctor ID"}</label>
          <input
            type="text"
            required
            value={lookupId}
            onChange={(e) => setLookupId(e.target.value)}
            placeholder={lookupType === "patient" ? "Patient UUID" : "Doctor ID"}
          />
        </div>

        <button type="submit" className="btn-primary" disabled={loading}>
          {loading ? "Fetching..." : "Fetch Appointments"}
        </button>
      </form>

      <div style={{ marginTop: "30px" }}>
        <h3>Appointment List</h3>
        {appointments.length === 0 ? (
          <p>No appointments found. Click <strong>Fetch Appointments</strong> to load.</p>
        ) : (
          <table className="table">
            <thead>
              <tr>
                <th>Appointment ID</th>
                <th>Patient ID</th>
                <th>Doctor ID</th>
                <th>Slot ID</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {appointments.map((apt) => {
                const id = apt.id || apt.appointmentId;
                const status = apt.status;
                return (
                  <tr key={id}>
                    <td><code>{id}</code></td>
                    <td>{apt.patientId}</td>
                    <td>{apt.doctorId}</td>
                    <td>{apt.slotId}</td>
                    <td>
                      <span
                        className={`badge ${
                          status === "CONFIRMED"
                            ? "badge-success"
                            : status === "CANCELLED"
                            ? "badge-danger"
                            : "badge-warning"
                        }`}
                      >
                        {status}
                      </span>
                    </td>
                    <td>
                      {status !== "CANCELLED" && (
                        <button
                          className="btn-danger btn-sm"
                          onClick={() => handleCancel(id)}
                          disabled={loading}
                        >
                          Cancel
                        </button>
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
};
