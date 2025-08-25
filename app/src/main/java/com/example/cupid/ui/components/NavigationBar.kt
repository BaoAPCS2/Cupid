package com.example.cupid.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.ui.tooling.preview.Preview
import com.example.cupid.R


enum class BottomBarItem(val label: String, val iconRes: Int) {
    HOME("Home", R.drawable.ic_cards),
    MATCHES("Scan", R.drawable.ic_heart),
    MESSAGE("Message", R.drawable.ic_message),
    PROFILE("Profile", R.drawable.ic_profile)
}

@Composable
fun CustomBottomNavigation(
    selectedItem: BottomBarItem,
    onItemSelected: (BottomBarItem) -> Unit
) {
    val items = listOf(
        BottomBarItem.HOME,
        BottomBarItem.MATCHES,
        BottomBarItem.MESSAGE,
        BottomBarItem.PROFILE
    )

    NavigationBar(
        containerColor = Color(0xFFF5F5F5),
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        tint = if (selectedItem == item) Color(0xFFE94057) else Color.Gray
                    )
                }
            )
        }
    }
}