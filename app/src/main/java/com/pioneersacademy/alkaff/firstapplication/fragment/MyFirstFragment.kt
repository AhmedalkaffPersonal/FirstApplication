package com.pioneersacademy.alkaff.firstapplication.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pioneersacademy.alkaff.firstapplication.databinding.MyFirstFragementBinding

// TODO: Step 1: create your own fragement class and inherit from Fragment super class
class MyFirstFragment : Fragment() {

    // TODO: Step 2: Add fragment life cycle call backs

    private lateinit var binder:MyFirstFragementBinding
    override fun onAttach(context: Context) {
        Log.d("MyFirstFragment","onAttach")
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyFirstFragment","onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyFirstFragment","onCreateView")

        // TODO: Step 4: inflate the fragment layout using the provided inflater object
        // make sure to use inflater at onCreateView function not at onViewCreated

        binder = MyFirstFragementBinding.inflate(inflater)

        Log.d("MyFirstFragment","binder is initialized")
        return binder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("MyFirstFragment","onViewCreated")

        super.onViewCreated(view, savedInstanceState)

    }

    fun changeText(text:String?){

        binder?.apply {
            editTextTextPersonName3.setText(text)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("MyFirstFragment","onActivityCreated")
        super.onActivityCreated(savedInstanceState)


    }

    override fun onStart() {
        Log.d("MyFirstFragment","onStart")
        super.onStart()


    }

    override fun onResume() {
        Log.d("MyFirstFragment","onResume")
        super.onResume()


    }

    override fun onPause() {
        Log.d("MyFirstFragment","onPause")
        super.onPause()


    }

    override fun onStop() {
        Log.d("MyFirstFragment","onStop")
        super.onStop()


    }

    override fun onDestroy() {
        Log.d("MyFirstFragment","onDestroy")
        super.onDestroy()


    }

    override fun onDestroyView() {
        Log.d("MyFirstFragment","onDestroyView")
        super.onDestroyView()


    }

    override fun onDetach() {
        Log.d("MyFirstFragment","onDetach")
        super.onDetach()


    }
}