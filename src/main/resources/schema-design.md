# Smart Clinic Management System - Database Schema Design

## Database: smartclinic

### Table 1: Doctor
```sql
CREATE TABLE Doctor (
    doctorId INT PRIMARY KEY AUTO_INCREMENT,
    doctorName VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    speciality VARCHAR(50) NOT NULL,
    experience INT,
    phone VARCHAR(15),
    registrationNumber VARCHAR(20) UNIQUE NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Table 2: Patient
```sql
CREATE TABLE Patient (
    patientId INT PRIMARY KEY AUTO_INCREMENT,
    patientName VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    dateOfBirth DATE,
    gender VARCHAR(10),
    address VARCHAR(255),
    city VARCHAR(50),
    state VARCHAR(50),
    pincode VARCHAR(6),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Table 3: Appointment
```sql
CREATE TABLE Appointment (
    appointmentId INT PRIMARY KEY AUTO_INCREMENT,
    patientId INT NOT NULL,
    doctorId INT NOT NULL,
    appointmentTime DATETIME NOT NULL,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Patient(patientId) ON DELETE CASCADE,
    FOREIGN KEY (doctorId) REFERENCES Doctor(doctorId) ON DELETE CASCADE,
    UNIQUE KEY unique_appointment (patientId, doctorId, appointmentTime)
);
```

### Table 4: Prescription
```sql
CREATE TABLE Prescription (
    prescriptionId INT PRIMARY KEY AUTO_INCREMENT,
    appointmentId INT NOT NULL,
    patientId INT NOT NULL,
    doctorId INT NOT NULL,
    medicines TEXT NOT NULL,
    dosage TEXT,
    duration TEXT,
    notes TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (appointmentId) REFERENCES Appointment(appointmentId) ON DELETE CASCADE,
    FOREIGN KEY (patientId) REFERENCES Patient(patientId) ON DELETE CASCADE,
    FOREIGN KEY (doctorId) REFERENCES Doctor(doctorId) ON DELETE CASCADE
);
```

### Table 5: DoctorAvailability
```sql
CREATE TABLE DoctorAvailability (
    availabilityId INT PRIMARY KEY AUTO_INCREMENT,
    doctorId INT NOT NULL,
    dayOfWeek INT,
    startTime TIME,
    endTime TIME,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctorId) REFERENCES Doctor(doctorId) ON DELETE CASCADE
);
```

### Table 6: Admin
```sql
CREATE TABLE Admin (
    adminId INT PRIMARY KEY AUTO_INCREMENT,
    adminName VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Indexes
```sql
CREATE INDEX idx_doctor_email ON Doctor(email);
CREATE INDEX idx_patient_email ON Patient(email);
CREATE INDEX idx_appointment_doctorId ON Appointment(doctorId);
CREATE INDEX idx_appointment_patientId ON Appointment(patientId);
CREATE INDEX idx_appointment_time ON Appointment(appointmentTime);
CREATE INDEX idx_prescription_patientId ON Prescription(patientId);
CREATE INDEX idx_prescription_doctorId ON Prescription(doctorId);
```
