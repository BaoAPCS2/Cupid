package com.example.cupid.ui.view.SignUp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsSelectionScreen(
    navController: NavHostController,
    onBackClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    val interests = listOf(
        "Photography", "Shopping", "Karaoke", "Yoga",
        "Cooking", "Tennis", "Run", "Swimming",
        "Art", "Traveling", "Extreme", "Music",
        "Drink", "Video games"
    )

    var selectedInterests by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(16.dp))
                    .clickable { navController.navigate(Screen.GenderSelection.route) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFFE94057))
            }

            Text(
                text = "Skip",
                color = Color(0xFFE94057),
                fontSize = 16.sp,
                modifier = Modifier.clickable { navController.navigate(Screen.ContactEnabled.route) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "Your interests",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Select a few of your interests and let everyone know what you’re passionate about.",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Interests Grid
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            interests.forEach { interest ->
                val isSelected = selectedInterests.contains(interest)

                Box(
                    modifier = Modifier
                        .background(
                            if (isSelected) Color(0xFFE94057) else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Transparent else Color.LightGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            selectedInterests = if (isSelected) {
                                selectedInterests - interest
                            } else {
                                selectedInterests + interest
                            }
                        }
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = interest,
                        color = if (isSelected) Color.White else Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Continue Button
        Button(
            onClick = { navController.navigate(Screen.ContactEnabled.route) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE94057)),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Continue", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InterestPreview() {
    val navController = rememberNavController()
    InterestsSelectionScreen(navController)
}