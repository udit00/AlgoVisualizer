package com.udit.algovisualizer.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.udit.algovisualizer.ui.home.ui.theme.AlgoVisualizerTheme
import kotlin.random.Random

class HomeActivity : ComponentActivity() {

    val TAG: String = "HomeActivity"

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlgoVisualizerTheme {
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
                                Text(text = "Home")
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        Log.d(TAG, "open Profile Nav Bar")
                                    }
                                ) {
                                  Icon(
                                      imageVector = Icons.Default.AccountCircle,
                                      contentDescription = "Profile"
                                  )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    numList(innerPadding)
                }
            }
        }
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
//    val list = generateRandomNumbers(6).toMutableList()
    var randomNumbers = remember {
//        generateRandomNumbers(6).toMutableList()
        mutableStateOf(listOf(1,2,3,4,5,6))
//        mutableStateListOf(mutableListOf(1,2,3,4,5,6))
    }
    val buttonName = remember {
        mutableStateOf("Shuffle")
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
                        animationSpec = tween(2000)
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
                    val tempList = randomNumbers.value.toMutableList()
                    val temp = tempList[0]
                    tempList[0] = tempList[3]
                    tempList[3] = temp
                    randomNumbers.value = tempList
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

@Preview(showBackground = true)
@Composable
fun TwoCards(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(50.dp))

        Card(
            modifier = Modifier.align(Alignment.CenterHorizontally),


//                .padding(150.dp)
//                .background(color = Color.Red)
        ) {
            Text(
                text = "A",
                fontSize = 20.sp,
                modifier = Modifier.padding(50.dp)

//                    .background(color = Color.Red)
            )
        }
        Spacer(modifier = Modifier.padding(60.dp))
        Card(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "B",
                fontSize = 20.sp,
                modifier = Modifier.padding(50.dp)

//                    .background(color = Color.Red)
            )
        }
    }
}
