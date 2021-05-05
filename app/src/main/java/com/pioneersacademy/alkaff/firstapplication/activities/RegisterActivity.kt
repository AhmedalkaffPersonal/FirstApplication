package com.pioneersacademy.alkaff.firstapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityRegisterBinding

// Read more about view binding at https://developer.android.com/topic/libraries/view-binding
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.editTextTextFistName.setText("This is the first name")
//        binding.editTextTextLastName.setText("last")
//        binding.editTextPhone.setText("Phone")

        binding.apply {
            editTextPhone.setText("Phone")
            editTextTextFistName.setText("First")
            editTextTextLastName.setText("Last")
        }


    }
}