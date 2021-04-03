package com.pioneersacademy.alkaff.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

// read more about intent at https://developer.android.com/guide/components/intents-filters
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        val name = intent.getStringExtra(Keys.Name)
        // Make sure that the key value pair is sent with the intent
        if(name != null)
            Log.d("MainActivity","${name}")

        // You can skip the null check because we have a default value
        Log.d("MainActivity","${intent.getIntExtra(Keys.Password,0)}")


    }
}