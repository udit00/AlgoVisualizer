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
//                    numList(innerPadding)
//                    LazyColumn(
//
//                    ) {
//
//                    }
                    LazyColumn(
                        modifier =
                    ) {
                        items(buttonList.size) {

                        }
                    }
                }
            }
        }
    }
}


