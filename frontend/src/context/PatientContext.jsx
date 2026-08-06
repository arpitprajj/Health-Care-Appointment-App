import React, { createContext, useContext, useState } from "react";
import { api } from "../services/api";

const PatientContext = createContext(null);

export const PatientProvider = ({ children }) => {
  const [patientId, setPatientId] = useState(
    localStorage.getItem("patientId") || ""
  );
  const [patientData, setPatientData] = useState(null);

  // Called right after login with the userId returned by the auth service
  const fetchAndStorePatient = async (userId) => {
    if (!userId) return;
    try {
      const res = await api.patient.getByUserId(userId);
      if (res) {
        const pid = res.id || res.patientId || "";
        setPatientId(pid);
        setPatientData(res);
        if (pid) localStorage.setItem("patientId", pid);
      }
    } catch (err) {
      // Patient profile may not exist yet (new user, or a DOCTOR role)
      console.log("No patient profile found for userId:", userId, err.message);
    }
  };

  const clearPatient = () => {
    setPatientId("");
    setPatientData(null);
    localStorage.removeItem("patientId");
  };

  return (
    <PatientContext.Provider
      value={{ patientId, patientData, fetchAndStorePatient, clearPatient }}
    >
      {children}
    </PatientContext.Provider>
  );
};

export const usePatient = () => useContext(PatientContext);
