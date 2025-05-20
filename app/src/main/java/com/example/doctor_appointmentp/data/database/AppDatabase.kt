package com.example.doctor_appointmentp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.doctor_appointmentp.data.dao.AppointmentDao
import com.example.doctor_appointmentp.data.dao.DoctorDao
import com.example.doctor_appointmentp.data.model.Appointment
import com.example.doctor_appointmentp.data.model.Doctor
import com.example.doctor_appointmentp.util.DateTimeConverters

@Database(
    entities = [Doctor::class, Appointment::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateTimeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun doctorDao(): DoctorDao
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "clinic_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}