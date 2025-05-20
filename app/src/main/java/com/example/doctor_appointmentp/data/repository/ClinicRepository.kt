package com.example.doctor_appointmentp.data.repository

import com.example.doctor_appointmentp.data.dao.AppointmentDao
import com.example.doctor_appointmentp.data.dao.DoctorDao
import com.example.doctor_appointmentp.data.model.Appointment
import com.example.doctor_appointmentp.data.model.Doctor
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class ClinicRepository(
    private val doctorDao: DoctorDao,
    private val appointmentDao: AppointmentDao
) {
    // Doctor operations
    fun getAllDoctors(): Flow<List<Doctor>> = doctorDao.getAllDoctors()

    suspend fun getDoctorById(id: Int): Doctor? = doctorDao.getDoctorById(id)

    suspend fun insertDoctor(doctor: Doctor): Long = doctorDao.insertDoctor(doctor)

    suspend fun insertDoctors(doctors: List<Doctor>) = doctorDao.insertDoctors(doctors)

    suspend fun updateDoctor(doctor: Doctor) = doctorDao.updateDoctor(doctor)

    suspend fun deleteDoctor(doctor: Doctor) = doctorDao.deleteDoctor(doctor)

    // Appointment operations
    fun getAllAppointments(): Flow<List<Appointment>> = appointmentDao.getAllAppointments()

    fun getAppointmentsByDoctor(doctorId: Int): Flow<List<Appointment>> = 
        appointmentDao.getAppointmentsByDoctor(doctorId)

    fun getAppointmentsByDate(date: LocalDate): Flow<List<Appointment>> = 
        appointmentDao.getAppointmentsByDate(date)

    suspend fun getAppointmentById(id: Int): Appointment? = appointmentDao.getAppointmentById(id)

    suspend fun insertAppointment(appointment: Appointment): Long = 
        appointmentDao.insertAppointment(appointment)

    suspend fun updateAppointment(appointment: Appointment) = 
        appointmentDao.updateAppointment(appointment)

    suspend fun deleteAppointment(appointment: Appointment) = 
        appointmentDao.deleteAppointment(appointment)
}