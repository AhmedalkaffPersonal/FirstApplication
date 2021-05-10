package com.pioneersacademy.alkaff.webservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.buttonGlide ->
                startActivity(Intent(this@MainActivity,GlideActivity::class.java))

            R.id.buttonMap ->
                startActivity(Intent(this@MainActivity,MapsActivity::class.java))

            R.id.buttonWeather ->
                startActivity(Intent(this@MainActivity,WeatherActivity::class.java))

            R.id.buttonCurrentPlace ->
                startActivity(Intent(this@MainActivity,MapsActivityCurrentPlace::class.java))


        }
    }
}