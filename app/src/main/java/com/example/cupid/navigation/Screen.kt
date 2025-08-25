package com.example.cupid.navigation

sealed class Screen(val route: String) {
    object OnBoarding: Screen("onboarding")
    object SignUp: Screen("signup")
    object EnterPhone: Screen("enterphone")
    object Otp: Screen("otp")
    object ProfileSetup: Screen("profile_setup")
    object GenderSelection: Screen("gender_selection")
    object Interests: Screen("interests")
    object ContactEnabled: Screen("contact")
    object NotificationEnabled: Screen("notification")
    object Main : Screen("main_screen/{tabName}") {
        fun createRoute(tabName: String) = "main_screen/$tabName"
    }
    object Map  : Screen("map?name={name}&lat={lat}&lng={lng}")
}