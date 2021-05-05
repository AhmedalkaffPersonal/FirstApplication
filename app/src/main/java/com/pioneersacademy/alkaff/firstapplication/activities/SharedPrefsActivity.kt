package com.pioneersacademy.alkaff.firstapplication.activities

import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivitySharedPrefsBinding
import com.pioneersacademy.alkaff.firstapplication.helpers.MySqlHelper
import kotlin.random.Random


// For more details about sharedPreferences read at
// https://developer.android.com/training/data-storage/shared-preferences#kotlin

// For more details about SQLite database read at
// https://developer.android.com/training/data-storage/sqlite


class SharedPrefsActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySharedPrefsBinding
    private var highestScore:Int = 0
    private lateinit var sharedPrefs:SharedPreferences
    private lateinit var myDbHelper: MySqlHelper
    private lateinit var myDb:SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedPrefsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPrefs = getSharedPreferences("Highest",MODE_PRIVATE)
        myDbHelper = MySqlHelper(applicationContext)
        myDb = myDbHelper.writableDatabase

        binding.apply {
            // Get the highest score from the shared Pres
//            highestScore = sharedPrefs.getInt("Highest",0)
//
            // Get the highest score from the database
            highestScore = myDbHelper.getHighestScore()
            textViewHighestScore.setText(highestScore.toString())
            textViewScore.setText("0")
            buttonPlay.setOnClickListener(){
                val rand:Int = Random.nextInt(0,1000)
                if(rand > highestScore) {
                    highestScore = rand
                    storetoSahredprefs(highestScore)
                    saveToDatabase(highestScore)
                }
                textViewScore.setText(rand.toString())
                textViewHighestScore.setText(highestScore.toString())
            }
        }
    }

    private fun saveToDatabase(highestScore: Int) {
//        val values = ContentValues().apply {
//            put(MySqlHelper.VALUE_COLUMN,highestScore)
//        }
//        myDb.insert(MySqlHelper.TABLE_NAME_HIGHEST,null,values)

        myDbHelper.addHighestScore(highestScore)
//        val sql:String = "INSERT INTO HIGHEST (VALUE) VALUES (${highestScore});"
//        Log.d("SharedPrefsActivity",sql)
//        myDb.execSQL(sql)

    }

    private fun storetoSahredprefs(highestScore: Int) {
        //getSharedPreferences("highestScore", MODE_PRIVATE)

        sharedPrefs.edit().apply(){
            putInt("Highest",highestScore)
            apply() // commit()
        }

    }
}