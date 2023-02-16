package com.walletconnect.codingchallenge.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.walletconnect.codingchallenge.ui.screens.clothingdetails.ClothingDetailsScreen
import com.walletconnect.codingchallenge.ui.screens.clothinglist.ClothingListScreen

@Composable
fun FashionConnectNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Home.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.Home.route) {
            ClothingListScreen(navigateToDetails = {
                navController.navigate("${Destinations.Details.route}/${it.id}")
            })
        }
        composable(
            "${Destinations.Details.route}/{clothingId}",
            arguments = listOf(
                navArgument("clothingId") {
                    this.type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val itemId = it.arguments?.getInt("clothingId")
            if (itemId != null) {
                ClothingDetailsScreen(itemId, navigateBack = { navController.navigateUp() })
            }
        }
    }
}

enum class Destinations(val route: String) {
    Home("home"),
    Details("details")
}
