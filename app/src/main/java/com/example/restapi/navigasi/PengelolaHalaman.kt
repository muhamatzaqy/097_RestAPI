package com.example.restapi.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.restapi.ui.theme.home.screen.DestinasiEntry
import com.example.restapi.ui.theme.home.screen.DestinasiHome
import com.example.restapi.ui.theme.home.screen.DetailsDestination
import com.example.restapi.ui.theme.home.screen.DetailsScreen
import com.example.restapi.ui.theme.home.screen.EditDestination
import com.example.restapi.ui.theme.home.screen.EntryKontakScreen
import com.example.restapi.ui.theme.home.screen.HomeScreen
import com.example.restapi.ui.theme.home.screen.ItemEditScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = {})
        }
        composable(DestinasiEntry.route) {
            EntryKontakScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(
                            DestinasiHome.route
                        ) { inclusive = true }
                    }
                },
            )
        }
        composable(
            DetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailsDestination.kontakId) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt(DetailsDestination.kontakId)
            itemId?.let {
                DetailsScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { itemId ->
                        navController.navigate("${EditDestination.route}/$itemId")
                        println(itemId)
                    }

                )
            }
        }
        composable(
            EditDestination.routeWithArgs,
            arguments = listOf(navArgument(EditDestination.kontakId) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}