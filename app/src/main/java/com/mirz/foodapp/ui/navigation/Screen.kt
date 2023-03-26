package com.mirz.foodapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object DetailFood : Screen("home/{foodId}") {
        fun createRoute(foodId: Int) = "home/$foodId"
    }
}