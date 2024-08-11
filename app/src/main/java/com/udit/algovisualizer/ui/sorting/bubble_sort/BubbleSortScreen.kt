package com.udit.algovisualizer.ui.sorting.bubble_sort

import GradientButton
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.udit.algovisualizer.ui.MyApp
import com.udit.algovisualizer.ui.main_activity.data.Screen
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleSortScreen(navController: NavController, viewModel: BubbleSortViewModel = viewModel()) {



    val TAG = "BubbleSortScreen"

    val buttonName by viewModel.buttonName.collectAsState()
    val isListSorted by viewModel.isListSorted.collectAsState()
    val randomNumbers by viewModel.randomNumbers.collectAsState()
    val openSettings by viewModel.openSettingsFlow.collectAsState()
    val selectedView by viewModel.selectedSettingsView.collectAsState()
    val isSorting by viewModel.isSorting.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val lifeCycleOwner = LocalLifecycleOwner.current


//    val notifyUserFlow = remember {
//        viewModel.notifyUserFlow
//    }

    fun debugLog(msg: String) {
        Log.d(TAG, msg)
    }

    fun showToastBar(msg: String) {
        MyApp.showToast(msg)
    }


    fun showSnackBar(msg: String) {
        if(msg == "") return
        scope.launch {
            snackBarHostState.showSnackbar(msg)
        }
    }

//    fun setObserver() {

//        scope.launch {
//            notifyUserFlow.collect { msg ->
//                debugLog("SNACK_BAR_OUT")
//                if(msg != "") {
//                    debugLog("SNACK_BAR")
//                    snackBarHostState.showSnackbar(msg)
//                }
//            }
//            viewModel.notifyUserFlow.collectLatest { msg ->
//                debugLog("SNACK_BAR_OUT")
//                if(msg != "") {
//                    debugLog("SNACK_BAR")
//                    snackBarHostState.showSnackbar(msg)
//                }
//            }
//        }
//    }


    LaunchedEffect(key1 = TAG) {
        debugLog("setObserver")
//        viewModel.notifyUserFlow.observe(lifeCycleOwner) { msg ->
//            debugLog("SNACK_BAR_OUT")
//            if(msg != "") {
//                debugLog("SNACK_BAR")
//                viewModel.viewModelScope.launch {
//                    snackBarHostState.showSnackbar(msg)
//                }
//            }
//        }

//        setObserver()
//        viewModel.notifyUserFlow.observe(LocalLifecycleOwner.current) {
//
//        }
    }



    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
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
                actions = {
                    IconButton(
                        onClick = {
//                            openSettingsAlert()
                            viewModel.showSettings()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
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
//        snackbarHost = ,
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                onClick = {
                    if(isListSorted) {
                        viewModel.regenerateList()
                    } else {
                        viewModel.sortList()
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
        when(selectedView) {
            BubbleSortViewModel.Companion.SettingsView.CARD -> cardView(padding = innerPadding, viewModel = viewModel)
            BubbleSortViewModel.Companion.SettingsView.LINES -> lineView(padding = innerPadding, viewModel = viewModel)
        }
        if(openSettings) {
//            openSettingsAlert(viewModel = viewModel)
//            if(isSorting) {
//
//            } else {
                openSettingsBottomSheet(viewModel = viewModel)
//            }
        }

    }
}

@Composable
private fun openSettingsAlert(viewModel: BubbleSortViewModel) {
    AlertDialog(
        title = { Text(text = "Settings") },
        titleContentColor = Color.Gray,
        textContentColor = Color.Green,
        dismissButton = {
            Button(
                onClick = {
                    viewModel.hideSettings()
                }) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.hideSettings()
                }) {
                Text(text = "Confirm")
            }
        },
        onDismissRequest = {
            viewModel.hideSettings()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun openSettingsBottomSheet(viewModel: BubbleSortViewModel) {

    val selectedSpeed by viewModel.selectedSettingSpeed.collectAsState()
    val selectedView by viewModel.selectedSettingsView.collectAsState()
    val notSelectedSettingsColor by viewModel.notSelectedSettingColor.collectAsState()
    val selectedSettingsColor by viewModel.selectedSettingColor.collectAsState()

    ModalBottomSheet(
        onDismissRequest = { viewModel.hideSettings() },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    title = {
                        Text(
                            text = "Settings"
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.hideSettings()
                            },
                        ) {
//                            Text(
//                                color = Color.Red,
//                                text = "CLOSE"
//                            )
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Settings"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Column {
                    Text(
                        text = "Speed",
                        fontSize = TextUnit(value = 23f, type = TextUnitType.Sp),
                        modifier = Modifier.padding(10.dp).fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        GradientButton(
                            text = BubbleSortViewModel.Companion.SettingsSpeed.POINT_TWO_FIVE.btnName,
                            textColor = Color.White,
                            borderColor = Color.Black,
                            gradientColors = if(selectedSpeed == BubbleSortViewModel.Companion.SettingsSpeed.POINT_TWO_FIVE) {
                                selectedSettingsColor
                            } else {
                                notSelectedSettingsColor
                            },
                            onClick = {
                                viewModel.changeDelayTime(
                                    runningSpeed = BubbleSortViewModel.Companion.SPEED.SLOWEST,
                                    selectedSpeed = BubbleSortViewModel.Companion.SettingsSpeed.POINT_TWO_FIVE
                                )
                            }
                        )
                        GradientButton(
                            text = BubbleSortViewModel.Companion.SettingsSpeed.POINT_FIVE.btnName,
                            textColor = Color.White,
                            borderColor = Color.Black,
                            gradientColors = if(selectedSpeed == BubbleSortViewModel.Companion.SettingsSpeed.POINT_FIVE) {
                                selectedSettingsColor
                            } else {
                                notSelectedSettingsColor
                            },
                            onClick = {
                                viewModel.changeDelayTime(
                                    runningSpeed = BubbleSortViewModel.Companion.SPEED.MODERATE,
                                    selectedSpeed = BubbleSortViewModel.Companion.SettingsSpeed.POINT_FIVE
                                )
                            }
                        )
                        GradientButton(
                            text = BubbleSortViewModel.Companion.SettingsSpeed.ONE.btnName,
                            textColor = Color.White,
                            borderColor = Color.Black,
                            gradientColors = if(selectedSpeed == BubbleSortViewModel.Companion.SettingsSpeed.ONE) {
                                selectedSettingsColor
                            } else {
                                notSelectedSettingsColor
                            },
                            onClick = {
                                viewModel.changeDelayTime(
                                    runningSpeed = BubbleSortViewModel.Companion.SPEED.FASTEST,
                                    selectedSpeed = BubbleSortViewModel.Companion.SettingsSpeed.ONE
                                )
                            }
                        )
//                        Button(
//                            onClick = {
//                                viewModel.changeDelayTime(BubbleSortViewModel.Companion.SPEED.SLOWEST)
//                            },
//                        ) {
//                            Text(text = "0.25x")
//                        }
//                        Button(onClick = {
//                            viewModel.changeDelayTime(BubbleSortViewModel.Companion.SPEED.MODERATE)
//                        }) {
//                            Text(text = "0.5x")
//                        }
//                        Button(onClick = {
//                            viewModel.changeDelayTime(BubbleSortViewModel.Companion.SPEED.FASTEST)
//                        }) {
//                            Text(text = "1.0x")
//                        }
                    }
                }
                Column {
                    Text(
                        text = "View",
                        fontSize = TextUnit(value = 23f, type = TextUnitType.Sp),
                        modifier = Modifier.padding(10.dp).fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        GradientButton(
                            text = BubbleSortViewModel.Companion.SettingsView.CARD.name,
                            textColor = Color.White,
                            borderColor = Color.Black,
                            gradientColors = if(selectedView == BubbleSortViewModel.Companion.SettingsView.CARD) {
                                selectedSettingsColor
                            } else {
                                notSelectedSettingsColor
                            },
                            onClick = {
                                viewModel.changeSettingView(BubbleSortViewModel.Companion.SettingsView.CARD)
                            }
                        )
                        GradientButton(
                            text = BubbleSortViewModel.Companion.SettingsView.LINES.name,
                            textColor = Color.White,
                            borderColor = Color.Black,
                            gradientColors = if(selectedView == BubbleSortViewModel.Companion.SettingsView.LINES) {
                                selectedSettingsColor
                            } else {
                                notSelectedSettingsColor
                            },
                            onClick = {
                                viewModel.changeSettingView(BubbleSortViewModel.Companion.SettingsView.LINES)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun lineView(padding: PaddingValues, viewModel: BubbleSortViewModel) {
    val delayTime by viewModel.delayTime.collectAsState()
    val randomNumbers by viewModel.randomNumbers.collectAsState()
    val selectedCards by viewModel.selectedCards.collectAsState()
//    val buttonName by viewModel.buttonName.collectAsState()
    val defaultCardColor by viewModel.defaultCardColor.collectAsState()
    val firstSelectedCardColor by viewModel.firstSelectedCardColor.collectAsState()
    val secondSelectedCardColor by viewModel.secondSelectedCardColor.collectAsState()
    val sortedCardColors by viewModel.sortedCardColors.collectAsState()
    val isListSorted by viewModel.isListSorted.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
//            verticalArrangement = Arrangement.spacedBy(15.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
//            contentPadding = padding
        ) {
            itemsIndexed(
                items = randomNumbers,
                key = { index, item -> item.id }
            ) { index, randomNumber ->
                Box(

                    modifier = Modifier
                        .height((randomNumber.num * 3).dp)
                        .width(15.dp)
                        .background(Color.Gray)
                        .animateItem(
                            fadeInSpec = tween(100),
                            fadeOutSpec = tween(100),
                            placementSpec = tween(delayTime.toInt())
                        )
                        .border(
                            border = BorderStroke(
                                1.dp,
                                color = if (isListSorted) {
                                    sortedCardColors
                                } else {
                                    when (index) {
                                        in selectedCards -> {
                                            if (index % 2 == 0) firstSelectedCardColor
                                            else secondSelectedCardColor
                                        }

                                        else -> {
                                            if (randomNumber.sorted) sortedCardColors
                                            else defaultCardColor
                                        }
                                    }
                                }
//                    color = Color.Red
                            ),
                        )
                )
            }
        }
    }
}

@Composable
private fun cardView(padding: PaddingValues, viewModel: BubbleSortViewModel) {

    val delayTime by viewModel.delayTime.collectAsState()
    val randomNumbers by viewModel.randomNumbers.collectAsState()
    val selectedCards by viewModel.selectedCards.collectAsState()
//    val buttonName by viewModel.buttonName.collectAsState()
    val defaultCardColor by viewModel.defaultCardColor.collectAsState()
    val firstSelectedCardColor by viewModel.firstSelectedCardColor.collectAsState()
    val secondSelectedCardColor by viewModel.secondSelectedCardColor.collectAsState()
    val sortedCardColors by viewModel.sortedCardColors.collectAsState()
    val isListSorted by viewModel.isListSorted.collectAsState()

    val selectedSpeed by viewModel.selectedSettingSpeed.collectAsState()
    val selectedView by viewModel.selectedSettingsView.collectAsState()
    val notSelectedSettingsColor by viewModel.notSelectedSettingColor.collectAsState()
    val selectedSettingsColor by viewModel.selectedSettingColor.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .weight(0.5F)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
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
                    key = { index, item -> item.id }
                ) { index, randomNumber ->
                    Card(

                        border = BorderStroke(
                            2.dp,
                            color = if (isListSorted) {
                                sortedCardColors
                            } else {
                                when (index) {
                                    in selectedCards -> {
                                        if (index % 2 == 0) firstSelectedCardColor
                                        else secondSelectedCardColor
                                    }

                                    else -> {
                                        if (randomNumber.sorted) sortedCardColors
                                        else defaultCardColor
                                    }
                                }
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
        Column(
            modifier = Modifier
                .weight(0.5F)
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            GradientButton(
                text = BubbleSortViewModel.Companion.SettingsSpeed.POINT_TWO_FIVE.btnName,
                textColor = Color.White,
                borderColor = Color.Black,
                gradientColors = if(selectedSpeed == BubbleSortViewModel.Companion.SettingsSpeed.POINT_TWO_FIVE) {
                    selectedSettingsColor
                } else {
                    notSelectedSettingsColor
                },
                onClick = {
                    viewModel.changeDelayTime(
                        runningSpeed = BubbleSortViewModel.Companion.SPEED.SLOWEST,
                        selectedSpeed = BubbleSortViewModel.Companion.SettingsSpeed.POINT_TWO_FIVE
                    )
                }
            )
            GradientButton(
                text = BubbleSortViewModel.Companion.SettingsSpeed.POINT_FIVE.btnName,
                textColor = Color.White,
                borderColor = Color.Black,
                gradientColors = if(selectedSpeed == BubbleSortViewModel.Companion.SettingsSpeed.POINT_FIVE) {
                    selectedSettingsColor
                } else {
                    notSelectedSettingsColor
                },
                onClick = {
                    viewModel.changeDelayTime(
                        runningSpeed = BubbleSortViewModel.Companion.SPEED.MODERATE,
                        selectedSpeed = BubbleSortViewModel.Companion.SettingsSpeed.POINT_FIVE
                    )
                }
            )
            GradientButton(
                text = BubbleSortViewModel.Companion.SettingsSpeed.ONE.btnName,
                textColor = Color.White,
                borderColor = Color.Black,
                gradientColors = if(selectedSpeed == BubbleSortViewModel.Companion.SettingsSpeed.ONE) {
                    selectedSettingsColor
                } else {
                    notSelectedSettingsColor
                },
                onClick = {
                    viewModel.changeDelayTime(
                        runningSpeed = BubbleSortViewModel.Companion.SPEED.FASTEST,
                        selectedSpeed = BubbleSortViewModel.Companion.SettingsSpeed.ONE
                    )
                }
            )
        }
    }
}
