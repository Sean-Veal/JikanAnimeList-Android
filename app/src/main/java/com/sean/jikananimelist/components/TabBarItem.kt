package com.sean.jikananimelist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabBarItem(
    val image: ImageVector,
    val title: String,
    val route: String
) {
    data object Top: TabBarItem(Icons.Filled.Star, "Top", "top_screen")
}