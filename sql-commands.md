# Smart Clinic Management System - SQL Commands and Outputs

## Question 19: Show Tables
```sql
SHOW TABLES;
```

### Expected Output:
```
+---------------------------+
| Tables_in_smartclinic     |
+---------------------------+
| Admin                     |
| Appointment               |
| Doctor                    |
| DoctorAvailability        |
| Patient                   |
| Prescription              |
| doctor_available_times    |
+---------------------------+
7 rows in set (0.00 sec)
```

## Question 20: Display 5 Patient Records
```sql
SELECT * FROM Patient LIMIT 5;
```

### Sample Output:
```
+-----------+----------------+---------------------+
| patientId | patientName    | email               |
+-----------+----------------+---------------------+
| 1         | John Doe       | john@example.com    |
| 2         | Jane Smith     | jane@example.com    |
| 3         | Bob Johnson    | bob@example.com     |
| 4         | Alice Brown    | alice@example.com   |
| 5         | Charlie Wilson | charlie@example.com |
+-----------+----------------+---------------------+
5 rows in set (0.00 sec)
```

## Question 21: GetDailyAppointmentReportByDoctor Stored Procedure
```sql
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(
    IN p_doctorId INT,
    IN p_appointmentDate DATE
)
BEGIN
    SELECT 
        d.doctorId,
        d.doctorName,
        d.speciality,
        COUNT(a.appointmentId) as total_appointments,
        SUM(CASE WHEN a.status = 'COMPLETED' THEN 1 ELSE 0 END) as completed,
        SUM(CASE WHEN a.status = 'CANCELLED' THEN 1 ELSE 0 END) as cancelled
    FROM Doctor d
    LEFT JOIN Appointment a ON d.doctorId = a.doctorId
    WHERE d.doctorId = p_doctorId
    AND DATE(a.appointmentTime) = p_appointmentDate
    GROUP BY d.doctorId, d.doctorName, d.speciality;
END;

CALL GetDailyAppointmentReportByDoctor(1, '2025-12-26');
```

### Sample Output:
```
+----------+-----------+----------+--------------------+-----------+-----------+
| doctorId | doctorName| speciality | total_appointments | completed | cancelled |
+----------+-----------+----------+--------------------+-----------+-----------+
| 1        | Dr. Smith | Cardiology | 5                  | 4         | 1         |
+----------+-----------+----------+--------------------+-----------+-----------+
1 row in set (0.00 sec)
```

## Question 22: GetDoctorWithMostPatientsByMonth Stored Procedure
```sql
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(
    IN p_month INT,
    IN p_year INT
)
BEGIN
    SELECT 
        d.doctorId,
        d.doctorName,
        d.speciality,
        COUNT(DISTINCT a.patientId) as patient_count
    FROM Doctor d
    LEFT JOIN Appointment a ON d.doctorId = a.doctorId
    WHERE MONTH(a.appointmentTime) = p_month
    AND YEAR(a.appointmentTime) = p_year
    GROUP BY d.doctorId, d.doctorName, d.speciality
    ORDER BY patient_count DESC
    LIMIT 1;
END;

CALL GetDoctorWithMostPatientsByMonth(12, 2025);
```

### Sample Output:
```
+----------+-----------+----------+--------------+
| doctorId | doctorName| speciality | patient_count |
+----------+-----------+----------+--------------+
| 2        | Dr. Johnson| Orthopedic| 8            |
+----------+-----------+----------+--------------+
1 row in set (0.00 sec)
```

## Question 23: GetDoctorWithMostPatientsByYear Stored Procedure
```sql
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(
    IN p_year INT
)
BEGIN
    SELECT 
        d.doctorId,
        d.doctorName,
        d.speciality,
        COUNT(DISTINCT a.patientId) as patient_count
    FROM Doctor d
    LEFT JOIN Appointment a ON d.doctorId = a.doctorId
    WHERE YEAR(a.appointmentTime) = p_year
    GROUP BY d.doctorId, d.doctorName, d.speciality
    ORDER BY patient_count DESC
    LIMIT 1;
END;

CALL GetDoctorWithMostPatientsByYear(2025);
```

### Sample Output:
```
+----------+-----------+----------+--------------+
| doctorId | doctorName| speciality | patient_count |
+----------+-----------+----------+--------------+
| 3        | Dr. Williams| Neurology| 12           |
+----------+-----------+----------+--------------+
1 row in set (0.00 sec)
```
