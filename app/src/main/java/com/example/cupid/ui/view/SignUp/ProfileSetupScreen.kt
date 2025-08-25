package com.example.cupid.ui.view.SignUp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.doubleFromBits
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.cupid.R
import com.example.cupid.navigation.Screen
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupScreen(
    navController: NavHostController,
    onSkip: () -> Unit = {},
    onConfirm: (String, String, Long?, Uri?) -> Unit = { _, _, _, _ -> }
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Launcher to pick image
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri = it }
        }

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Skip",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFE53945),
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.GenderSelection.route)
                    }
                )
            }
        },
        bottomBar = {
            Button(
                onClick = {
                    onConfirm(firstName, lastName, birthday, imageUri)
                    navController.navigate(Screen.GenderSelection.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53945)
                )
            ) {
                Text("Confirm")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Text(
                text = "Profile details",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(Modifier.height(24.dp))

            // Profile image
            Box(contentAlignment = Alignment.BottomEnd) {
                AsyncImage(
                    model = imageUri ?: R.drawable.user_placeholder,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(24.dp))
                )
                IconButton(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFE53945), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Upload",
                        tint = Color.White
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // First name
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = { Text("First name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Last name
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = { Text("Last name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Birthday picker button
            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDE7EA),
                    contentColor = Color(0xFFE53945)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = birthday?.let {
                        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
                    } ?: "Choose birthday date"
                )
            }
        }
    }

    // Birthday picker dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = birthday ?: System.currentTimeMillis()
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    birthday = datePickerState.selectedDateMillis
                    showDatePicker = false
                }) {
                    Text("Save", color = Color(0xFFE53945))
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
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileSetupPreview() {
    val navController = rememberNavController()
    ProfileSetupScreen(navController)
}