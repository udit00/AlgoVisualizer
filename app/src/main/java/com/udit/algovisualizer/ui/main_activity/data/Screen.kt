package com.udit.algovisualizer.ui.main_activity.data

import androidx.navigation.NavController
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
@Serializable
sealed class Screen(val route: String) {
//    @Serializable data class HomeScreen(): Screen(NavigationRoutes.homeScreenRoute)
//    @Serializable data class BinarySearchScreen(@Contextual val navController: NavController): Screen(NavigationRoutes.binarySearchScreen)
//    @Serializable data class BubbleSortScreen(@Contextual val navController: NavController): Screen(NavigationRoutes.bubbleSortScreen)
    @Serializable object HomeScreen: Screen(NavigationRoutes.homeScreenRoute)
    @Serializable object BinarySearchScreen: Screen(NavigationRoutes.binarySearchScreen)
    @Serializable object BubbleSortScreen: Screen(NavigationRoutes.bubbleSortScreen)
}
