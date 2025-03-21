package com.udit.algovisualizer.ui.sorting.selection_sort

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@SuppressLint("UnrememberedAnimatable")
@Composable
fun SelectionSortScreen(navController: NavController) {
    var list by remember { mutableStateOf(listOf(10, 20, 30, 40)) }
    val positions = remember { mutableStateMapOf<Int, Animatable<Float, AnimationVector1D>>() }
    val scope = rememberCoroutineScope()

    // Initialize animation values for each item
    LaunchedEffect(list) {
        list.forEach { item ->
            if (!positions.containsKey(item)) {
                positions[item] = Animatable(0f)
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        list.forEach { item ->
            val offsetX = positions[item] ?: Animatable(0f)

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .background(Color.Blue, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        scope.launch {
                            swapWithAnimation(positions, list) { updatedList ->
                                list = updatedList
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.toString(), color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

// Function to animate swapping between two indices
suspend fun swapWithAnimation(
    positions: MutableMap<Int, Animatable<Float, AnimationVector1D>>,
    currentList: List<Int>,
    updateList: (List<Int>) -> Unit
) {
    val firstIndex = 1
    val secondIndex = 3
    val firstItem = currentList[firstIndex]
    val secondItem = currentList[secondIndex]

    val distance = 200f // Adjust based on item width

    coroutineScope {
        val firstAnimation = launch {
            positions[firstItem]?.animateTo(distance, animationSpec = tween(300))
        }
        val secondAnimation = launch {
            positions[secondItem]?.animateTo(-distance, animationSpec = tween(300))
        }

        firstAnimation.join()
        secondAnimation.join()
    }

    // Swap in the list after animation completes
    val newList = currentList.toMutableList().apply { swap(firstIndex, secondIndex) }
    updateList(newList)

    // Reset offsets after swap
    coroutineScope {
        positions[firstItem]?.snapTo(0f)
        positions[secondItem]?.snapTo(0f)
    }
}

// Swap function
fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}