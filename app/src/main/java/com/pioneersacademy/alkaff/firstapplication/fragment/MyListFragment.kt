package com.pioneersacademy.alkaff.firstapplication.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.ListFragment
import com.pioneersacademy.alkaff.firstapplication.R
import java.lang.ClassCastException

class MyListFragment : ListFragment() {

    interface ItemListener {
        fun itemSelected(text:String)
    }

    private var listener: ItemListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as ItemListener
        }catch (e:ClassCastException)
        {
            throw ClassCastException("${context?.toString()} must implement MyListFragment.ItemListener")
        }



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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = resources.getStringArray(R.array.list_items)
        val adapter:ArrayAdapter<String> = ArrayAdapter(view.context,
            android.R.layout.simple_list_item_1,data)
        listAdapter = adapter


        listView.setOnItemClickListener { parent, view, position, id ->
            // you can get the text from the view and then user textview.text
            // note: if the list view is complex view then use view.findViewById()
            // to get the desired view
            val textview = view as TextView
            // or you can get the text from the data array using the position
            val text = data[position]

            // We need to call a function in the Host activity

            // you can not use this ListFragment with any other activity,
            // since the next line will create an exception
//            val hostActivity = activity as FragmentHostActivity
//            hostActivity.ItemSelected(text)

            listener?.itemSelected(text)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}