package com.pioneersacademy.alkaff.firstapplication

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.SimpleAdapter
import android.widget.SimpleCursorAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityListBinding
import com.pioneersacademy.alkaff.firstapplication.sqlite.MySqlHelper

class ListActivity : AppCompatActivity() {
    companion object{
        val KEY_NAME:String = "name"
        val KEY_AGE:String = "age"
        val KEY_BUTTON:String = "button"
    }

    private lateinit var  simpleAdapter:SimpleAdapter
    private lateinit var cursorAdapter: SimpleCursorAdapter
    private lateinit var myHelper:MySqlHelper
    private lateinit var binding:ActivityListBinding
    var data:ArrayList<String> = ArrayList()
    val datamap:MutableList<Map<String,Any?>> = mutableListOf()

    val from:Array<String> = arrayOf(KEY_NAME, KEY_AGE, KEY_BUTTON)
    val fromCursor:Array<String> = arrayOf(MySqlHelper.NAME_COLUMN,MySqlHelper.AGE_COLUMN,MySqlHelper.ID_COLUMN)
    val to:IntArray = intArrayOf(R.id.textView_item_name,R.id.textView_item_age,R.id.button_item_add)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))


        myHelper = MySqlHelper(applicationContext)
        //
        //val names = arrayOf("Ahmed","Ali","Mohammed","Fadi","Ahlam","Amal")

//        val arrayAdapter:ArrayAdapter<String> =
//            ArrayAdapter(this,
//                android.R.layout.simple_list_item_1,
//                data)

//        val arrayAdapter:ArrayAdapter<String> =
//        ArrayAdapter(this,
//        R.layout.list_item_custom,
//            R.id.textView_item,
//        data)



        //simpleAdapter = SimpleAdapter(this,datamap,R.layout.list_item_custom,from,to)

        val cursor = myHelper.getCompany()
        cursorAdapter = SimpleCursorAdapter(applicationContext,R.layout.list_item_custom,cursor,fromCursor,to,SimpleAdapter.NO_SELECTION)

        binding.content.listview.adapter = cursorAdapter

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener { view ->


                //data.add(editTextTextPersonName2.text.toString().trim())
                //SaveDataIntoMemeory()
                SaveDataToDatabase()
                clearText()
            }


        }


    private fun SaveDataToDatabase() {
        binding.content.apply {
           val result =  myHelper.addCompany(editTextTextPersonName2.text.toString(),
            editTextNumber.text.toString().toInt(),
            "Address",
            1000.0)

            Log.d("ListActivity", "Add company result ${result}" )
        }

    }

    private fun SaveDataIntoMemeory() {
        val map = mutableMapOf<String,Any?>()
        binding.content.apply {
            map.put(KEY_NAME, editTextTextPersonName2.text.toString())
            map.put(KEY_AGE, editTextNumber.text.toString())
            map.put(KEY_BUTTON, editTextTextPersonName2.text.toString())
            datamap.add(map)
        }
        simpleAdapter.notifyDataSetChanged()
        //arrayAdapter.notifyDataSetChanged()


    }

    private fun clearText() {
        binding.content.apply {
            editTextTextPersonName2.setText("")
            editTextNumber.setText("")
        }
    }

}
