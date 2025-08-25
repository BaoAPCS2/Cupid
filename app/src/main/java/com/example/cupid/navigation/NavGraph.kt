package com.example.cupid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cupid.ui.components.BottomBarItem
import com.example.cupid.ui.view.MainContent.MainScreen
import com.example.cupid.ui.view.OnBoarding.OnBoardingScreen
import com.example.cupid.ui.view.Profile.GoogleMapScreen
import com.example.cupid.ui.view.SignUp.ContactEnabledScreen
import com.example.cupid.ui.view.SignUp.EnableNotificationScreen
import com.example.cupid.ui.view.SignUp.EnterPhoneNumberScreen
import com.example.cupid.ui.view.SignUp.GenderSelectionScreen
import com.example.cupid.ui.view.SignUp.InterestsSelectionScreen
import com.example.cupid.ui.view.SignUp.OtpScreen
import com.example.cupid.ui.view.SignUp.ProfileSetupScreen
import com.example.cupid.ui.view.SignUp.SignUpScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.OnBoarding.route
    ) {
        composable(
            Screen.OnBoarding.route
        ) {
            OnBoardingScreen(navController)
        }
        composable(
            Screen.SignUp.route
        ) {
            SignUpScreen(navController)
        }
        composable(
            Screen.EnterPhone.route
        ){
            EnterPhoneNumberScreen(navController)
        }
        composable(
            Screen.Otp.route
        ) {
            OtpScreen(navController)
        }
        composable(
            Screen.ProfileSetup.route
        ) {
            ProfileSetupScreen(navController)
        }
        composable(
            Screen.GenderSelection.route
        ) {
            GenderSelectionScreen(navController)
        }
        composable(
            Screen.Interests.route
        ) {
            InterestsSelectionScreen(navController)
        }
        composable(
            Screen.ContactEnabled.route
        ) {
            ContactEnabledScreen(
                navController,
                onSkip = {},
                onAccessContacts = {}
            )
        }
        composable(
            Screen.NotificationEnabled.route
        ) {
            EnableNotificationScreen(
                navController,
                onSkip = {},
                onEnableNotification = {}
            )
        }
        composable(
            route = Screen.Main.route,
            arguments = listOf(
                navArgument("tabName") {
                    type = NavType.StringType
                    defaultValue = BottomBarItem.HOME.name
                }
            )
        ) { backStackEntry ->
            val tabName = backStackEntry.arguments?.getString("tabName")
            MainScreen(
                navController = navController,
                initialTabName = tabName ?: BottomBarItem.HOME.name
            )
        }
        composable(
            route = "map?name={name}&lat={lat}&lng={lng}",
            arguments = listOf(
                navArgument("name"){ type = NavType.StringType; defaultValue = "User" },
                navArgument("lat"){ type = NavType.FloatType; defaultValue = 0f },
                navArgument("lng"){ type = NavType.FloatType; defaultValue = 0f },
            )
        ) { backStack ->
            val name = backStack.arguments?.getString("name") ?: "User"
            val lat = backStack.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lng = backStack.arguments?.getFloat("lng")?.toDouble() ?: 0.0
            GoogleMapScreen(userName = name, destination = LatLng(lat, lng))
        }


    }
}