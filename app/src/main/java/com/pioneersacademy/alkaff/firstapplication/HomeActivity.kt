package com.pioneersacademy.alkaff.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val editTextName = findViewById<View>(R.id.editTextTextPersonName) as EditText
        editTextName.setText("")
        Log.v("HomeActivity","Verbose")
        Log.i("HomeActivity","Info")
        Log.d("HomeActivity","Debug")
        Log.w("HomeActivity","Warning")
        Log.e("HomeActivity","Error")
        Log.wtf("HomeActivity","Assert")
        Log.d("HomeActivity","onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeActivity","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeActivity","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("HomeActivity","onRestart")
    }

    private var Backpressed:Boolean = false
    override fun onBackPressed() {

        // TODO: find a way to make some kind of timer
        Log.d("HomeActivity","onBackPressed")
        if(!Backpressed){
            Toast.makeText(applicationContext, "press back again to exit", Toast.LENGTH_LONG).show()

            Backpressed = true}
        else {
        super.onBackPressed()
        }


    }

    fun login(view: View) {
        val editTextName = findViewById<View>(R.id.editTextTextPersonName) as EditText
        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        //val name = editTextName.text

        val name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        // or val editText1 = findViewById<EditText>(R.id.editTextTextPersonName)

        // TODO:Check name and password
        if(name.equals(password)) {
            Toast.makeText(applicationContext, "${name}:${password}", Toast.LENGTH_LONG).show()
            editTextName.setText("")
            editTextPassword.setText("")
        }
        else {
            Toast.makeText(applicationContext, "Error log in", Toast.LENGTH_LONG).show()
        }
    }
    fun newAccount(view: View) {
        Toast.makeText(applicationContext,"new Account",Toast.LENGTH_SHORT).show()
    }



}