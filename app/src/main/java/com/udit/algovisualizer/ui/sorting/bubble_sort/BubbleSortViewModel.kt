package com.udit.algovisualizer.ui.sorting.bubble_sort

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.math.MathUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udit.algovisualizer.ui.MyApp
import com.udit.algovisualizer.ui.ScreenDimensions
import com.udit.algovisualizer.ui.sorting.commonSortingData.RandomNumberSorting
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.random.Random

class BubbleSortViewModel(
    val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {

//        object SPEED {
//            val SLOWEST = 100L
//            val MODERATE = 550L
//            val FASTEST = 750L
//        }

//        @Serializable
//        sealed class SettingsSpeed(val displayName: String, val speed: Long) {
//            @Serializable
//            data object SLOWEST: SettingsSpeed("0.25x", 750L)
//            @Serializable
//            data object MODERATE: SettingsSpeed("0.5x",550L)
//            @Serializable
//            data object FASTEST: SettingsSpeed("1.0x",100L)
//        }

        sealed class SettingsSpeed(val displayName: String, val speed: Long) {
            data object SLOWEST: SettingsSpeed("0.25x", 750L)
            data object MODERATE: SettingsSpeed("0.5x",550L)
            data object FASTEST: SettingsSpeed("1.0x",100L)
        }

//        enum class SettingsSpeed(val btnName: String, val speed: Long) {
//            POINT_TWO_FIVE("0.25x", 750L),
//            POINT_FIVE("0.5x", 550L),
//            ONE("1.0x", 100L)
//        }

        enum class SettingsView {
            CARD,
            LINES
        }

        val TAG = "BubbleSortViewModel"
        val randomNumberListTag = "randomNumberListTag"
        val selectedCardsTag = "selectedCardsTag"
        val buttonNameTag = "buttonNameTag"
        val defaultCardColorTag = "defaultCardColorTag"
        val firstSelectedCardColorTag = "firstSelectedCardColorTag"
        val secondSelectedCardColorTag = "secondSelectedCardColorTag"
        val sortedCardColorsTag = "sortedCardColorsTag"
//        val delayTimeTag = "delayTimeTag"
        val isSortingTag = "isSortingTag"
        val showSettingsTag = "showSettingsTag"
        val selectedSettingsSpeedTag = "selectedSettingsSpeedTag"
        val selectedSettingsViewTag = "selectedSettingsViewTag"
        val notSelectedSettingColorTag = "notSelectedSettingColorTag"
        val selectedSettingColorTag = "selectedSettingColorTag"
        val scaffoldStateTag = "scaffoldStateTag"
        val notifyUserTag = "notifyUserTag"

        private val buttonNameBubbleSortString = "Bubble Sort"
        private val buttonNameReShuffleString = "Re-Shuffle"
        private val isListSortedTag = "isListSortedTag"
    }

    var sortingCoroutine: Job? = null

    val screenDimensions: ScreenDimensions = MyApp.getScreenDimensions()
//    val delayTime = savedStateHandle.getStateFlow(delayTimeTag, SettingsSpeed.FASTEST.speed)
    val buttonName = savedStateHandle.getStateFlow(buttonNameTag, buttonNameBubbleSortString)
    val defaultCardColor = savedStateHandle.getStateFlow(defaultCardColorTag, Color.Red)
    val firstSelectedCardColor = savedStateHandle.getStateFlow(firstSelectedCardColorTag, Color.Yellow)
    val secondSelectedCardColor = savedStateHandle.getStateFlow(secondSelectedCardColorTag, Color.White)
    val sortedCardColors = savedStateHandle.getStateFlow(sortedCardColorsTag, Color.Green)
    val selectedCards = savedStateHandle.getStateFlow(selectedCardsTag, listOf(0, 1))

    val uiDistanceBetweenItems: StateFlow<Float> = MutableStateFlow(5f)
    val uiLineWidth: StateFlow<Float> = MutableStateFlow(15f)
    val generateRandomNumbersCount: MutableStateFlow<Int> = MutableStateFlow(calculateRandomNumberCount())

    val randomNumbers = savedStateHandle.getStateFlow(randomNumberListTag, generateRandomNumbers(generateRandomNumbersCount.value, 100, 200).toMutableList())
    val isListSorted = savedStateHandle.getStateFlow(isListSortedTag, false)
    val isSorting = savedStateHandle.getStateFlow(isSortingTag, false)

//    private val _notifyUser = MutableStateFlow("")
//    val notifyUserFlow: StateFlow<String> = _notifyUser.asStateFlow()

    private val _notifyUser = MutableLiveData<String>()
    val notifyUserFlow: LiveData<String> = _notifyUser

    val openSettingsFlow = savedStateHandle.getStateFlow(showSettingsTag, false)
//    val selectedSettingSpeed: StateFlow<SettingsSpeed> = savedStateHandle.getStateFlow(selectedSettingsSpeedTag, SettingsSpeed.FASTEST)
    var selectedSettingSpeed: MutableStateFlow<SettingsSpeed> = MutableStateFlow(SettingsSpeed.FASTEST)
    val selectedSettingsView = savedStateHandle.getStateFlow(selectedSettingsViewTag, SettingsView.LINES)
    val notSelectedSettingColor = savedStateHandle.getStateFlow(notSelectedSettingColorTag, listOf(Color.DarkGray, Color.Gray))
    val selectedSettingColor = savedStateHandle.getStateFlow(selectedSettingColorTag, listOf(Color.Magenta, Color.Red))
//    var randomNumbers = mutableStateOf(
//        savedStateHandle.get<List<RandomNumber>>(randomNumberListTag) ?: generateRandomNumbers(6)
//    )

    fun logScreenDimensions() {
        debugLog("ScreenDimensions WIDTH: ${screenDimensions.width}")
        debugLog("ScreenDimensions HEIGHT: ${screenDimensions.height}")
        debugLog("ScreenDimensions DP - WIDTH: ${screenDimensions.dpWidth}")
        debugLog("ScreenDimensions DP - HEIGHT: ${screenDimensions.dpHeight}")
    }

    fun calculateRandomNumberCount(): Int {
        debugLog("CALCULATING DIMENSION")
        logScreenDimensions()
        val width = floor(screenDimensions.dpWidth.toDouble())
        val dividend = uiDistanceBetweenItems.value + uiLineWidth.value
        val count: Int = (width / dividend).toInt()
        debugLog("CALCULATING DIMENSION $count")
        return count
    }

    fun debugLog(msg: String) {
        Log.d(TAG, msg)
    }

    fun showToastBar(msg: String) {
        MyApp.showToast(msg)
    }

    fun notifyUser(msg: String) {
        debugLog("notifyUser")
        showToastBar(msg)
        viewModelScope.launch {
            _notifyUser.value = msg
//            _notifyUser.emit(msg)
//            _notifyUser.update {
//                msg
//            }
        }

    }

    fun showSettings() {
        savedStateHandle[showSettingsTag] = true
    }

    fun hideSettings() {
        savedStateHandle[showSettingsTag] = false
    }

    fun regenerateList() {
        savedStateHandle[isListSortedTag] = false
        val tempList = generateRandomNumbers(20, 100, 200).toMutableList()
        savedStateHandle[randomNumberListTag] = tempList
        savedStateHandle[buttonNameTag] = buttonNameBubbleSortString
//        randomNumbers.value = tempList
    }

    fun changeDelayTime(selectedSpeed: SettingsSpeed) {
//        if(isSorting.value) {
//            Log.d(TAG, "LIST IS SORTING so cannot change DelayTime")
//            return;
//        }
//        savedStateHandle[delayTimeTag] = runningSpeed.speed
        selectedSettingSpeed.value = selectedSpeed
    }

    fun changeSettingView(selectedView: SettingsView) {
        if(isSorting.value) {
//            Log.d(TAG, "LIST IS SORTING so cannot change settings View")
            notifyUser(msg = "LIST IS SORTING so cannot change settings View")
            return;
        }
        savedStateHandle[selectedSettingsViewTag] = selectedView
    }

    private fun listSorted() {
        savedStateHandle[isListSortedTag] = true
        savedStateHandle[isSortingTag] = false
        savedStateHandle[buttonNameTag] = buttonNameReShuffleString
        var tempArr = randomNumbers.value
        tempArr.forEachIndexed { index, randomNumber ->
            if(!randomNumber.sorted) randomNumber.sorted = true
        }
        savedStateHandle[randomNumberListTag] = tempArr
    }

    fun swapElement(arr: MutableList<RandomNumberSorting>, leftIndex: Int, rightIndex: Int): MutableList<RandomNumberSorting> {
        val temp = arr[leftIndex]
        arr[leftIndex] = arr[rightIndex]
        arr[rightIndex] = temp
        return arr
    }



    private suspend fun bubbleSort() {
        savedStateHandle[isSortingTag] = true
        for(i in randomNumbers.value.indices) {
            var leftPtr = 0
            var rightPtr = 1
            var tempArr: MutableList<RandomNumberSorting>
            var swapHappened = false
            for(j in 0 until randomNumbers.value.size - i) {
                savedStateHandle[selectedCardsTag] = listOf(leftPtr, rightPtr)
                savedStateHandle[selectedCardsTag] = listOf(leftPtr, rightPtr)
                delay(selectedSettingSpeed.value.speed / 5)
//                delay(selectedSettingSpeed.value.speed / 5)
                tempArr = randomNumbers.value.toMutableList()
                if(leftPtr >= tempArr.size || rightPtr >= tempArr.size) {
                    tempArr[tempArr.size - 1].sorted = true
                    savedStateHandle[randomNumberListTag] = tempArr
                    break
                }
                if(tempArr[leftPtr].isGreater(tempArr[rightPtr])) {
                    swapHappened = true
                    tempArr = swapElement(tempArr, leftPtr, rightPtr)
                }
                if(j == randomNumbers.value.size - i - 1) {
                    tempArr[randomNumbers.value.size - i - 1].sorted = true
                }
                savedStateHandle[randomNumberListTag] = tempArr
                if(swapHappened) {
                    delay(timeMillis = selectedSettingSpeed.value.speed * 2)
                } else {
                    delay(timeMillis = selectedSettingSpeed.value.speed / 2)
                }
                leftPtr++
                rightPtr++
                swapHappened = false
            }
        }
        listSorted()
    }

    fun sortList() {
        if(sortingCoroutine?.isActive == true) {
            return
        }
        sortingCoroutine = viewModelScope.launch {
            bubbleSort()
        }
    }

    fun stopSorting() {
        if(sortingCoroutine?.isActive == true) {
            sortingCoroutine?.cancel("MARZI MERI")
        }
    }

    private fun generateRandomNumbers(count: Int, fromRange: Int, toRange: Int): List<RandomNumberSorting> {
        val list = generateSequence {
            Random.nextInt(fromRange, toRange)
        }
            .distinct()
            .take(count)
            .toSet()
            .toList()

        val randomNumbers: MutableList<RandomNumberSorting> = mutableListOf()
        for(items in list) {
            val randomNumber = RandomNumberSorting(
                num = items,
                color = Color.Magenta,
                sorted = false
            )
            randomNumbers.add(randomNumber)
        }
        return randomNumbers
    }

}
