package com.udit.algovisualizer.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log

class AirplaneModeBroadcastReceiver: BroadcastReceiver() {

//    val TAG: String = javaClass.name
    val TAG: String = "AirplaneModeBroadcastReceiver"
    fun debugLog(msg: String) {
        Log.d(TAG, msg)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
//        if(intent != null) {
            if(intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                debugLog("Airplane Mode Changed")
                val isAirplaneModeEnabled = Settings.Global.getInt(
                    context?.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON
                ) != 0
                debugLog("Is Airplane Mode On? $isAirplaneModeEnabled")
//            } else if(intent?.action == Intent.)
        }
    }
}