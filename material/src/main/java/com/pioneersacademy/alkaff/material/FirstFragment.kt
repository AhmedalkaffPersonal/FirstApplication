package com.pioneersacademy.alkaff.material

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            this@FirstFragment.context?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("material dialog")
                    .setSingleChoiceItems(R.array.genders, -1, DialogInterface.OnClickListener {
                            dialogInterface: DialogInterface, i: Int ->
                        Toast.makeText(this.context,resources.getStringArray(R.array.genders)[i].toString(),Toast.LENGTH_LONG).show()

                    })
                    .show()
            }
        }
    }
}