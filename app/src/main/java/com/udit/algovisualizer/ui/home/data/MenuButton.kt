package com.udit.algovisualizer.ui.home.data

import android.media.Image
import androidx.annotation.DrawableRes


data class MenuButton(
    val name: ButtonType,
    @DrawableRes val image: Int
)

enum class ButtonType {
    Searching,
    Sorting
}
