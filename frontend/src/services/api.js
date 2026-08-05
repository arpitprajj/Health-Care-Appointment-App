const BASE_URL = "http://localhost:5000";

async function request(endpoint, options = {}) {
  const token = localStorage.getItem("token");
  const headers = {
    "Content-Type": "application/json",
    ...(options.headers || {}),
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const response = await fetch(`${BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    let errorMessage = `Error ${response.status}: ${response.statusText}`;
    try {
      const errorData = await response.json();
      if (errorData.message) errorMessage = errorData.message;
      else if (typeof errorData === "string") errorMessage = errorData;
    } catch (e) {
      // Ignore JSON parse errors for non-JSON response bodies
    }
    throw new Error(errorMessage);
  }

  // If no content (e.g., 204 or empty response)
  const contentType = response.headers.get("content-type");
  if (contentType && contentType.includes("application/json")) {
    return await response.json();
  }
  return null;
}

export const api = {
  // Auth Service
  auth: {
    register: (data) =>
      request("/auth/register", {
        method: "POST",
        body: JSON.stringify(data),
      }),
    login: (data) =>
      request("/auth/login", {
        method: "POST",
        body: JSON.stringify(data),
      }),
  },

  // Patient Service
  patient: {
    create: (data) =>
      request("/api/patients", {
        method: "POST",
        body: JSON.stringify(data),
      }),
    getById: (patientId) => request(`/api/patients/${patientId}`),
    getByUserId: (userId) => request(`/api/patients/user/${userId}`),
    update: (patientId, data) =>
      request(`/api/patients/${patientId}`, {
        method: "PUT",
        body: JSON.stringify(data),
      }),
    delete: (patientId) =>
      request(`/api/patients/${patientId}`, {
        method: "DELETE",
      }),
  },

  // Doctor Service
  doctor: {
    create: (data) =>
      request("/api/doctors", {
        method: "POST",
        body: JSON.stringify(data),
      }),
    getAll: () => request("/api/doctors"),
    getById: (doctorId) => request(`/api/doctors/${doctorId}`),
    update: (doctorId, data) =>
      request(`/api/doctors/${doctorId}`, {
        method: "PUT",
        body: JSON.stringify(data),
      }),
    delete: (doctorId) =>
      request(`/api/doctors/${doctorId}`, {
        method: "DELETE",
      }),
  },

  // Slot Service
  slot: {
    generate: (data) =>
      request("/api/slots/generate", {
        method: "POST",
        body: JSON.stringify(data),
      }),
    getByDoctorAndDate: (doctorId, date) =>
      request(`/api/slots/doctor/${doctorId}?date=${date}`),
    getByDoctorAndStatus: (doctorId, status) =>
      request(`/api/slots/status/${doctorId}?status=${status}`),
    getById: (slotId) => request(`/api/slots/${slotId}`),
    reserve: (slotId, patientId, appointmentId) =>
      request(
        `/api/slots/${slotId}/reserve?patientId=${patientId}&appointmentId=${appointmentId}`,
        { method: "PATCH" }
      ),
    book: (slotId, appointmentId) =>
      request(`/api/slots/${slotId}/book?appointmentId=${appointmentId}`, {
        method: "PATCH",
      }),
    release: (slotId, appointmentId) =>
      request(`/api/slots/${slotId}/release?appointmentId=${appointmentId}`, {
        method: "PATCH",
      }),
  },

  // Appointment Service
  appointment: {
    create: (slotId) =>
      request(`/api/appointments/slots/${slotId}`, {
        method: "POST",
      }),
    getById: (appointmentId) => request(`/api/appointments/${appointmentId}`),
    getByPatientId: (patientId) =>
      request(`/api/appointments/patient/${patientId}`),
    getByDoctorId: (doctorId) =>
      request(`/api/appointments/doctor/${doctorId}`),
    cancel: (appointmentId) =>
      request(`/api/appointments/${appointmentId}/cancel`, {
        method: "PATCH",
      }),
  },

  // Payment Service
  payment: {
    createOrder: (appointmentId, paymentMethod = "UPI") =>
      request("/api/payments/create-order", {
        method: "POST",
        body: JSON.stringify({ appointmentId, paymentMethod }),
      }),
    verify: (data) =>
      request("/api/payments/verify", {
        method: "POST",
        body: JSON.stringify(data),
      }),
    getById: (paymentId) => request(`/api/payments/${paymentId}`),
  },
};
