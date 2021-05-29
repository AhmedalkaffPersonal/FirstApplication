package com.pioneersacademy.alkaff.demofirebase

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.pioneersacademy.alkaff.demofirebase.databinding.ActivityMainBinding
import java.util.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var users: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var mBinding: ActivityMainBinding

    // Google Analytics
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    // Authentication
    private lateinit var auth: FirebaseAuth

    // Storage
    private lateinit var mStorage: FirebaseStorage
    private lateinit var mStorageRef: StorageReference

    // FireStore database
    private lateinit var mDb: FirebaseFirestore

    // Real time database
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference

    private  var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        auth = FirebaseAuth.getInstance()

        mDb = FirebaseFirestore.getInstance()

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase.getReference()

        mStorage = FirebaseStorage.getInstance()
        mStorageRef = mStorage.getReferenceFromUrl("gs://pioneerstraining-fc3bc.appspot.com")

        users = mutableListOf<String>()

        adapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,users)


        mDb.collection(USER_COLLECTION).whereEqualTo(EMAIL,auth.currentUser?.email).get().addOnSuccessListener { query ->
            Log.d(TAG,"Start loading data")
            for (doc in query.documents)
            {
                Log.d(TAG,"Image uri: ${doc.get(IMAGE_KEY)}")

                mStorageRef.child(doc.get(IMAGE_KEY).toString()).downloadUrl
                    .addOnSuccessListener {
                    Log.d(TAG,"Image was downloaded from : ${it}")
                    Glide.with(this@MainActivity)
                        .load(it)
                        .override(resources.getDimensionPixelSize(R.dimen._100sdp), resources.getDimensionPixelSize(R.dimen._150sdp))
                        .centerCrop()
                        .into(mBinding.imageView)
                }

            }
        }

        mBinding.apply {

            listviewUsers.adapter = adapter
            auth.currentUser?.let {
                val name = if (it.displayName.isNullOrEmpty()) it.email else it.displayName
                textView.text = "Hello ${name}"
                // TODO: save users in the database

                mDatabaseReference.child(USER).child(preparePath(it.email)).apply {
                    child(UID).setValue(it.uid)
                        .addOnCompleteListener {
                            Log.d(TAG, "${UID} was added successfully.")
                        }
                    child(EMAIL).setValue(it.email)
                    child(LOGIN_TIME).setValue(Calendar.getInstance().time)
                    it.email?.let { it1 -> Log.d(TAG, it1)
                        Log.d(TAG, it1.equals("ahmedalkaff@outlook.com",true).toString())}
                    if(it.email?.toString().equals("ahmedalkaff@outlook.com",true))
                    {
                        child(ADMIN).setValue(true)
                    }


                }





                mDatabaseReference.child(USER)
                    .addValueEventListener(object :ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d(TAG,"Data changed ${snapshot.value}")
                        users.clear()
                        snapshot.children.forEach {
                            Log.d(TAG, "Data: ${it.toString()}")
                            it.child(EMAIL)?.let { it1 ->
                                if (!it1.value.toString()
                                        .equals(auth.currentUser?.email.toString()))
                                {
                                    Log.d(TAG,"Child value:${it.child(COUNTER).value}")
                                    users.add("${it1.value.toString()} : ${it.child(COUNTER).value}")
                                }
                            }


                        }
                        adapter.notifyDataSetChanged()


                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(TAG,"Data changed cancelled!:"+ error.message)
                    }

                })


            }

            listviewUsers.setOnItemClickListener(object :AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val userId = preparePath(users[position].split(":")[0].trim())
                    Log.d(TAG,"User ID:${userId}")
                    mDatabaseReference.child(USER).child(userId).child(COUNTER).get().addOnSuccessListener {
                        try {
                            counter = if(it.value == null) 0 else it.value.toString().toInt()
                            Log.d(TAG,"Counter : ${counter}")
                            mDatabaseReference.child(USER).child(userId).child(COUNTER).setValue(++counter)
                        }catch (e:Exception)
                        {
                            Log.e(TAG,e.message.toString())
                            Log.e(TAG,"it.value: ${it.value}")
                        }

                    }

                }


            })

            // TODO: read the image path from FireStore database for the current users

            mDatabaseReference.child(USER).get()


            // TODO: download the image from the Storage using the image path stored at the FireStore
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG,"Start at user ${auth.currentUser?.email}")


    }

    fun onClick(view: View) {
        val bundel = Bundle()
        bundel.putString("Logout", "User logged out.")
        bundel.putString("Time", Calendar.getInstance().time.toString())
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundel)

        //TODO: remove user from the database
        auth.currentUser?.let {
            mDatabaseReference.child(USER).child(preparePath(it.email)).removeValue()
        }

        auth.signOut()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))

    }

    fun preparePath(path: String?): String {
        val regex = "[/.#$/[/]]".toRegex()
        return path?.let { regex.replace(it, "_") } ?: ""
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        const val USER = "Users"
        const val ADMIN = "admin"
        const val EMAIL = "email"
        const val UID = "uid"
        const val USER_COLLECTION = "users"
        const val IMAGE_KEY = "image"

        const val COUNTER = "counter"
        const val LOGIN_TIME = "Login time"

    }
}