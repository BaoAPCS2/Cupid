package com.example.cupid.ui.view.SignUp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cupid.navigation.Screen

@Composable
fun GenderSelectionScreen(
    navController: NavHostController,
    onBackClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    onContinueClick: (String) -> Unit = {}
) {
    var selectedGender by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFF7F5F7), RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate(Screen.ProfileSetup.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFE94057)
                )
            }

            TextButton(onClick = {navController.navigate(Screen.Interests.route)}) {
                Text("Skip", color = Color(0xFFE94057))
            }
        }

        // Content
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "I am a",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            GenderOption(
                text = "Woman",
                isSelected = selectedGender == "Woman",
                onClick = { selectedGender = "Woman" }
            )
            GenderOption(
                text = "Man",
                isSelected = selectedGender == "Man",
                onClick = { selectedGender = "Man" }
            )
            GenderOption(
                text = "Choose another",
                isSelected = selectedGender == "Other",
                onClick = { selectedGender = "Other" },
                showCheck = false
            )
        }

        // Continue Button
        Button(
            onClick = {
                selectedGender?.let {
                    onContinueClick(it)
                    navController.navigate(Screen.Interests.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE94057))
        ) {
            Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun GenderOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    showCheck: Boolean = true
) {
    val backgroundColor = if (isSelected) Color(0xFFE94057) else Color.Transparent
    val textColor = if (isSelected) Color.White else Color.Black
    val borderColor = if (isSelected) Color.Transparent else Color(0xFFE5E5E5)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, color = textColor, fontSize = 18.sp, fontWeight = FontWeight.Medium)

        if (showCheck) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.White
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xFF9E9E9E)
                )
            }
        } else {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Choose another",
                tint = Color(0xFF9E9E9E)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GenderOptionPreview() {
    val navController = rememberNavController()
    GenderSelectionScreen(navController)
}
