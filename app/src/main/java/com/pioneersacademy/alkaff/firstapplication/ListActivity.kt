package com.pioneersacademy.alkaff.firstapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityListBinding
import com.pioneersacademy.alkaff.firstapplication.sqlite.MySqlHelper

class ListActivity : AppCompatActivity() {
    companion object {
        val KEY_NAME: String = "name"
        val KEY_AGE: String = "age"
        val KEY_BUTTON: String = "button"
    }

    private lateinit var simpleAdapter: SimpleAdapter
    private lateinit var cursorAdapter: SimpleCursorAdapter
    private lateinit var myCustomCursorAdapter:MyCustomCursorAdapter
    private lateinit var myHelper: MySqlHelper
    private lateinit var binding: ActivityListBinding
    var data: ArrayList<String> = ArrayList()
    val datamap: MutableList<Map<String, Any?>> = mutableListOf()

    val from: Array<String> = arrayOf(KEY_NAME, KEY_AGE, KEY_BUTTON)

    val fromCursor: Array<String> =
        arrayOf(MySqlHelper.NAME_COLUMN, MySqlHelper.AGE_COLUMN,MySqlHelper.ID_COLUMN)
    val to: IntArray =
        intArrayOf(R.id.textView_item_name, R.id.textView_item_age,R.id.button_item_add)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))


        myHelper = MySqlHelper(applicationContext)

        customCusorAdapter();


        binding.content.listview.setOnItemClickListener{ p, v, pos , id ->
            Toast.makeText(this,"position:${pos}, Id:${id}",Toast.LENGTH_SHORT).show()
        }

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener { view ->
            
            //data.add(editTextTextPersonName2.text.toString().trim())
            //SaveDataIntoMemeory()
            SaveDataToDatabase()
            clearText()
        }
    }
    fun customCusorAdapter()
    {
         myCustomCursorAdapter = MyCustomCursorAdapter(applicationContext,myHelper.getCompany())
        binding.content.listview.adapter = myCustomCursorAdapter
    }

    fun DynamicSimpleCursorAdapter()
    {
        val cursor = myHelper.getCompany()

        cursorAdapter = SimpleCursorAdapter(
            applicationContext,
            R.layout.list_item_custom,
            cursor,
            fromCursor,
            to,
            SimpleAdapter.NO_SELECTION
        )

        binding.content.listview.adapter = cursorAdapter
    }

    fun buttonClicked(view:View)
    {
        Toast.makeText(this,"Button clicked",Toast.LENGTH_SHORT).show()
    }

    fun staticDataSource()
    {
        val names = arrayOf("Ahmed","Ali","Mohammed","Fadi","Ahlam","Amal")

        val arrayAdapter:ArrayAdapter<String> =
            ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                names)

        // you can not add to this data source

        binding.content.listview.adapter = arrayAdapter ;
    }

    fun simpleStringDynamicDataSource()
    {

        val arrayAdapter:ArrayAdapter<String> =
            ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                data)

        binding.content.listview.adapter = arrayAdapter ;


        // you can add to this data source using
        // data.add("new item")
        // then you need to call arrayAdapter.notifyDataSetChanged()
        // to make sure that the list view is reflecting the changes on the data source


    }

    fun ComplexDynamicDataSource()
    {
        // in this case we need to use MutableList<Map<String, Any?>> and SimpleAdapter
        // DataSource: List<Map<String,Any?>
        // From: String Array for the Keys in the data source map
        // To : Integer array of the Id's in the listview layout where the data will be filled
        // Note: the From need to be ordered with the array To one by one
        simpleAdapter = SimpleAdapter(this,datamap,R.layout.list_item_custom,from,to)

        // To Add you need to create a Map and add all values to it, see method SaveDataIntoMemeory()


    }

    private fun SaveDataToDatabase() {
        binding.content.apply {
            val result = myHelper.addCompany(
                editTextTextPersonName2.text.toString(),
                editTextNumber.text.toString().toInt(),
                "Address",
                1000.0
            )

            // get the data again from the database
            myCustomCursorAdapter.changeCursor(myHelper.getCompany())
            // notify the adpater
            myCustomCursorAdapter.notifyDataSetChanged()

            Log.d("ListActivity", "Add company result ${result}")
        }

    }

    private fun SaveDataIntoMemeory() {
        val map = mutableMapOf<String, Any?>()
        binding.content.apply {
            map.put(KEY_NAME, editTextTextPersonName2.text.toString())
            map.put(KEY_AGE, editTextNumber.text.toString())
            map.put(KEY_BUTTON, editTextTextPersonName2.text.toString())
            datamap.add(map)
        }
        // Adapter need to get notified if any data changed on the data source
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
