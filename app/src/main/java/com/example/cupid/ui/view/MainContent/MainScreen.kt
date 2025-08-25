package com.example.cupid.ui.view.MainContent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cupid.ui.components.BottomBarItem
import com.example.cupid.ui.components.CustomBottomNavigation
import com.example.cupid.ui.view.Home.HomeScreen
import com.example.cupid.ui.view.Profile.ProfileScreen


@Composable
fun MainScreen(navController: NavHostController, initialTabName: String) {
    var selectedItem by remember { mutableStateOf(BottomBarItem.valueOf(initialTabName)) }

    Scaffold(
        bottomBar = {
            CustomBottomNavigation(
                selectedItem = selectedItem,
                onItemSelected = { item ->
                    selectedItem = item
                }
            )
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = selectedItem,
            label = "MainScreenAnimation",
            transitionSpec = {
                if (targetState.ordinal > initialState.ordinal) {
                    slideInHorizontally { width -> width } + fadeIn(animationSpec = tween(300)) togetherWith
                            slideOutHorizontally { width -> -width } + fadeOut(animationSpec = tween(300))
                } else {
                    slideInHorizontally { width -> -width } + fadeIn(animationSpec = tween(300)) togetherWith
                            slideOutHorizontally { width -> width } + fadeOut(animationSpec = tween(300))
                }
            }
        ) { targetScreen ->
            when (targetScreen) {
                BottomBarItem.HOME -> HomeScreen(navController, innerPadding)
                BottomBarItem.PROFILE -> ProfileScreen(navController, innerPadding)
                // thêm các màn khác nếu có
                else -> {}
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()

    MainScreen(navController, BottomBarItem.HOME.name)
}