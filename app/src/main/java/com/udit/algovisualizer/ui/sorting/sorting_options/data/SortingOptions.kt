package com.udit.algovisualizer.ui.sorting.sorting_options.data

import androidx.compose.ui.graphics.Color
import com.udit.algovisualizer.ui.main_activity.data.Screen

data class SortingOptions(
    val name: String,
    val screen: Screen,
    val color: Color?
) {
    val id: Int get() = hashCode()
}
