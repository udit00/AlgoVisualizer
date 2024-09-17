package com.udit.algovisualizer.ui

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import com.udit.algovisualizer.broadcast_receivers.AirplaneModeBroadcastReceiver
import com.udit.algovisualizer.ui.sorting.commonSortingData.RandomNumberSorting

class MyApp: Application() {

//    val airplaneModeBroadcastReceiver = AirplaneModeBroadcastReceiver()

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

        fun debugLog(tag: String, msg: String) {
            Log.d(tag, msg)
        }

        fun logRandomNumber_Sorting(tag: String, randomNumbers: MutableList<RandomNumberSorting>) {
            val tempArr: MutableList<Int> = mutableListOf()
            randomNumbers.forEachIndexed { index, randomNumberSorting ->
                tempArr.add(randomNumberSorting.num)
            }
            debugLog(tag, tempArr.toString())
        }

    }


    override fun onCreate() {
        context = this
        displayMetrics = (context as MyApp).resources.displayMetrics
        super.onCreate()
//        registerReceiver(airplaneModeBroadcastReceiver, IntentFilter())
    }

}

class ScreenDimensions {
    var height: Float = 0f
    var width: Float = 0f
    var dpHeight: Float = 0f
    var dpWidth: Float = 0f

}