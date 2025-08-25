package com.example.cupid.ui.view.SignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.cupid.ui.components.BottomBarItem

@Composable
fun EnableNotificationScreen(
    navController: NavHostController,
    onSkip: () -> Unit,
    onEnableNotification: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Skip button
        TextButton(
            onClick = { navController.navigate(Screen.Main.createRoute(BottomBarItem.HOME.name)) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Text(
                text = "Skip",
                color = Color(0xFFE94057),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Replace with your illustration (vector/png)
            Image(
                painter = painterResource(id = R.drawable.notification_placeholder),
                contentDescription = "Notification illustration",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 32.dp)
            )

            Text(
                text = "Enable notification’s",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Get push-notification when you get the match or receive a message.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        // Bottom button
        Button(
            onClick = { navController.navigate(Screen.Main.createRoute(BottomBarItem.HOME.name)) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE94057)),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            Text(
                text = "I want to be notified",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun asdf() {
    val navController = rememberNavController()
    EnableNotificationScreen(
        navController,
        onSkip = {},
        onEnableNotification = {}
    )
}
