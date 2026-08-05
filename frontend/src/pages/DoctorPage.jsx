import React, { useState, useEffect } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";

// Helper function to safely format LocalTime (handles both "09:00:00" and [9, 0])
export const formatTime = (timeVal) => {
  if (!timeVal) return "";
  if (Array.isArray(timeVal)) {
    const hh = String(timeVal[0]).padStart(2, "0");
    const mm = String(timeVal[1] || 0).padStart(2, "0");
    return `${hh}:${mm}`;
  }
  return String(timeVal);
};

// Helper function to safely format LocalDate (handles both "2026-08-04" and [2026, 8, 4])
export const formatDate = (dateVal) => {
  if (!dateVal) return "";
  if (Array.isArray(dateVal)) {
    const yyyy = dateVal[0];
    const mm = String(dateVal[1]).padStart(2, "0");
    const dd = String(dateVal[2]).padStart(2, "0");
    return `${yyyy}-${mm}-${dd}`;
  }
  return String(dateVal);
};

export const DoctorPage = () => {
  const { userId } = useAuth();

  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  // Doctor Registration Form
  const [docForm, setDocForm] = useState({
    name: "",
    specialization: "Cardiology",
    qualification: "MD, MBBS",
    experienceYears: 5,
    consultationFee: 500,
    contactNumber: "9876543210",
    email: "",
  });

  // Slot Generator Form: Expects doctorId, startDate, and endDate
  const [slotForm, setSlotForm] = useState({
    doctorId: "",
    startDate: new Date().toISOString().split("T")[0],
    endDate: new Date().toISOString().split("T")[0],
  });

  // View Slots State
  const [viewDoctorId, setViewDoctorId] = useState("");
  const [viewDate, setViewDate] = useState(new Date().toISOString().split("T")[0]);
  const [generatedSlots, setGeneratedSlots] = useState([]);
  const [slotsLoading, setSlotsLoading] = useState(false);

  useEffect(() => {
    fetchDoctors();
  }, []);

  const fetchDoctors = async () => {
    setLoading(true);
    try {
      const res = await api.doctor.getAll();
      if (res && Array.isArray(res)) {
        setDoctors(res);
        if (res.length > 0) {
          const firstDocId = res[0].id || res[0].doctorId || "";
          setSlotForm((prev) => ({ ...prev, doctorId: firstDocId }));
          setViewDoctorId(firstDocId);
        }
      }
    } catch (err) {
      console.error("Error fetching doctors:", err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateDoctor = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      const payload = {
        userId: userId || `DOC_${Date.now()}`,
        ...docForm,
      };
      const res = await api.doctor.create(payload);
      const createdId = res.id || res.doctorId || "Success";
      setMessage(`Doctor Profile Created! Doctor ID: ${createdId}`);
      fetchDoctors();
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleGenerateSlots = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      const payload = {
        doctorId: slotForm.doctorId,
        startDate: slotForm.startDate,
        endDate: slotForm.endDate,
      };
      await api.slot.generate(payload);
      setMessage(
        `Slots generated successfully for Doctor ${slotForm.doctorId} from ${slotForm.startDate} to ${slotForm.endDate}!`
      );
      // Auto fetch generated slots
      setViewDoctorId(slotForm.doctorId);
      setViewDate(slotForm.startDate);
      fetchSlotsForDoctor(slotForm.doctorId, slotForm.startDate);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchSlotsForDoctor = async (docId, date) => {
    if (!docId) return;
    setSlotsLoading(true);
    try {
      const res = await api.slot.getByDoctorAndDate(docId, date);
      setGeneratedSlots(Array.isArray(res) ? res : []);
    } catch (err) {
      console.error("Error fetching slots:", err.message);
      setGeneratedSlots([]);
    } finally {
      setSlotsLoading(false);
    }
  };

  return (
    <div className="card">
      <h2>Doctor Management & Slot Generation (`/api/doctors`, `/api/slots/generate`)</h2>

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      <div className="grid-2">
        <div>
          <h3>1. Create Doctor Profile (`POST /api/doctors`)</h3>
          <form onSubmit={handleCreateDoctor} className="form">
            <div className="form-group">
              <label>Doctor Name</label>
              <input
                type="text"
                required
                value={docForm.name}
                onChange={(e) => setDocForm({ ...docForm, name: e.target.value })}
                placeholder="Dr. Smith"
              />
            </div>
            <div className="form-group">
              <label>Specialization</label>
              <input
                type="text"
                required
                value={docForm.specialization}
                onChange={(e) =>
                  setDocForm({ ...docForm, specialization: e.target.value })
                }
              />
            </div>
            <div className="form-group">
              <label>Qualification</label>
              <input
                type="text"
                required
                value={docForm.qualification}
                onChange={(e) =>
                  setDocForm({ ...docForm, qualification: e.target.value })
                }
              />
            </div>
            <div className="form-group">
              <label>Experience (Years)</label>
              <input
                type="number"
                required
                value={docForm.experienceYears}
                onChange={(e) =>
                  setDocForm({
                    ...docForm,
                    experienceYears: parseInt(e.target.value) || 0,
                  })
                }
              />
            </div>
            <div className="form-group">
              <label>Consultation Fee (₹)</label>
              <input
                type="number"
                required
                value={docForm.consultationFee}
                onChange={(e) =>
                  setDocForm({
                    ...docForm,
                    consultationFee: parseFloat(e.target.value) || 0,
                  })
                }
              />
            </div>
            <div className="form-group">
              <label>Contact Number</label>
              <input
                type="text"
                required
                value={docForm.contactNumber}
                onChange={(e) =>
                  setDocForm({ ...docForm, contactNumber: e.target.value })
                }
              />
            </div>
            <div className="form-group">
              <label>Email</label>
              <input
                type="email"
                required
                value={docForm.email}
                onChange={(e) => setDocForm({ ...docForm, email: e.target.value })}
              />
            </div>
            <button type="submit" className="btn-primary" disabled={loading}>
              {loading ? "Creating..." : "Create Doctor Profile"}
            </button>
          </form>
        </div>

        <div>
          <h3>2. Generate Slots (`POST /api/slots/generate`)</h3>
          <form onSubmit={handleGenerateSlots} className="form">
            <div className="form-group">
              <label>Select / Enter Doctor ID</label>
              <input
                type="text"
                required
                value={slotForm.doctorId}
                onChange={(e) =>
                  setSlotForm({ ...slotForm, doctorId: e.target.value })
                }
                placeholder="Doctor ID"
              />
            </div>
            <div className="form-group">
              <label>Start Date</label>
              <input
                type="date"
                required
                value={slotForm.startDate}
                onChange={(e) =>
                  setSlotForm({ ...slotForm, startDate: e.target.value })
                }
              />
            </div>
            <div className="form-group">
              <label>End Date</label>
              <input
                type="date"
                required
                value={slotForm.endDate}
                onChange={(e) =>
                  setSlotForm({ ...slotForm, endDate: e.target.value })
                }
              />
            </div>
            <button type="submit" className="btn-primary" disabled={loading}>
              {loading ? "Generating..." : "Generate Daily Slots"}
            </button>
          </form>
        </div>
      </div>

      {/* Registered Doctors Table */}
      <div style={{ marginTop: "30px" }}>
        <h3>Registered Doctors</h3>
        {doctors.length === 0 ? (
          <p>No doctors found.</p>
        ) : (
          <table className="table">
            <thead>
              <tr>
                <th>Doctor ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Fee</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {doctors.map((doc) => {
                const id = doc.id || doc.doctorId;
                return (
                  <tr key={id}>
                    <td><code>{id}</code></td>
                    <td>{doc.name}</td>
                    <td>{doc.specialization}</td>
                    <td>₹{doc.consultationFee}</td>
                    <td>
                      <button
                        className="btn-secondary btn-sm"
                        onClick={() => {
                          setSlotForm((prev) => ({ ...prev, doctorId: id }));
                          setViewDoctorId(id);
                          fetchSlotsForDoctor(id, viewDate);
                        }}
                      >
                        Select Doctor
                      </button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        )}
      </div>

      {/* Slots Viewer Section */}
      <div style={{ marginTop: "30px", borderTop: "2px solid #e5e7eb", paddingTop: "20px" }}>
        <h3>3. View Slots for Doctor (`GET /api/slots/doctor/{`{doctorId}`}?date=...`)</h3>

        <div className="form-inline" style={{ marginBottom: "15px" }}>
          <div className="form-group">
            <label>Doctor ID</label>
            <input
              type="text"
              value={viewDoctorId}
              onChange={(e) => setViewDoctorId(e.target.value)}
              placeholder="Doctor ID"
            />
          </div>
          <div className="form-group">
            <label>Date</label>
            <input
              type="date"
              value={viewDate}
              onChange={(e) => setViewDate(e.target.value)}
            />
          </div>
          <button
            className="btn-primary"
            onClick={() => fetchSlotsForDoctor(viewDoctorId, viewDate)}
            disabled={slotsLoading}
          >
            {slotsLoading ? "Loading..." : "Fetch Slots"}
          </button>
        </div>

        {generatedSlots.length === 0 ? (
          <p>No slots found for Doctor ID <code>{viewDoctorId || "N/A"}</code> on date {viewDate}. Generate slots using the form above.</p>
        ) : (
          <div className="slots-grid">
            {generatedSlots.map((slot) => (
              <div key={slot.id} className="slot-card slot-available">
                <p><strong>Slot ID:</strong> <code>{String(slot.id).substring(0, 8)}...</code></p>
                <p><strong>Date:</strong> {formatDate(slot.slotDate)}</p>
                <p>⏱ <strong>{formatTime(slot.startTime)} - {formatTime(slot.endTime)}</strong></p>
                <p>
                  Status:{" "}
                  <span className={`badge ${slot.status === "AVAILABLE" ? "badge-success" : "badge-warning"}`}>
                    {slot.status}
                  </span>
                </p>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
