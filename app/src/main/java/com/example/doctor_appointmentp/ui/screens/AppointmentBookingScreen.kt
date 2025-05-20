package com.example.doctor_appointmentp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.doctor_appointmentp.data.model.Appointment
import com.example.doctor_appointmentp.navigation.Screen
import com.example.doctor_appointmentp.ui.viewmodel.ClinicViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentBookingScreen(
    doctorId: Int,
    navController: NavController,
    viewModel: ClinicViewModel = viewModel()
) {
    val selectedDoctor by viewModel.selectedDoctor.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(doctorId) {
        viewModel.getDoctorById(doctorId)
    }
    
    // Form state
    var patientName by remember { mutableStateOf("") }
    var patientPhone by remember { mutableStateOf("") }
    var patientEmail by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var additionalNotes by remember { mutableStateOf("") }
    var isFirstVisit by remember { mutableStateOf(true) }
    
    // Date picker state
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    var showDatePicker by remember { mutableStateOf(false) }
    
    // Time picker state
    val timePickerState = rememberTimePickerState(
        initialHour = 9,
        initialMinute = 0
    )
    var showTimePicker by remember { mutableStateOf(false) }
    
    // Dropdown state for visit reason
    var isReasonExpanded by remember { mutableStateOf(false) }
    val reasonOptions = listOf(
        "Regular check-up",
        "Consultation",
        "Follow-up",
        "Emergency",
        "Procedure",
        "Other"
    )
    
    // Form validation
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var reasonError by remember { mutableStateOf<String?>(null) }
    
    fun validateForm(): Boolean {
        var isValid = true
        
        if (patientName.isBlank()) {
            nameError = "Name is required"
            isValid = false
        } else {
            nameError = null
        }
        
        if (patientPhone.isBlank()) {
            phoneError = "Phone number is required"
            isValid = false
        } else if (!patientPhone.matches(Regex("^[0-9]{10}$"))) {
            phoneError = "Enter a valid 10-digit phone number"
            isValid = false
        } else {
            phoneError = null
        }
        
        if (patientEmail.isBlank()) {
            emailError = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(patientEmail).matches()) {
            emailError = "Enter a valid email address"
            isValid = false
        } else {
            emailError = null
        }
        
        if (reason.isBlank()) {
            reasonError = "Please select a reason for visit"
            isValid = false
        } else {
            reasonError = null
        }
        
        return isValid
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (selectedDoctor == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val doctor = selectedDoctor!!
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = "Appointment with ${doctor.name}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = doctor.specialization,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Patient information form
                Text(
                    text = "Patient Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = patientName,
                    onValueChange = { patientName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nameError != null,
                    supportingText = { nameError?.let { Text(it) } }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = patientPhone,
                    onValueChange = { patientPhone = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    isError = phoneError != null,
                    supportingText = { phoneError?.let { Text(it) } }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = patientEmail,
                    onValueChange = { patientEmail = it },
                    label = { Text("Email Address") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailError != null,
                    supportingText = { emailError?.let { Text(it) } }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                HorizontalDivider()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Appointment details
                Text(
                    text = "Appointment Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Date picker
                OutlinedTextField(
                    value = if (datePickerState.selectedDateMillis != null) {
                        val date = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                    } else {
                        ""
                    },
                    onValueChange = {},
                    label = { Text("Date") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Select Date"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Time picker
                OutlinedTextField(
                    value = String.format(
                        "%02d:%02d %s",
                        if (timePickerState.hour > 12) timePickerState.hour - 12 else timePickerState.hour,
                        timePickerState.minute,
                        if (timePickerState.hour >= 12) "PM" else "AM"
                    ),
                    onValueChange = {},
                    label = { Text("Time") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showTimePicker = true }) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "Select Time"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (showTimePicker) {
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = { showTimePicker = false },
                        title = { Text("Select Time") },
                        text = { TimePicker(state = timePickerState) },
                        confirmButton = {
                            TextButton(onClick = { showTimePicker = false }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showTimePicker = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Reason dropdown
                ExposedDropdownMenuBox(
                    expanded = isReasonExpanded,
                    onExpandedChange = { isReasonExpanded = it }
                ) {
                    OutlinedTextField(
                        value = reason,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Reason for Visit") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isReasonExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        isError = reasonError != null,
                        supportingText = { reasonError?.let { Text(it) } }
                    )
                    
                    ExposedDropdownMenu(
                        expanded = isReasonExpanded,
                        onDismissRequest = { isReasonExpanded = false }
                    ) {
                        reasonOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    reason = option
                                    isReasonExpanded = false
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // First visit radio buttons
                Text(
                    text = "Is this your first visit?",
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = isFirstVisit,
                                onClick = { isFirstVisit = true },
                                role = Role.RadioButton
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isFirstVisit,
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Yes")
                    }
                    
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = !isFirstVisit,
                                onClick = { isFirstVisit = false },
                                role = Role.RadioButton
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !isFirstVisit,
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("No")
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Additional notes
                OutlinedTextField(
                    value = additionalNotes,
                    onValueChange = { additionalNotes = it },
                    label = { Text("Additional Notes (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Submit button
                Button(
                    onClick = {
                        if (validateForm()) {
                            val selectedDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            
                            val selectedTime = LocalTime.of(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                            
                            val appointment = Appointment(
                                doctorId = doctor.id,
                                patientName = patientName,
                                patientPhone = patientPhone,
                                patientEmail = patientEmail,
                                date = selectedDate,
                                time = selectedTime,
                                reason = reason,
                                isFirstVisit = isFirstVisit,
                                additionalNotes = additionalNotes
                            )
                            
                            viewModel.saveAppointment(appointment) { appointmentId ->
                                Toast.makeText(context, "Appointment booked successfully!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.AppointmentConfirmation.route + "/$appointmentId") {
                                    popUpTo(Screen.DoctorList.route) {
                                        inclusive = false
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please fix the errors in the form", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Book Appointment")
                }
            }
        }
    }
}