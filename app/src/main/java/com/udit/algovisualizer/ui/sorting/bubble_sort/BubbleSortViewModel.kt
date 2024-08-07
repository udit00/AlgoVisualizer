package com.udit.algovisualizer.ui.sorting.bubble_sort

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udit.algovisualizer.ui.sorting.commonSortingData.RandomNumberSorting
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        sealed class SPEED(val speed: Long) {
            object SLOWEST: SPEED(750L)
            object MODERATE: SPEED(550L)
            object FASTEST: SPEED(100L)
        }

        val TAG = "BubbleSortViewModel"
        val randomNumberListTag = "randomNumberListTag"
        val selectedCardsTag = "selectedCardsTag"
        val buttonNameTag = "buttonNameTag"
        val defaultCardColorTag = "defaultCardColorTag"
        val firstSelectedCardColorTag = "firstSelectedCardColorTag"
        val secondSelectedCardColorTag = "secondSelectedCardColorTag"
        val sortedCardColorsTag = "sortedCardColorsTag"
        val delayTimeTag = "delayTimeTag"
        val isSortingTag = "isSortingTag"

        private val buttonNameBubbleSortString = "Bubble Sort"
        private val buttonNameReShuffleString = "Re-Shuffle"
        private val isListSortedTag = "isListSortedTag"
    }

    val delayTime = savedStateHandle.getStateFlow(delayTimeTag, SPEED.MODERATE)
    val buttonName = savedStateHandle.getStateFlow(buttonNameTag, buttonNameBubbleSortString)
    val defaultCardColor = savedStateHandle.getStateFlow(defaultCardColorTag, Color.Red)
    val firstSelectedCardColor = savedStateHandle.getStateFlow(firstSelectedCardColorTag, Color.Yellow)
    val secondSelectedCardColor = savedStateHandle.getStateFlow(secondSelectedCardColorTag, Color.White)
    val sortedCardColors = savedStateHandle.getStateFlow(sortedCardColorsTag, Color.Green)
    val selectedCards = savedStateHandle.getStateFlow(selectedCardsTag, listOf(0, 1))
    val randomNumbers = savedStateHandle.getStateFlow(randomNumberListTag, generateRandomNumbers(10).toMutableList())
    val isListSorted = savedStateHandle.getStateFlow(isListSortedTag, false)
    val isSorting = savedStateHandle.getStateFlow(isSortingTag, false)
//    var randomNumbers = mutableStateOf(
//        savedStateHandle.get<List<RandomNumber>>(randomNumberListTag) ?: generateRandomNumbers(6)
//    )

    fun regenerateList() {
        savedStateHandle[isListSortedTag] = false
        val tempList = generateRandomNumbers(10).toMutableList()
        savedStateHandle[randomNumberListTag] = tempList
        savedStateHandle[buttonNameTag] = buttonNameBubbleSortString
//        randomNumbers.value = tempList
    }

    fun changeDelayTime(speed: SPEED) {
        if(isSorting.value) {
            Log.d(TAG, "LIST IS SORTING so cannot change DelayTime")
            return;
        }
        savedStateHandle[delayTimeTag] = speed
        Log.d(TAG, "delayTime is ${delayTime.value}")

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
        Log.d(TAG, delayTime.value.toString())
        savedStateHandle[isSortingTag] = true
        for(i in randomNumbers.value.indices) {
            var leftPtr = 0
            var rightPtr = 1
            var tempArr: MutableList<RandomNumberSorting>
            var swapHappened = false
            for(j in 0 until randomNumbers.value.size - i) {
                savedStateHandle[selectedCardsTag] = listOf(leftPtr, rightPtr)
                savedStateHandle[selectedCardsTag] = listOf(leftPtr, rightPtr)
                delay(delayTime.value.speed / 5)
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
//                tempArr.forEachIndexed { index, randomNumber ->
//                    Log.d("Sorting Random Numbers", randomNumber.toString())
//                }
                savedStateHandle[randomNumberListTag] = tempArr
                if(swapHappened) {
                    delay(timeMillis = delayTime.value.speed * 2)
                } else {
                    delay(timeMillis = delayTime.value.speed / 2)
                }
                leftPtr++
                rightPtr++
                swapHappened = false
            }
        }
        listSorted()
    }

    fun sortList() {
        viewModelScope.launch {
            bubbleSort()
        }
    }

    private fun generateRandomNumbers(count: Int): List<RandomNumberSorting> {
        val list = generateSequence {
            Random.nextInt(1, count * 10)
        }
            .distinct()
            .take(count)
            .toSet()
            .toList()

        list.forEachIndexed { index, i ->
            Log.d("RANDOM_NUMBERS",i.toString());
        }
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
