package com.udit.algovisualizer.ui.sorting.bubble_sort

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleSortScreen(navController: NavController) {

    val TAG = "BubbleSortScreen"

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
                }
            )
        }
    ) { innerPadding ->
        numList(padding = innerPadding)
    }
}


fun generateRandomNumbers(count: Int): List<Int> {
    val list = generateSequence {
        Random.nextInt(1, 10)
    }
        .distinct()
        .take(6)
        .toSet()
        .toList()
    list.forEachIndexed { index, i ->
        Log.d("RANDOM_NUMBERS",i.toString());
    }

    return list
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun numList(padding: PaddingValues) {
    val coroutineScope = rememberCoroutineScope()

    val delayTime = remember {
        mutableStateOf(1000.toLong())
    }
//    val list = generateRandomNumbers(6).toMutableList()
    var randomNumbers = remember {
//        generateRandomNumbers(6).toMutableList()
//        mutableStateOf(listOf(1,2,3,4,5,6))
        mutableStateOf(listOf(4,2,6,5,1,3))
//        mutableStateListOf(mutableListOf(1,2,3,4,5,6))
    }
    val buttonName = remember {
        mutableStateOf("Shuffle")
    }

    fun swapElement(arr: MutableList<Int>, leftIndex: Int, rightIndex: Int): MutableList<Int> {
        val temp = arr[leftIndex]
        arr[leftIndex] = arr[rightIndex]
        arr[rightIndex] = temp
        return arr
    }

    suspend fun bubbleSort() {
        for(i in randomNumbers.value.indices) {
            var leftPtr = 0
            var rightPtr = 1
            var tempArr: MutableList<Int>
            for(j in 0 until randomNumbers.value.size - i) {
                tempArr = randomNumbers.value.toMutableList()
                if(leftPtr >= tempArr.size || rightPtr >= tempArr.size) {
//                               j = randomNumbers.value.size + i
                    break
                }
                if(tempArr[leftPtr] > tempArr[rightPtr]) {
                    tempArr = swapElement(tempArr, leftPtr, rightPtr)
//                                randomNumbers.value = swapElement(tempArr, leftPtr, rightPtr)
                }
                randomNumbers.value = tempArr
                delay(timeMillis = delayTime.value * 2)
                leftPtr++
                rightPtr++
            }
        }
//        randomNumbers.
//        delay(1000)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = padding
//        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 100.dp)
    ) {
        items(
            items = randomNumbers.value,
            key = { it },
        ) {
            Card(

                border = BorderStroke(2.dp, color = Color.Red),
                modifier = Modifier
                    .animateItemPlacement(
                        animationSpec = tween(delayTime.value.toInt())
                    )

//                .padding(150.dp)
//                .background(color = Color.Red)
            ) {
                Text(
                    text = it.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(30.dp)


//                    .background(color = Color.Red)
                )
            }
        }
        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        bubbleSort()
                    }
//                    val tempList = randomNumbers.value.toMutableList()
//                    val temp = tempList[0]
//                    tempList[0] = tempList[3]
//                    tempList[3] = temp
//                    randomNumbers.value = tempList

                    Log.d("RANDOM_NUM", randomNumbers.toString())
//                    randomNumbers = randomNumbers.shuffled().toMutableList()
                    buttonName.value = "CHANGED"
                }
            ) {
                Text(text = buttonName.value)
            }
        }
    }
}