package com.example.doctor_appointmentp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class Doctor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val specialization: String,
    val experience: Int, // in years
    val certifications: String,
    val competencies: String,
    val languages: String,
    val imageUrl: String,
    val rating: Float,
    val availability: String // e.g., "Mon-Fri, 9AM-5PM"
)