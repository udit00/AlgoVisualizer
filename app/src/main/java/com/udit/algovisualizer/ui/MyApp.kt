package com.udit.algovisualizer.ui

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast

class MyApp: Application() {


    companion object {
        private var context: Context? = null
        var displayMetrics: DisplayMetrics? = null

        fun showToast(msg: String) {
            if(context != null) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }

        fun getScreenDimensions(): ScreenDimensions {
            val screenDimensions = ScreenDimensions()
            if(displayMetrics != null) {
                screenDimensions.height = displayMetrics!!.widthPixels.toFloat()
                screenDimensions.width = displayMetrics!!.widthPixels.toFloat()
                screenDimensions.dpHeight = displayMetrics!!.heightPixels / displayMetrics!!.density
                screenDimensions.dpWidth = displayMetrics!!.widthPixels / displayMetrics!!.density
            }
            return screenDimensions
        }
    }


    override fun onCreate() {
        context = this
        displayMetrics = (context as MyApp).resources.displayMetrics
        super.onCreate()
    }
}

class ScreenDimensions {
    var height: Float = 0f
    var width: Float = 0f
    var dpHeight: Float = 0f
    var dpWidth: Float = 0f

}