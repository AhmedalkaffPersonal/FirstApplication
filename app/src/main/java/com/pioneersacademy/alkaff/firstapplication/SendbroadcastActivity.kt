package com.pioneersacademy.alkaff.firstapplication

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivitySendbroadcastBinding
import java.lang.IllegalArgumentException

class SendbroadcastActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySendbroadcastBinding
    private lateinit var myReciver:MyReceiver
    private lateinit var mySMSReciver: SmsBroadcastReceiver
    private lateinit var intentFilter:IntentFilter
    private lateinit var sMSintentFilter:IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendbroadcastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        myReciver = MyReceiver()
        mySMSReciver = SmsBroadcastReceiver()
        intentFilter = IntentFilter("com.pioneersacademy.alkaff.MyAction").apply {
            addAction(Intent.ACTION_BOOT_COMPLETED)
        }
        sMSintentFilter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION).apply {
            priority = 999
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->

            //Log.d("BroadcastReceiver","FloatingActionButton")
            // this will
            //sendBroadcast(Intent(this@SendbroadcastActivity,MyReceiver::class.java))

            val intent = Intent("com.pioneersacademy.alkaff.MyAction").apply {
                putExtra(MyReceiver.MESSAGE_TAG,binding.content.editTextTextMessage.text.toString())
                binding.content.editTextTextMessage.setText("")
            }
            sendBroadcast(intent)
        }

        binding.content.apply {
            switchRegister.setOnCheckedChangeListener { cb, checked ->

                if(myReciver == null)
                    myReciver = MyReceiver()

                try {
                    if(checked)
                    {
                        // Register the receiver
                        registerReceiver(myReciver,intentFilter)
                        Log.d("BroadcastReceiver","My broadcast receiver is   is registered")

                    }else {
                        // Un-Register the receiver
                        unregisterReceiver(myReciver)
                        Log.d("BroadcastReceiver","My broadcast receiver is   is un-registered")

                    }
                }catch (e:IllegalArgumentException)
                {
                    Log.e("SendbroadcastActivity",e.stackTraceToString())
                }


            }

            switchRegisterSms.setOnCheckedChangeListener { b, checked ->


                try {
                    if(checked)
                    {
                        // Register the receiver
                        requestSMSPermissions()
                        ///registerReceiver(mySMSReciver,sMSintentFilter)
                        Log.d("BroadcastReceiver","SMS broadcast is registered")
                    }else {
                        // Un-Register the receiver
                        unregisterReceiver(mySMSReciver)
                        Log.d("BroadcastReceiver","SMS broadcast is un-registered")
                    }
                }catch (e:IllegalArgumentException)
                {
                    Log.e("SendbroadcastActivity",e.stackTraceToString())
                }

            }
        }

    }

    private fun requestSMSPermissions(){
        Dexter.withContext(this)
            .withPermissions(Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if(mySMSReciver == null)
                        mySMSReciver = SmsBroadcastReceiver()

                    registerReceiver(mySMSReciver,sMSintentFilter)

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    Toast.makeText(this@SendbroadcastActivity,"We need sms permission",Toast.LENGTH_LONG).show()

                }

            }).onSameThread().check()


    }

    override fun onStop() {
        try {
            unregisterReceiver(myReciver)
            Log.d("BroadcastReceiver","My broadcast receiver is   is un-registered")

        }catch (e:Exception){}

        try {
            unregisterReceiver(mySMSReciver)
            Log.d("BroadcastReceiver","SMS broadcast is un-registered")
        }catch (e:java.lang.Exception) {}
        super.onStop()
    }
}