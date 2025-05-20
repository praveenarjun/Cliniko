package com.example.doctor_appointmentp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var rating by remember { mutableFloatStateOf(3.0f) }
    var feedback by remember { mutableStateOf("") }
    var feedbackError by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Provide Feedback") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "We Value Your Feedback",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "How would you rate your experience?",
                style = MaterialTheme.typography.titleMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Rating stars
            RatingBar(
                rating = rating,
                onRatingChanged = { rating = it }
            )
            
            Text(
                text = when {
                    rating <= 1 -> "Poor"
                    rating <= 2 -> "Fair"
                    rating <= 3 -> "Good"
                    rating <= 4 -> "Very Good"
                    else -> "Excellent"
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            OutlinedTextField(
                value = feedback,
                onValueChange = { 
                    feedback = it
                    if (it.isNotEmpty()) feedbackError = null
                },
                label = { Text("Tell us about your experience") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                isError = feedbackError != null,
                supportingText = { feedbackError?.let { Text(it) } }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    if (feedback.isBlank()) {
                        feedbackError = "Please provide some feedback"
                    } else {
                        // Here you would typically save the feedback to your database
                        // For now, we'll just show a toast
                        Toast.makeText(
                            context,
                            "Thank you for your feedback!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit Feedback")
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.layout.Row {
                for (i in 1..5) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star $i",
                        tint = if (i <= rating) Color(0xFFFFC107) else Color.Gray,
                        modifier = Modifier
                            .padding(4.dp)
                            .padding(end = 4.dp)
                    )
                }
            }
        }
        
        Slider(
            value = rating,
            onValueChange = { onRatingChanged(it) },
            valueRange = 0f..5f,
            steps = 4,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}