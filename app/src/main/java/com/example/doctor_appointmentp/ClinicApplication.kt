package com.example.doctor_appointmentp

import android.app.Application
import com.example.doctor_appointmentp.data.database.AppDatabase

class ClinicApplication : Application() {
    
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    
    override fun onCreate() {
        super.onCreate()
        // Initialize any application-wide components here
    }
}