package com.udit.algovisualizer.ui.sorting.commonSortingData

import androidx.compose.ui.graphics.Color

data class RandomNumberSorting(
    val num: Int,
    val color: Color,
    var sorted: Boolean
) {
    val id: Int get() = hashCode()

    fun isGreater(randomNumber: RandomNumberSorting): Boolean = num > randomNumber.num
    fun isSmaller(randomNumber: RandomNumberSorting): Boolean = num < randomNumber.num
    fun isGreaterOrEqualTo(randomNumber: RandomNumberSorting): Boolean = num >= randomNumber.num
    fun isSmallerOrEqualTo(randomNumber: RandomNumberSorting): Boolean = num <= randomNumber.num
}
