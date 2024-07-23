package com.udit.algovisualizer.ui.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.udit.algovisualizer.ui.home.HomeScreen
import com.udit.algovisualizer.ui.main_activity.data.Screen
import com.udit.algovisualizer.ui.main_activity.ui.theme.AlgoVisualizerTheme
import com.udit.algovisualizer.ui.searching.binary_search.BinarySearchScreen
import com.udit.algovisualizer.ui.searching.linear_search.LinearSearchScreen
import com.udit.algovisualizer.ui.sorting.bubble_sort.BubbleSortScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AlgoVisualizerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen) {
        composable<Screen.HomeScreen> {
//            val args = it.toRoute<Screen.HomeScreen>()
            HomeScreen(navController)
        }
        composable<Screen.BubbleSortScreen> {
//            val args = it.toRoute<Screen.BubbleSortScreen>()
            BubbleSortScreen(navController)
        }
        composable<Screen.BinarySearchScreen> {
//            val args = it.toRoute<Screen.BinarySearchScreen>()
            BinarySearchScreen(navController)
        }
        composable<Screen.LinearSearchScreen> {
            LinearSearchScreen(navController)
        }
    }

}
