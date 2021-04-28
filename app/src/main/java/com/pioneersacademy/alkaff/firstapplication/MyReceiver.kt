package com.pioneersacademy.alkaff.firstapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val action = intent!!.action
        when(action){
            "com.pioneersacademy.alkaff.MyAction" -> {
                val data = intent.getStringExtra(MESSAGE_TAG)
                Log.d("BroadcastReceiver","onReceive:${data}")
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("BroadcastReceiver","onReceive:${Intent.ACTION_BOOT_COMPLETED}")
            }
        }

    }

    companion object {
        val MESSAGE_TAG = "message"
    }
}