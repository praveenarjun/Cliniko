package com.example.doctor_appointmentp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctor_appointmentp.data.SampleDataProvider
import com.example.doctor_appointmentp.data.database.AppDatabase
import com.example.doctor_appointmentp.data.model.Appointment
import com.example.doctor_appointmentp.data.model.Doctor
import com.example.doctor_appointmentp.data.repository.ClinicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

class ClinicViewModel(application: Application) : AndroidViewModel(application) {
    
    private val database = AppDatabase.getDatabase(application)
    private val doctorDao = database.doctorDao()
    private val appointmentDao = database.appointmentDao()
    private val repository = ClinicRepository(doctorDao, appointmentDao)
    
    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> = _doctors.asStateFlow()
    
    private val _selectedDoctor = MutableStateFlow<Doctor?>(null)
    val selectedDoctor: StateFlow<Doctor?> = _selectedDoctor.asStateFlow()
    
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()
    
    private val _selectedAppointment = MutableStateFlow<Appointment?>(null)
    val selectedAppointment: StateFlow<Appointment?> = _selectedAppointment.asStateFlow()
    
    val clinicInfo = SampleDataProvider.clinicInfo
    
    init {
        loadDoctors()
        loadAppointments()
    }
    
    private fun loadDoctors() {
        viewModelScope.launch {
            try {
                // Check if we already have doctors in the database
                val existingDoctors = repository.getAllDoctors().first()
                
                if (existingDoctors.isEmpty()) {
                    // If no doctors exist, insert sample data
                    repository.insertDoctors(SampleDataProvider.getDoctorsSampleData())
                }
                
                // Collect doctors from the database
                repository.getAllDoctors().collect { doctorsList ->
                    _doctors.value = doctorsList
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
                // Provide fallback data in case of database error
                _doctors.value = SampleDataProvider.getDoctorsSampleData()
            }
        }
    }
    
    private fun loadAppointments() {
        viewModelScope.launch {
            repository.getAllAppointments().collect { appointmentsList ->
                _appointments.value = appointmentsList
            }
        }
    }
    
    fun getDoctorById(id: Int) {
        viewModelScope.launch {
            _selectedDoctor.value = repository.getDoctorById(id)
        }
    }
    
    fun getAppointmentById(id: Int) {
        viewModelScope.launch {
            _selectedAppointment.value = repository.getAppointmentById(id)
        }
    }
    
    fun getAppointmentsByDoctor(doctorId: Int) {
        viewModelScope.launch {
            repository.getAppointmentsByDoctor(doctorId).collect { appointmentsList ->
                _appointments.value = appointmentsList
            }
        }
    }
    
    fun getAppointmentsByDate(date: LocalDate) {
        viewModelScope.launch {
            repository.getAppointmentsByDate(date).collect { appointmentsList ->
                _appointments.value = appointmentsList
            }
        }
    }
    
    fun saveAppointment(appointment: Appointment, onSuccess: (Int) -> Unit) {
        viewModelScope.launch {
            val id = repository.insertAppointment(appointment).toInt()
            onSuccess(id)
        }
    }
    
    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.updateAppointment(appointment)
        }
    }
    
    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.deleteAppointment(appointment)
        }
    }
}