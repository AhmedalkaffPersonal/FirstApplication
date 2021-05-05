package com.pioneersacademy.alkaff.firstapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.pioneersacademy.alkaff.firstapplication.helpers.Keys
import com.pioneersacademy.alkaff.firstapplication.R

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // Initialize the UI elements
        UIInit()

        val text = intent.getStringExtra("data")
        if(text != null)
            editTextName.setText(text)

        val editTextName = findViewById<View>(R.id.editTextTextPersonName) as EditText
        //editTextName.setText("")
        Log.v("LoginActivity","Verbose")
        Log.i("LoginActivity","Info")
        Log.d("LoginActivity","Debug")
        Log.w("LoginActivity","Warning")
        Log.e("LoginActivity","Error")
        Log.wtf("LoginActivity","Assert")
        Log.d("LoginActivity","onCreate")

    }

    /**
     * Initialize the UI elements
     */
    private fun UIInit() {
        editTextName = findViewById<EditText>(R.id.editTextTextPersonName)
        editTextPassword  = findViewById<EditText>(R.id.editTextTextPassword)
    }

    override fun onStart() {
        super.onStart()
        Log.d("LoginActivity","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LoginActivity","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LoginActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LoginActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LoginActivity","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LoginActivity","onRestart")
    }

    private var Backpressed:Boolean = false
    override fun onBackPressed() {

        // TODO: find a way to make some kind of timer
        Log.d("LoginActivity","onBackPressed")
        if(!Backpressed){
            Toast.makeText(applicationContext, "press back again to exit", Toast.LENGTH_LONG).show()

            Backpressed = true}
        else {
        super.onBackPressed()
        }


    }

    fun login(view: View) {
//        val editTextName = findViewById<View>(R.id.editTextTextPersonName) as EditText
//        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        //val name = editTextName.text

        val name = editTextName.text.toString()
        val password = editTextPassword.text.toString()
        // or val editText1 = findViewById<EditText>(R.id.editTextTextPersonName)

        // TODO:Check name and password
        if(checkLogin(name,password)) {
            // TODO: Go to another activity (MainActivity)
//                val intent = Intent(this,MainActivity::class.java).apply {
//                    putExtra(Keys.Name,name)
//                    putExtra(Keys.Password,password.toInt())
//                }
//                intent.putExtra(Keys.Name,name)
//                intent.putExtra(Keys.Password,password.toInt())
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra(Keys.Name,name)
                    putExtra(Keys.Password,password.toInt())
                })
            clearText()

        }
        else {
            Toast.makeText(applicationContext, "Error log in", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearText() {
        editTextName.setText("")
        editTextPassword.setText("")
    }

    private fun checkLogin(name: String, password: String): Boolean {
        return name.length > 3 && password.length > 3
    }

    fun newAccount(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }



}