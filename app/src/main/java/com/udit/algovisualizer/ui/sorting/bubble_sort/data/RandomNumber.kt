package com.udit.algovisualizer.ui.sorting.bubble_sort.data

import androidx.compose.ui.graphics.Color

data class RandomNumber(
    val num: Int,
    val color: Color,
    val sorted: Boolean
) {
    val id: Int get() = hashCode()

    fun isGreater(randomNumber: RandomNumber): Boolean = num > randomNumber.num
}
