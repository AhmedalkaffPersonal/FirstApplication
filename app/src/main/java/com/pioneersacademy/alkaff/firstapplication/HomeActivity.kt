package com.pioneersacademy.alkaff.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun login(view: View) {

        Toast.makeText(applicationContext,"Login",Toast.LENGTH_LONG).show()
    }
    fun newAccount(view: View) {
        Toast.makeText(applicationContext,"new Account",Toast.LENGTH_SHORT).show()
    }
}