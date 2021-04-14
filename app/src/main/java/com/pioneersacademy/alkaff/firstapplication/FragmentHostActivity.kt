package com.pioneersacademy.alkaff.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit

class FragmentHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyFirstFragment", "HostActivity:onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_host)


        //
        // Add the fragment to framelayout1

        // TODO: Step 6: add the fragement object to the fragment container view
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container_view1, MyListFragement())
                add(R.id.fragment_container_view2, MyFirstFragment())
            }

        }
    }


    override fun onStart() {
        Log.d("MyFirstFragment", "HostActivity:onStart")
        super.onStart()

    }

    override fun onDestroy() {
        Log.d("MyFirstFragment", "HostActivity:onDestroy")
        super.onDestroy()

    }
}