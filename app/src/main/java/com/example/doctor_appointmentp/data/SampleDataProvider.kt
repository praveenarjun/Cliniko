package com.example.doctor_appointmentp.data

import com.example.doctor_appointmentp.data.model.Doctor

object SampleDataProvider {
    
    fun getDoctorsSampleData(): List<Doctor> {
        return listOf(
            Doctor(
                id = 1,
                name = "Dr. Sarah Johnson",
                specialization = "Cardiologist",
                experience = 15,
                certifications = "American Board of Internal Medicine, Cardiovascular Disease",
                competencies = "Heart disease, Hypertension, Cardiac rehabilitation",
                languages = "English, Spanish",
                imageUrl = "https://randomuser.me/api/portraits/women/44.jpg",
                rating = 4.8f,
                availability = "Mon-Thu, 9AM-5PM"
            ),
            Doctor(
                id = 2,
                name = "Dr. Michael Chen",
                specialization = "Pediatrician",
                experience = 10,
                certifications = "American Board of Pediatrics",
                competencies = "Child development, Preventive care, Childhood diseases",
                languages = "English, Mandarin",
                imageUrl = "https://randomuser.me/api/portraits/men/42.jpg",
                rating = 4.9f,
                availability = "Mon-Fri, 8AM-4PM"
            ),
            Doctor(
                id = 3,
                name = "Dr. Emily Rodriguez",
                specialization = "Dermatologist",
                experience = 8,
                certifications = "American Board of Dermatology",
                competencies = "Skin cancer screening, Acne treatment, Cosmetic procedures",
                languages = "English, Spanish",
                imageUrl = "https://randomuser.me/api/portraits/women/36.jpg",
                rating = 4.7f,
                availability = "Tue-Sat, 10AM-6PM"
            ),
            Doctor(
                id = 4,
                name = "Dr. James Wilson",
                specialization = "Orthopedic Surgeon",
                experience = 20,
                certifications = "American Board of Orthopedic Surgery",
                competencies = "Joint replacement, Sports injuries, Fracture care",
                languages = "English",
                imageUrl = "https://randomuser.me/api/portraits/men/32.jpg",
                rating = 4.6f,
                availability = "Mon, Wed, Fri, 8AM-6PM"
            ),
            Doctor(
                id = 5,
                name = "Dr. Aisha Patel",
                specialization = "Neurologist",
                experience = 12,
                certifications = "American Board of Psychiatry and Neurology",
                competencies = "Headache disorders, Epilepsy, Multiple sclerosis",
                languages = "English, Hindi, Gujarati",
                imageUrl = "https://randomuser.me/api/portraits/women/28.jpg",
                rating = 4.9f,
                availability = "Mon-Thu, 9AM-5PM"
            )
        )
    }
    
    // Clinic information
    val clinicInfo = ClinicInfo(
        name = "HealthFirst Medical Clinic",
        address = "123 Medical Center Drive, Anytown, USA 12345",
        phone = "(555) 123-4567",
        email = "info@healthfirstclinic.com",
        website = "https://www.healthfirstclinic.com",
        hours = "Monday-Friday: 8AM-6PM, Saturday: 9AM-1PM, Sunday: Closed",
        latitude = 37.7749,
        longitude = -122.4194
    )
}

data class ClinicInfo(
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val website: String,
    val hours: String,
    val latitude: Double,
    val longitude: Double
)