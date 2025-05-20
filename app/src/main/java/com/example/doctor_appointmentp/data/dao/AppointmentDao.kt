package com.example.doctor_appointmentp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.doctor_appointmentp.data.model.Appointment
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE doctorId = :doctorId")
    fun getAppointmentsByDoctor(doctorId: Int): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE date = :date")
    fun getAppointmentsByDate(date: LocalDate): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE id = :id")
    suspend fun getAppointmentById(id: Int): Appointment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment): Long

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)
}