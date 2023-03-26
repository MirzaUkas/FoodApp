package com.mirz.foodapp

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mirz.foodapp.ui.navigation.NavigationItem
import com.mirz.foodapp.ui.navigation.Screen
import com.mirz.foodapp.ui.screen.detail.DetailFoodScreen
import com.mirz.foodapp.ui.screen.home.HomeScreen
import com.mirz.foodapp.ui.screen.profile.ProfileScreen


@Composable
fun FoodApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    Scaffold {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(it)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { foodId ->
                        navController.navigate(Screen.DetailFood.createRoute(foodId))
                    },
                    navigateToProfile = { navController.navigate(Screen.Profile.route) }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
            composable(
                Screen.DetailFood.route,
                arguments = listOf(navArgument("foodId") { type = NavType.IntType }),
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("foodId") ?: -1
                DetailFoodScreen(
                    foodId = id,
                    shareAction = { summary ->
                        shareFood(context, summary)
                    },
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}


private fun shareFood(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.delicious_food))
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.delicious_food)))
}


