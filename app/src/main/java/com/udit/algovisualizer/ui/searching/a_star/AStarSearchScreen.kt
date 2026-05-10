package com.udit.algovisualizer.ui.searching.a_star

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val gridSize = 12
val cellSize = 40
@Composable
fun AStarSearchScreen() {
    var triggerAnimation by remember { mutableStateOf(false) }
    var showResetBtn by remember { mutableStateOf(false) }
    var resetGrid by remember { mutableStateOf(false) }

    Scaffold (
        modifier = Modifier,
        bottomBar = {
            if(!showResetBtn) {
                Button(
                    modifier = Modifier.fillMaxWidth().navigationBarsPadding()
                        .padding(bottom = 10.dp),
                    onClick = {
                        triggerAnimation = true
                        showResetBtn = true
                    },
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color.Black.copy(alpha = 0.5f))
                ) {
                    Text("Start")
                }
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth().navigationBarsPadding()
                        .padding(bottom = 10.dp),
                    onClick = {
                        resetGrid = true
                        showResetBtn = false
                    },
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color.Black.copy(alpha = 0.5f))
                ) {
                    Text("Reset")
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
//            NewCell(animateCell = triggerAnimation)
            CellGrid(
                nByM = Pair(gridSize,gridSize),
                startAnimation = triggerAnimation,
                onAnimationCompleted = {
                    triggerAnimation = false
                },
                resetGrid = resetGrid,
                onResetCompleted = {
                    resetGrid = false
                }
            )
        }
    }

}

@Composable
private fun CellGrid(
    nByM: Pair<Int, Int>,
    startAnimation: Boolean,
    onAnimationCompleted: () -> Unit,
    resetGrid: Boolean,
    onResetCompleted: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val cellStates: MutableList<CellState> = remember {
        mutableStateListOf<CellState>().apply {
            repeat(gridSize * gridSize) {
                add(CellState.INITIAL)
            }
        }
    }
    var triggerAnimation by remember { mutableStateOf(false) }
    var animationJob by remember { mutableStateOf<Job?>(null) }

    fun startAnimation() {
        if(!triggerAnimation) {
            animationJob?.cancel()
            animationJob = scope.launch(Dispatchers.Default) {
                triggerAnimation = true
                for (i in 0 until gridSize * gridSize) {
                    cellStates[i] = CellState.ANIMATE
                    delay(100)
                }
                triggerAnimation = false
                onAnimationCompleted.invoke()
            }
        }
    }

    fun resetGrid() {
        animationJob?.cancel()

        cellStates.forEachIndexed { index, _ ->
            cellStates[index] = CellState.INITIAL
        }

        scope.launch {
            delay(200)
            onResetCompleted.invoke()
        }
    }

    LaunchedEffect(startAnimation, resetGrid) {
        if (resetGrid) {
            resetGrid()
        }
        else if(startAnimation) {
            startAnimation()
        }
    }



    var items: List<Int> = remember {
        List(nByM.first * nByM.second) { it }
    }
    Box(
        modifier = Modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(cellSize.dp),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(
                start = 10.dp,
                end = 10.dp,
                top = 20.dp
            )
        ) {
            itemsIndexed(
                items, key = { index: Int, item: Int -> "index_${index}_item_${item}" },
            ) { index, item ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    println("ANIMATION_LOG: index: $index animationCellState: ${cellStates[index]}")
                    NewCell(
                        outerCellState = cellStates[index],
                        onAnimationCompleted = {
                            cellStates[index] = CellState.VISITED
                        }
                    )
                }
            }
        }
    }
}

enum class CellState {
    INITIAL,
    ANIMATE,
    VISITED
}

@Composable
private fun NewCell(
    outerCellState: CellState,
    onAnimationCompleted: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val completeAnimationTime = 1000
    val cellShape = RoundedCornerShape(10.dp)
    val visitedProgress by animateFloatAsState(
        targetValue = if (outerCellState != CellState.INITIAL) 1f else 0f,
        animationSpec = tween(durationMillis = completeAnimationTime)
    )
    val colors = listOf(
        Color(0xFF4966A3),
        Color(0xFF5386D7),
        Color(0xFF5386D7),
        Color(0xFF5B9CE3),
        Color(0xFF5B9CE3),
        Color(0xFF6ADCCC),
        Color(0xFF67D0E6),
    )

    fun multiColorLerpForAnimation(
        progress: Float,
        colors: List<Color>
    ): Color {
        if (colors.isEmpty()) return Color.Transparent
        if (colors.size == 1) return colors.first()

        val p = progress.coerceIn(0f, 1f)

        val scaled = p * (colors.size - 1)
        val index = scaled.toInt()
        val fraction = scaled - index

        val start = colors[index]
        val end = colors.getOrElse(index + 1) { start }

        return lerp(start, end, fraction)
    }

    fun multiColorLerp(progress: Float): Color {
        return when (outerCellState) {
            CellState.INITIAL -> Color.White
            CellState.VISITED -> lerp(
                colors.last(),
                Color(0xFF66D1E6),
                1f
            )
            CellState.ANIMATE -> multiColorLerpForAnimation(
                progress = progress,
                colors = colors
            )
        }
    }

    val bgColor = multiColorLerp(visitedProgress)

//    LaunchedEffect(outerCellState) {
//        if(outerCellState == CellState.ANIMATE && cellState == CellState.INITIAL) {
//            animateVisited = true
//            cellState = CellState.ANIMATE
//            startAnimationTimer()
//        } else if(outerCellState == CellState.INITIAL && cellState != CellState.ANIMATE) {
//            cellState = CellState.INITIAL
//            animateVisited = false
//        }
//    }

    LaunchedEffect(outerCellState) {
        when(outerCellState) {
            CellState.INITIAL -> {}
            CellState.ANIMATE -> {
                delay((completeAnimationTime).toLong())
                onAnimationCompleted.invoke()
            }
            CellState.VISITED -> {}
        }
    }

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = cellShape
            )

            .clip(cellShape)
            .size(cellSize.dp)

    ) {
        if(outerCellState != CellState.INITIAL) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(visitedProgress) // 👈 grows from center
                    .background(
                        color = bgColor,
                        shape = cellShape
                    )
            )
        }
    }
}
