package com.example.doctor_appointmentp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.doctor_appointmentp.ui.screens.AppIconSelectionScreen
import com.example.doctor_appointmentp.ui.screens.AppointmentBookingScreen
import com.example.doctor_appointmentp.ui.screens.AppointmentConfirmationScreen
import com.example.doctor_appointmentp.ui.screens.BookAppointmentScreen
import com.example.doctor_appointmentp.ui.screens.ClinicInfoScreen
import com.example.doctor_appointmentp.ui.screens.DoctorDetailScreen
import com.example.doctor_appointmentp.ui.screens.DoctorListScreen
import com.example.doctor_appointmentp.ui.screens.FeedbackScreen
import com.example.doctor_appointmentp.ui.screens.HomeScreen
import com.example.doctor_appointmentp.ui.screens.NotificationsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        composable(Screen.DoctorList.route) {
            DoctorListScreen(navController = navController)
        }
        
        composable(
            route = Screen.DoctorDetail.route + "/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
            DoctorDetailScreen(
                doctorId = doctorId,
                navController = navController
            )
        }
        
        // New dedicated booking screen
        composable(Screen.BookAppointment.route) {
            BookAppointmentScreen(navController = navController)
        }
        
        composable(
            route = Screen.AppointmentBooking.route + "/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getInt("doctorId") ?: 0
            AppointmentBookingScreen(
                doctorId = doctorId,
                navController = navController
            )
        }
        
        composable(
            route = Screen.AppointmentConfirmation.route + "/{appointmentId}",
            arguments = listOf(navArgument("appointmentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getInt("appointmentId") ?: 0
            AppointmentConfirmationScreen(
                appointmentId = appointmentId,
                navController = navController
            )
        }
        
        composable(Screen.ClinicInfo.route) {
            ClinicInfoScreen(navController = navController)
        }
        
        // New feedback screen
        composable(Screen.Feedback.route) {
            FeedbackScreen(navController = navController)
        }
        
        // New notifications screen
        composable(Screen.Notifications.route) {
            NotificationsScreen(navController = navController)
        }
        
        // App icon selection screen
        composable(Screen.AppIconSelection.route) {
            AppIconSelectionScreen(navController = navController)
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DoctorList : Screen("doctor_list")
    object DoctorDetail : Screen("doctor_detail")
    object BookAppointment : Screen("book_appointment") // New dedicated booking screen
    object AppointmentBooking : Screen("appointment_booking")
    object AppointmentConfirmation : Screen("appointment_confirmation")
    object ClinicInfo : Screen("clinic_info")
    object Feedback : Screen("feedback") // New feedback screen
    object Notifications : Screen("notifications") // New notifications screen
    object AppIconSelection : Screen("app_icon_selection") // App icon selection screen
}