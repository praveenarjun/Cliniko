package com.example.doctor_appointmentp.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.doctor_appointmentp.navigation.Screen
import com.example.doctor_appointmentp.ui.viewmodel.ClinicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicInfoScreen(
    navController: NavController,
    viewModel: ClinicViewModel = viewModel()
) {
    val clinicInfo = viewModel.clinicInfo
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clinic Information") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = clinicInfo.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Contact Information",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    ClinicInfoItem(
                        icon = Icons.Default.LocationOn,
                        label = "Address",
                        value = clinicInfo.address
                    )
                    
                    ClinicInfoItem(
                        icon = Icons.Default.Call,
                        label = "Phone",
                        value = clinicInfo.phone
                    )
                    
                    ClinicInfoItem(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = clinicInfo.email
                    )
                    
                    ClinicInfoItem(
                        icon = Icons.Default.Language,
                        label = "Website",
                        value = clinicInfo.website
                    )
                    
                    ClinicInfoItem(
                        icon = Icons.Default.Schedule,
                        label = "Working Hours",
                        value = clinicInfo.hours
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Buttons for implicit intents
            Button(
                onClick = {
                    // Open website using implicit intent
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(clinicInfo.website))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "Visit Website"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Visit Our Website")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = {
                    // Open location on map using implicit intent
                    val gmmIntentUri = Uri.parse("geo:${clinicInfo.latitude},${clinicInfo.longitude}?q=${Uri.encode(clinicInfo.address)}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    if (mapIntent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(mapIntent)
                    } else {
                        // If Google Maps is not installed, open in browser
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://maps.google.com/?q=${clinicInfo.latitude},${clinicInfo.longitude}")
                        )
                        context.startActivity(browserIntent)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "View on Map"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("View on Map")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = {
                    // Call clinic using implicit intent
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${clinicInfo.phone.replace(Regex("[^0-9]"), "")}"))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call Clinic"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Call Clinic")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = {
                    // Email clinic using implicit intent
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:${clinicInfo.email}")
                        putExtra(Intent.EXTRA_SUBJECT, "Inquiry about clinic services")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Clinic"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Email Clinic")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))
            
            // App settings section
            Text(
                text = "App Settings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    navController.navigate(Screen.AppIconSelection.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Change App Icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Change App Icon")
            }
        }
    }
}

@Composable
fun ClinicInfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 40.dp)
        )
        
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}