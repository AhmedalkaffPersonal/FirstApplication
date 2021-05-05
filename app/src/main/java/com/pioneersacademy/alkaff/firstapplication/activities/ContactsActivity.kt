package com.pioneersacademy.alkaff.firstapplication.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SimpleAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.pioneersacademy.alkaff.firstapplication.R
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityContactsBinding

class ContactsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityContactsBinding
    private var data:ArrayList<Map<String,Any>> = ArrayList()
    private lateinit var adapter:SimpleCursorAdapter
    private lateinit var simpleAdapter:SimpleAdapter

    private val from_keys = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,ContactsContract.CommonDataKinds.Phone.NUMBER)
    private val to_ids = intArrayOf(R.id.textViewName, R.id.textViewPhone)


    private val from_keys1 = arrayOf(KEY_NAME, KEY_PHONE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SimpleCursorAdapter(this, R.layout.contact_item,null,from_keys,to_ids,0)
        simpleAdapter = SimpleAdapter(this,data, R.layout.contact_item,from_keys1,to_ids)
        binding.contactsList.adapter = simpleAdapter

    }

    override fun onStart() {
        super.onStart()
        if(hasContactPermissions())
        {
           // loadContactList()
            loadContactListWithAllPhones()
        }else {
            requestContactPermissions()
        }
    }
    private fun requestContactPermissions() {

        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_CONTACTS)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    //loadContactList()
                    loadContactListWithAllPhones()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    Toast.makeText(this@ContactsActivity,"${Manifest.permission.READ_CONTACTS} permission is required to read contacts!",Toast.LENGTH_LONG).show()
                }

            })
            .onSameThread().check()


    }

    private fun loadContactList() {

        // to read contacts information use  ContactsContract.Contacts.CONTENT_URI
        //val uri = ContactsContract.Contacts.CONTENT_URI

        // to read phone numbers use ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        // content://contacts
        // make sure to add _ID in your projects, based on the URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY,ContactsContract.CommonDataKinds.Phone.NUMBER
        )
       val cursor = contentResolver.query(uri,projection,null,null,null)
        adapter.changeCursor(cursor)
        adapter.notifyDataSetChanged()
    }

    private fun loadContactListWithAllPhones() {
        // to read contacts information use  ContactsContract.Contacts.CONTENT_URI
        val uri = ContactsContract.Contacts.CONTENT_URI
        val phone_uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // content://contacts
        // make sure to add _ID in your projects, based on the URI
        val projection = arrayOf(ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,ContactsContract.Contacts.HAS_PHONE_NUMBER)

        val phone_projection = arrayOf(ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER)
        val cursor = contentResolver.query(uri,null,null,null,null)
        if(cursor != null && cursor.count > 0)
        {
            while(cursor.moveToNext())
            {
                val contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val contact_name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                val hasPhoneNumber = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if(hasPhoneNumber > 0){
                    val selection = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ? OR ${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY} = ?"
                    val selectionArgs = arrayOf(contact_id, contact_name)
                    val phones_cursor = contentResolver.query(phone_uri,phone_projection,selection,selectionArgs,null)
                    var phones = ""
                    if (phones_cursor != null) {

                        while (phones_cursor.moveToNext()) {
                            phones += phones_cursor.getString(phones_cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))+ "/"
                        }
                        phones_cursor.close()
                    }

                    val map : HashMap<String,Any> = hashMapOf()
                    map.put(KEY_ID, contact_id)
                    map.put(KEY_NAME,contact_name)
                    map.put(KEY_PHONE,phones)
                    data.add(map)
                }
            }

            cursor.close()
        }


        simpleAdapter.notifyDataSetChanged()


    }

    private fun hasContactPermissions(): Boolean {

        return  ActivityCompat.checkSelfPermission(applicationContext,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED

    }

    companion object{
        private val KEY_ID = "_id"
        private val KEY_NAME = "name"
        private val KEY_PHONE = "phone"

    }
}