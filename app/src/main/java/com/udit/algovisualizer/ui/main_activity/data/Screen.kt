package com.udit.algovisualizer.ui.main_activity.data

import kotlinx.serialization.Serializable
@Serializable
sealed class Screen() {
    @Serializable object HomeScreen: Screen()
    @Serializable object SortingOptions: Screen()
    @Serializable object BinarySearchScreen: Screen()
    @Serializable object LinearSearchScreen: Screen()
    @Serializable object BubbleSortScreen: Screen()
    @Serializable object InsertionSortScreen: Screen()
    @Serializable object SelectionSortScreen: Screen()
    @Serializable object AStarSearchScreen: Screen()
}
