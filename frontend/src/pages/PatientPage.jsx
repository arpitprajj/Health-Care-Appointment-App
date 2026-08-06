import React, { useState, useEffect } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";

export const PatientPage = () => {
  const { userId, isAuthenticated } = useAuth();

  const [patientData, setPatientData] = useState(null);
  const [form, setForm] = useState({
    name: "",
    dateOfBirth: "1995-05-15",
    gender: "MALE",
    bloodGroup: "O+",
    contactNumber: "9876543210",
    address: "123 Health Street, City",
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (userId) {
      fetchPatientProfile(userId);
    }
  }, [userId]);

  const fetchPatientProfile = async (uId) => {
    try {
      const res = await api.patient.getByUserId(uId);
      if (res) {
        setPatientData(res);
        setForm({
          name: res.name || "",
          dateOfBirth: res.dateOfBirth || "1995-05-15",
          gender: res.gender || "MALE",
          bloodGroup: res.bloodGroup || "O+",
          contactNumber: res.contactNumber || "9876543210",
          address: res.address || "123 Health Street, City",
        });
        console.log(res)
      }
    } catch (err) {
      // Profile might not exist yet
      console.log("No existing profile found:", err.message);
    }
  };

  const handleSave = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);
    setError(null);
    try {
      if (patientData && patientData.id) {
        // Update existing patient
        const res = await api.patient.update(patientData.id, form);
        setPatientData(res);

        setMessage("Patient Profile Updated!");
      } else {
        // Create new patient profile
        const payload = {
          userId: userId || "USER_123",
          ...form,
        };
        const res = await api.patient.create(payload);
        setPatientData(res);
        setMessage("Patient Profile Created Successfully!");
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (!isAuthenticated) {
    return (
      <div className="card">
        <div className="alert alert-warning">
          Please login first on the <strong>Authentication</strong> tab to manage your Patient Profile.
        </div>
      </div>
    );
  }

  return (
    <div className="card">
      <h2>Patient Service (`/api/patients`)</h2>

      {message && <div className="alert alert-success">{message}</div>}
      {error && <div className="alert alert-danger">{error}</div>}

      {patientData ? (
        <div className="profile-box">
          <h3>Active Patient Profile</h3>
          <p><strong>Patient ID:</strong> <code>{patientData.userId}</code></p>
          <p><strong>Auth User ID:</strong> {patientData.userId}</p>
          <p><strong>Name:</strong> {patientData.fullName}</p>
          <p><strong>Contact:</strong> {patientData.emergencyContactNumber}</p>
          <p><strong>Blood Group:</strong> {patientData.bloodGroup}</p>
          <p><strong>Address:</strong> {patientData.address}</p>
        </div>
      ) : (
        <p>No profile created yet for your account. Fill out the form below:</p>
      )}

      <form onSubmit={handleSave} className="form" style={{ marginTop: "20px" }}>
        <h3>{patientData ? "Update Patient Profile" : "Create Patient Profile"}</h3>
        
        <div className="form-group">
          <label>Full Name</label>
          <input
            type="text"
            required
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
            placeholder="John Doe"
          />
        </div>

        <div className="form-group">
          <label>Date of Birth</label>
          <input
            type="date"
            required
            value={form.dateOfBirth}
            onChange={(e) => setForm({ ...form, dateOfBirth: e.target.value })}
          />
        </div>

        <div className="form-group">
          <label>Gender</label>
          <select
            value={form.gender}
            onChange={(e) => setForm({ ...form, gender: e.target.value })}
          >
            <option value="MALE">MALE</option>
            <option value="FEMALE">FEMALE</option>
            <option value="OTHER">OTHER</option>
          </select>
        </div>

        <div className="form-group">
          <label>Blood Group</label>
          <input
            type="text"
            required
            value={form.bloodGroup}
            onChange={(e) => setForm({ ...form, bloodGroup: e.target.value })}
            placeholder="O+"
          />
        </div>

        <div className="form-group">
          <label>Contact Number</label>
          <input
            type="text"
            required
            value={form.contactNumber}
            onChange={(e) => setForm({ ...form, contactNumber: e.target.value })}
            placeholder="9876543210"
          />
        </div>

        <div className="form-group">
          <label>Address</label>
          <textarea
            value={form.address}
            onChange={(e) => setForm({ ...form, address: e.target.value })}
            placeholder="Address..."
          />
        </div>

        <button type="submit" className="btn-primary" disabled={loading}>
          {loading ? "Saving..." : patientData ? "Update Profile" : "Create Profile"}
        </button>
      </form>
    </div>
  );
};
