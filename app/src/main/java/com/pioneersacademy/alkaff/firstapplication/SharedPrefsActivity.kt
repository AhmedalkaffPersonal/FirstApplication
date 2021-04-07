package com.pioneersacademy.alkaff.firstapplication

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivitySharedPrefsBinding
import kotlin.random.Random


// For more details read at https://developer.android.com/training/data-storage/shared-preferences#kotlin

class SharedPrefsActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySharedPrefsBinding
    private var highestScore:Int = 0
    private lateinit var sharedPrefs:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPrefsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = getSharedPreferences("Highest",MODE_PRIVATE)

        binding.apply {
            highestScore = sharedPrefs.getInt("Highest",0)
            textViewHighestScore.setText(highestScore.toString())
            textViewScore.setText("0")
            buttonPlay.setOnClickListener(){
                val rand:Int = Random.nextInt(0,1000)
                if(rand > highestScore) {
                    highestScore = rand
                    storetoSahredprefs(highestScore)
                }
                textViewScore.setText(rand.toString())
                textViewHighestScore.setText(highestScore.toString())
            }
        }
    }

    private fun storetoSahredprefs(highestScore: Int) {
        //getSharedPreferences("highestScore", MODE_PRIVATE)

        sharedPrefs.edit().apply(){
            putInt("Highest",highestScore)
            apply() // commit()
        }

    }
}