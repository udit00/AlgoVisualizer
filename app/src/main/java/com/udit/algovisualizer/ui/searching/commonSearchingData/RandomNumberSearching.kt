package com.udit.algovisualizer.ui.searching.commonSearchingData

import androidx.compose.ui.graphics.Color

data class RandomNumberSearching(
    val num: Int,
    val color: Color,
    var sorted: Boolean
) {
    val id: Int get() = hashCode()
}
