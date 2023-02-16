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
    startDestination: String = Destinations.Home.name
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.Home.name) {
            ClothingListScreen(navigateToDetails = {
                navController.navigate("details/${it.id}")
            })
        }
        composable(
            "${Destinations.Details.name}/{clothingId}",
            arguments = listOf(
                navArgument("clothingId") {
                    this.type = NavType.IntType
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

enum class Destinations(name: String) {
    Home("home"),
    Details("details")
}
