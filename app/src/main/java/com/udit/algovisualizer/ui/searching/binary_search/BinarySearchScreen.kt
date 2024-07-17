package com.udit.algovisualizer.ui.searching.binary_search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BinarySearchScreen(navController: NavController) {
    Text(
        text = "Hello from Binary Search Screen!",
        modifier = Modifier.fillMaxSize()
    )
}