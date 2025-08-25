package com.example.cupid.ui.view.SignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cupid.R
import com.example.cupid.navigation.Screen

@Composable
fun EnterPhoneNumberScreen(navController: NavHostController) {
    var phoneNumber by remember { mutableStateOf("") }
    var selectedCountryCode by remember { mutableStateOf("+1") } // default US

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "My mobile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Please enter your valid phone number. We will send you a 4-digit code to verify your account.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Phone input field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Phone Number Input
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = { Text("Phone number") },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )

        }

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                navController.navigate(Screen.Otp.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE94057))
        ) {
            Text("Continue", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewEnterPhoneScreen() {
    val navController = rememberNavController()
    EnterPhoneNumberScreen(navController)
}