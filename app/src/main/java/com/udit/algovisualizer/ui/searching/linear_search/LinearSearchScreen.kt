package com.udit.algovisualizer.ui.searching.linear_search

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinearSearchScreen(navController: NavController, viewModel: LinearSearchViewModel = viewModel()) {

    val buttonName by viewModel.buttonName.collectAsState()
    val isListSearched by viewModel.isListSearched.collectAsState()
    val randomNumbers by viewModel.randomNumbers.collectAsState()

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
                    Text(text = "Sorting")
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
//                actions = {
//                    IconButton(
//                        onClick = {
//                            viewModel.regenerateList()
//                        }) {
//                        Icon(
//                            imageVector = Icons.Default.Refresh,
//                            contentDescription = "Refresh"
//                        )
//                    }
//                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                onClick = {
                    if(isListSearched) {
                        viewModel.regenerateList()
                    } else {
                        viewModel.searchList(10)
                    }
                    Log.d("RANDOM_NUM", randomNumbers.toString())
                }) {
                Text(
                    fontSize = 20.sp,
                    text = buttonName
                )
            }
        }
    ) { innerPadding ->
        numList(padding = innerPadding, viewModel = viewModel)

    }
}


@Composable
private fun numList(padding: PaddingValues, viewModel: LinearSearchViewModel) {

    val delayTime by viewModel.delayTime.collectAsState()
    val randomNumbers by viewModel.randomNumbers.collectAsState()
    val selectedCards by viewModel.selectedCards.collectAsState()
//    val buttonName by viewModel.buttonName.collectAsState()
    val defaultCardColor by viewModel.defaultCardColor.collectAsState()
    val firstSelectedCardColor by viewModel.firstSelectedCardColor.collectAsState()
    val secondSelectedCardColor by viewModel.secondSelectedCardColor.collectAsState()
    val sortedCardColors by viewModel.sortedCardColors.collectAsState()
    val isListSearched by viewModel.isListSearched.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(20.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = padding
//        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 100.dp)
    ) {
//            items(
//                items = randomNumbers.value,
//                key = { it.num },
//            ) { randomNumber ->
        itemsIndexed(
            items = randomNumbers,
            key = {index, item -> item.id }
        ) { index, randomNumber ->
            Card(

                border = BorderStroke(
                    2.dp,
                    color = if(isListSearched) {
                        sortedCardColors
                    } else {
                        if(index == selectedCards) {
                            firstSelectedCardColor
                        } else {
                            sortedCardColors
                        }
//                        when (index) {
//                            in selectedCards -> {
//                                if (index % 2 == 0) firstSelectedCardColor
//                                else secondSelectedCardColor
//                            }
//
//                            else -> {
//                                if (randomNumber.sorted) sortedCardColors
//                                else defaultCardColor
//                            }
//                        }
                    }
//                    color = Color.Red
                ),
                modifier = Modifier
                    .animateItem(
                        fadeInSpec = tween(100),
                        fadeOutSpec = tween(100),
                        placementSpec = tween(delayTime.toInt())
                    )
//                    .width(randomNumber.num.dp)

//                .padding(150.dp)
//                .background(color = Color.Red)
            ) {
                Text(
                    text = randomNumber.num.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(10.dp)


//                    .background(color = Color.Red)
                )
            }
        }
//        item {
//            Button(
//                modifier = Modifier
////                    .align(Alignment.CenterHorizontally),
//                    .fillMaxSize(),
//                onClick = {
//                    if(isListSorted) {
//                        viewModel.regenerateList()
//                    } else {
//                        viewModel.sortList()
//                    }
//                    Log.d("RANDOM_NUM", randomNumbers.toString())
//                }
//            ) {
//                Text(text = buttonName)
//            }
//        }
    }
}