package com.udit.algovisualizer.services.logger

import android.util.Log

class LoggerService private constructor() {
    companion object {
        private const val TAG: String = "LOGGER_SERVICE"
        fun log(tag: String = TAG, msg: String) {
            Log.d(tag, msg)
        }
    }
}