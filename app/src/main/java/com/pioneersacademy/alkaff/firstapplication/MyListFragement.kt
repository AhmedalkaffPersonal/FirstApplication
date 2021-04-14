package com.pioneersacademy.alkaff.firstapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment

class MyListFragement : ListFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val data = resources.getStringArray(R.array.list_items)
        val adapter:ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_list_item_1,data)
        listAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // val data = arrayOf("Item 1","item 2") // bad hard coded strings

//        val data = resources.getStringArray(R.array.list_items)
//        val adapter:ArrayAdapter<String> = ArrayAdapter(inflater.context,android.R.layout.simple_list_item_1,data)
//        listAdapter = adapter
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}