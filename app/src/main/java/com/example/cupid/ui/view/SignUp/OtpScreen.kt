package com.example.cupid.ui.view.SignUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cupid.navigation.Screen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var otp by remember { mutableStateOf(List(4) { "" }) }
    val focusRequesters = List(4) { FocusRequester() }

    // Countdown timer
    var timeLeft by remember { mutableStateOf(60) }
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.EnterPhone.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFFE94057)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Timer
            Text(
                text = String.format("00:%02d", timeLeft),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Type the verification code we’ve sent you",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // OTP input
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                otp.forEachIndexed { index, value ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = {
                            if (it.length <= 1 && it.all { ch -> ch.isDigit() }) {
                                otp = otp.toMutableList().also { list -> list[index] = it }
                                if (it.isNotEmpty() && index < 3) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                                if (otp.all { code -> code.isNotEmpty() }) {
                                    // check whether the otp is correct

                                    navController.navigate(Screen.ProfileSetup.route)
                                }
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .size(64.dp)
                            .focusRequester(focusRequesters[index]),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFE94057),
                            unfocusedIndicatorColor = Color.LightGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Send again",
                color = Color(0xFFE94057),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable { timeLeft = 60 } // reset timer
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OtpPreview() {
    val navController = rememberNavController()
    OtpScreen(navController)
}