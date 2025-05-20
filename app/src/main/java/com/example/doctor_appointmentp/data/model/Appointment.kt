package com.example.doctor_appointmentp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "appointments",
    foreignKeys = [
        ForeignKey(
            entity = Doctor::class,
            parentColumns = ["id"],
            childColumns = ["doctorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [androidx.room.Index("doctorId")]
)
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val doctorId: Int,
    val patientName: String,
    val patientPhone: String,
    val patientEmail: String,
    val date: LocalDate,
    val time: LocalTime,
    val reason: String,
    val isFirstVisit: Boolean,
    val additionalNotes: String = "",
    val status: AppointmentStatus = AppointmentStatus.PENDING
)

enum class AppointmentStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED
}