package com.pioneersacademy.alkaff.firstapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.commit
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityFragmentHostBinding
import java.lang.Exception

class FragmentHostActivity : AppCompatActivity(), MyListFragment.ItemListener {

    private var myFirstFragment: MyFirstFragment? = null

    private lateinit var binding:ActivityFragmentHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MyFirstFragment", "HostActivity:onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(myFirstFragment == null)
            myFirstFragment = MyFirstFragment()

        supportFragmentManager.addOnBackStackChangedListener {
            setlayout()
        }


        //
        // Add the fragment to frame layout1

        // TODO: Step 6: add the fragment object to the fragment container view
        if (savedInstanceState == null) {

            supportFragmentManager.commit {
                add(R.id.fragment_container_view1, MyListFragment())
                //add(R.id.fragment_container_view2, myFirstFragment!!)
            }

        }
    }

    private fun setlayout() {
        if(myFirstFragment?.isAdded != true)
        {
            binding.fragmentContainerView2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1.0f)
        }else {
            binding.fragmentContainerView2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1.0f)

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

    override fun itemSelected(text: String) {


        if(myFirstFragment?.isAdded != true)
        {
            try{
                supportFragmentManager.commit {
                    add(R.id.fragment_container_view2, myFirstFragment!!)
                    addToBackStack(null)

                }
                // force the fragment manager to reflect the changes directly
                supportFragmentManager.executePendingTransactions()

                //setlayout()



            }catch (e:Exception)
            {
                Log.e("FragmentHostActivity",e.stackTraceToString())
            }


        }

        myFirstFragment?.changeText(text)


    }


}