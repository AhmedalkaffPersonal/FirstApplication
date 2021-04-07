package com.pioneersacademy.alkaff.firstapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityIntentBinding

class IntentActivity : AppCompatActivity() {

    private lateinit var binding:ActivityIntentBinding
    private val IMAGE_CAPTURE_REQUEST_CODE: Int = 100
    private lateinit var sharepref:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            // TODO: call another application (default dialer) and view the number
            // wihtout view binding
//            val phoneNumber = findViewById<EditText>(R.id.editTextPhoneNumber).text.toString()
//            Toast.makeText(this,phoneNumber,Toast.LENGTH_LONG).show()

            // with view binding with include
            val number = binding.contentIntent.editTextPhoneNumber.text.toString()
            // You can define your own action (string), make sure to use the same string at the intent filter
            val intent = Intent("com.pioneersacademy.alkaff.firstapplication.myAction").apply {
                putExtra("data",number)
            }
            startActivity(intent)
            // or
            //intent.setData(Uri.parse("tel:${number}"))


        }

        binding.contentIntent.apply {
            imageButtonCall.setOnClickListener {
                val number = editTextPhoneNumber.text.toString()
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setData(Uri.parse("tel:${number}"))
                }
                startActivity(intent)
            }

            imageButtonVisit.setOnClickListener {
                // TODO: check if the url text start with http or https and add it if not exist
                startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("https://${editTextTextUrl.text.toString()}")))
            }

            imageViewLogo.setOnClickListener{
                startActivityForResult(Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE),IMAGE_CAPTURE_REQUEST_CODE)
            }
        }
    }
    // package (intent) / request code

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == IMAGE_CAPTURE_REQUEST_CODE)
        {
            when(resultCode)
            {
//                RESULT_CANCELED -> {
//
//                }
                RESULT_OK -> {
                    val bitmap:Bitmap = data?.extras?.get("data") as Bitmap
                    binding.contentIntent.imageViewLogo.setImageBitmap(bitmap)
                }
                else -> {
                    binding.contentIntent.imageViewLogo.setImageResource(R.drawable.logo)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}