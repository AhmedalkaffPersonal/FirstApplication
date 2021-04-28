package com.pioneersacademy.alkaff.firstapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log


class SmsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { Log.d("BroadcastReceiver", it) }
        if(intent.action.equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            val intentExtras = intent.extras
            if (intentExtras != null) {

                val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                for (message in smsMessages) {
                    if(message.displayMessageBody.startsWith("#"))
                        Log.d("BroadcastReceiver", "Sender:${message.displayOriginatingAddress}, message:${message.displayMessageBody}")
                }
            }
        }
    }

}