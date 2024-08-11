package com.udit.algovisualizer.ui

import android.app.Application
import android.content.Context
import android.widget.Toast

class MyApp: Application() {


    companion object {

        private var context: Context? = null

        fun showToast(msg: String) {
            if(context != null) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate() {
        context = this
        super.onCreate()
    }
}