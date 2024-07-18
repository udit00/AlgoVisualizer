package com.udit.algovisualizer.ui.sorting.bubble_sort

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udit.algovisualizer.ui.sorting.bubble_sort.data.RandomNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class BubbleSortViewModel(
    val savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        val randomNumberListTag = "randomNumberListTag"
        val selectedCardsTag = "selectedCardsTag"
        val buttonNameTag = "buttonNameTag"
        val defaultCardColorTag = "defaultCardColorTag"
        val firstSelectedCardColorTag = "firstSelectedCardColorTag"
        val secondSelectedCardColorTag = "secondSelectedCardColorTag"
        val sortedCardColorsTag = "sortedCardColorsTag"

        private val buttonNameBubbleSortString = "Bubble Sort"
        private val buttonNameReShuffleString = "Re-Shuffle"
        private val isListSortedTag = "isListSortedTag"
    }

    val delayTime = savedStateHandle.getStateFlow("delayTime", 100.toLong())
    val buttonName = savedStateHandle.getStateFlow(buttonNameTag, buttonNameBubbleSortString)
    val defaultCardColor = savedStateHandle.getStateFlow(defaultCardColorTag, Color.Red)
    val firstSelectedCardColor = savedStateHandle.getStateFlow(firstSelectedCardColorTag, Color.Yellow)
    val secondSelectedCardColor = savedStateHandle.getStateFlow(secondSelectedCardColorTag, Color.White)
    val sortedCardColors = savedStateHandle.getStateFlow(sortedCardColorsTag, Color.Green)
    val selectedCards = savedStateHandle.getStateFlow(selectedCardsTag, listOf(0, 1))
    val randomNumbers = savedStateHandle.getStateFlow(randomNumberListTag, generateRandomNumbers(6).toMutableList())
    val isListSorted = savedStateHandle.getStateFlow(isListSortedTag, false)
//    var randomNumbers = mutableStateOf(
//        savedStateHandle.get<List<RandomNumber>>(randomNumberListTag) ?: generateRandomNumbers(6)
//    )

    fun regenerateList() {
        savedStateHandle[isListSortedTag] = false
        val tempList = generateRandomNumbers(6).toMutableList()
        savedStateHandle[randomNumberListTag] = tempList
        savedStateHandle[buttonNameTag] = buttonNameBubbleSortString
//        randomNumbers.value = tempList
    }

    private fun listSorted() {
        savedStateHandle[isListSortedTag] = true
        savedStateHandle[buttonNameTag] = buttonNameReShuffleString
        var tempArr = randomNumbers.value
        tempArr.forEachIndexed { index, randomNumber ->
            if(!randomNumber.sorted) randomNumber.sorted = true
        }
        savedStateHandle[randomNumberListTag] = tempArr
    }

    fun swapElement(arr: MutableList<RandomNumber>, leftIndex: Int, rightIndex: Int): MutableList<RandomNumber> {
        val temp = arr[leftIndex]
        arr[leftIndex] = arr[rightIndex]
        arr[rightIndex] = temp
        return arr
    }



    private suspend fun bubbleSort() {
        for(i in randomNumbers.value.indices) {
            var leftPtr = 0
            var rightPtr = 1
            var tempArr: MutableList<RandomNumber>
            var swapHappened = false
            for(j in 0 until randomNumbers.value.size - i) {
                savedStateHandle[selectedCardsTag] = listOf(leftPtr, rightPtr)
                savedStateHandle[selectedCardsTag] = listOf(leftPtr, rightPtr)
                delay(delayTime.value / 5)
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
                tempArr.forEachIndexed { index, randomNumber ->
                    Log.d("Sorting Random Numbers", randomNumber.toString())
                }
                savedStateHandle[randomNumberListTag] = tempArr
                if(swapHappened) {
                    delay(timeMillis = delayTime.value * 2)
                } else {
                    delay(timeMillis = delayTime.value / 2)
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

    private fun generateRandomNumbers(count: Int): List<RandomNumber> {
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
        val randomNumbers: MutableList<RandomNumber> = mutableListOf()
        for(items in list) {
            val randomNumber = RandomNumber(
                num = items,
                color = Color.Magenta,
                sorted = false
            )
            randomNumbers.add(randomNumber)
        }
        return randomNumbers
    }

}