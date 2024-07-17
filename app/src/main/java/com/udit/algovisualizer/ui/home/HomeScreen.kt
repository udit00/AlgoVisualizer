package com.udit.algovisualizer.ui.home

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.udit.algovisualizer.R
import com.udit.algovisualizer.ui.home.data.ButtonType
import com.udit.algovisualizer.ui.home.data.MenuButton
import com.udit.algovisualizer.ui.main_activity.data.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val TAG = "HOME_SCREEN"
    var buttonList: ArrayList<MenuButton>

    val searchingButton = MenuButton(
        name = ButtonType.Searching,
        image = R.drawable.filter_image
//            image = android.R.drawable.f
    )
    val sortingButton = MenuButton(
        name = ButtonType.Sorting,
        image = R.drawable.filter_image
    )
    buttonList = arrayListOf(
        searchingButton,
        sortingButton
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            items(
                items = buttonList
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(60.dp),
                    onClick = {
                        when(it.name) {
                            ButtonType.Sorting -> {
                                navController.navigate(Screen.BubbleSortScreen)
                            }
                            ButtonType.Searching -> {
                                navController.navigate(Screen.BinarySearchScreen)
                            }
                            else -> {
                                Log.e(TAG, "ENUM NOT SET AT ANY OBJECT", )
                            }
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = it.name.toString()
                    )
                }
//                            Spacer(modifier = Modifier.padding(2.dp))
//                            Card(
//
//                                border = BorderStroke(2.dp, color = Color.Red),
//                                modifier = Modifier
//                                    .animateItemPlacement(
//                                        animationSpec = tween(2000)
//                                    )
//
//                            ) {
//                                Image(
//                                    modifier = Modifier
//                                        .padding(50.dp)
//                                        .fillMaxWidth()
////                                        .height(100.dp)
//                                    ,
//                                    painter = painterResource(id = it.image),
//                                    contentDescription = "TE"
//                                )
//                                Text(
//                                    text = it.name,
//                                    fontSize = 20.sp,
//                                    modifier = Modifier
//                                        .padding(30.dp)
//                                )
//                            }
            }
        }
    }
}