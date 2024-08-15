package com.udit.algovisualizer.ui.sorting.sorting_options

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udit.algovisualizer.R
import com.udit.algovisualizer.ui.MyApp
import com.udit.algovisualizer.ui.main_activity.data.Screen
import com.udit.algovisualizer.ui.sorting.sorting_options.data.SortingOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortingOptionsScreen(navController: NavController) {

    val TAG: String = "SortingOptions"
//    val arrayOfSortingAlgorithms: List<String> = listOf("Bubble Sort", "Insertion Sort", "Selection Sort")
    val arrayOfSortingAlgorithms: List<SortingOptions> = listOf(
        SortingOptions(name = "Bubble Sort", screen = Screen.BubbleSortScreen, color = Color.Red),
        SortingOptions(name = "Insertion Sort", screen = Screen.InsertionSortScreen, color = Color.Gray),
        SortingOptions(name = "Selection Sort", screen = Screen.SelectionSortScreen, color = Color.Green)
    )
            
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Gray,
                    scrolledContainerColor = Color.Green,
                    navigationIconContentColor = Color.Cyan
                ),
                title = {
                    Text(text = "Sorting Options")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back To Home"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(
                items = arrayOfSortingAlgorithms,
                key = { index: Int, item: SortingOptions -> item.id }
            ) { index, algorithm ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 5.dp),
//                        .padding(start = 5.dp, top = 2.dp, end = 5.dp, bottom = 0.dp),
                    onClick = { 
                        MyApp.debugLog(TAG, "CARD CLICK")
                        navController.navigate(algorithm.screen)
                    },
                    border = BorderStroke(width = 1.dp, color = algorithm.color ?: Color.Cyan)
//                    colors = CardColors(containerColor = algorithm.color)
                ) {
                    Image(painter = painterResource(id = R.drawable.filter_image), contentDescription = "")
                    Text(
                        modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(10.dp),
                        style = TextStyle(fontSize = TextUnit(value = 20f, type = TextUnitType.Sp)),
                        textAlign = TextAlign.Center,
                        text = algorithm.name
                    )
//                    Row(
//                        modifier = Modifier.fillMaxSize()
//                            .align(Alignment.CenterHorizontally)
//                            .background(Color.Red)
//                    ) {
//
//                    }

                }
            }
        }
    }
}