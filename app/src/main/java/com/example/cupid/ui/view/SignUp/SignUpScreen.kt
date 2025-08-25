package com.example.cupid.ui.view.SignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun SignUpScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_app_circle), // replace with your logo
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "Sign up to continue",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Continue with Email Button
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE94057)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continue with email", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Use Phone Number Button
        OutlinedButton(
            onClick = {
                navController.navigate(Screen.EnterPhone.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFE94057)),
            shape = RoundedCornerShape(12.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFE94057)))
        ) {
            Text("Use phone number", color = Color(0xFFE94057), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Divider with text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
            Text(
                text = "  or sign up with  ",
                fontSize = 14.sp,
                color = Color.Black
            )
            Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Social buttons row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SocialButton(R.drawable.ic_facebook, onClick = {})
            SocialButton(R.drawable.ic_google, onClick = {})
            SocialButton(R.drawable.ic_apple, onClick = {})
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Terms and Privacy
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Terms of use",
                color = Color(0xFFE94057),
                fontSize = 14.sp,
                modifier = Modifier.clickable {  }
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Privacy Policy",
                color = Color(0xFFE94057),
                fontSize = 14.sp,
                modifier = Modifier.clickable {}
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SocialButton(iconRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSignUpScreen() {
    val navController = rememberNavController()
    SignUpScreen(navController)
}
