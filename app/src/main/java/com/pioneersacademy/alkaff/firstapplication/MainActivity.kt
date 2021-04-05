package com.pioneersacademy.alkaff.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

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


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_item_add -> {
                showToast("Add")
                true
            }
            R.id.menu_item_search -> {
                showToast("Search")
                true
            }
            R.id.menu_item_remove -> {
                showToast("Remove")
                true
            }
            R.id.menu_item_settings -> {
                showToast("Settings")
                true
            }

            R.id.menu_item_switch -> {
                showToast("Switch")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private  fun showToast(str:String)
    {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show()
    }
}