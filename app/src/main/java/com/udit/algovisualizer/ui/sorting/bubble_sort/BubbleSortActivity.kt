package com.udit.algovisualizer.ui.sorting.bubble_sort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.udit.algovisualizer.ui.sorting.bubble_sort.ui.theme.AlgoVisualizerTheme
import kotlin.random.Random

class BubbleSortActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlgoVisualizerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    numList(padding = innerPadding)
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
