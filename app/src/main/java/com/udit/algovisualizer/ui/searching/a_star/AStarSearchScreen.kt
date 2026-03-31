package com.udit.algovisualizer.ui.searching.a_star

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val gridSize = 14
@Composable
fun AStarSearchScreen() {
    var triggerAnimation by remember { mutableStateOf(false) }
    var animateCell by remember { mutableIntStateOf(-1) }
    Scaffold (
        modifier = Modifier,
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(bottom = 10.dp),
                onClick = {
                    triggerAnimation = true
                },
                colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Black.copy(alpha = 0.5f))
            ) {
                Text("Start")
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            CellGrid(
                nByM = Pair(gridSize,gridSize),
                animateCell = animateCell
            )
        }
    }

    LaunchedEffect(triggerAnimation) {
        if(triggerAnimation) {
            for(i in 0 until gridSize * gridSize) {
//                for(j in 0 until 20) {
                    animateCell = i
                    delay(100)
//                }
            }
            triggerAnimation = false
        }
    }

}

@Composable
private fun CellGrid(
    nByM: Pair<Int, Int>,
    animateCell: Int = -1
) {
    var items: List<Int> = List(nByM.first * nByM.second) { it }
    Box(
        modifier = Modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            itemsIndexed(items) { index, _ ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SingleCell(animateCell = animateCell == index)
                }
            }
        }
    }

}

@Composable
private fun SingleCell(
    animateCell: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    var showRed by remember { mutableStateOf(false) }

    fun startAnimation() {
        scope.launch {
            showRed = true
            scale.snapTo(1f)
            scale.animateTo(2f, tween(200))
            scale.animateTo(1f, tween(200))
            delay(100) // keep visible a bit longer
            showRed = false
        }
    }

    LaunchedEffect(animateCell) {
        if (animateCell) {
            startAnimation()
        }
    }



    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(10.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
            }
            .background(Color.Gray)
    ) {
        AnimatedVisibility(
            visible = showRed,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            if(showRed) {
                Box(
                    modifier = Modifier.fillMaxSize().size(5.dp).background(Color.Red)
                        .align(Alignment.Center)
                )
            }
        }
    }
}