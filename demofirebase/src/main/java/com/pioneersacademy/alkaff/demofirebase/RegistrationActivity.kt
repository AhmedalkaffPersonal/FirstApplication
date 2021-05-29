package com.pioneersacademy.alkaff.demofirebase

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.pioneersacademy.alkaff.demofirebase.databinding.ActivityRegistrationBinding
import com.pioneersacademy.alkaff.demofirebase.databinding.DialogCustomImageSelectionBinding
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.contracts.contract

class RegistrationActivity : AppCompatActivity() {

    private lateinit var mBinder: ActivityRegistrationBinding
    // FireStore database
    private lateinit var mDb: FirebaseFirestore

    // Storage
    private lateinit var mStorage: FirebaseStorage
    private lateinit var mStorageRef: StorageReference
    private lateinit var mAuth: FirebaseAuth

    private lateinit var mGallaryActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var mCameraActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(mBinder.root)

        mDb = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        mStorage = FirebaseStorage.getInstance()
        mStorageRef = mStorage.getReferenceFromUrl("gs://pioneerstraining-fc3bc.appspot.com")

        mGallaryActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            activityResult ->
            when(activityResult.resultCode){
                Activity.RESULT_OK ->
                {
                    activityResult.data.let {
                        val dataUri = it?.data
                        mBinder.ivImagePerson.setImageURI(dataUri)
                    }
                }
                Activity.RESULT_CANCELED -> {
                        // TODO: do something (e.g: load placeholder image)
                }
                Activity.RESULT_FIRST_USER -> {

                }
            }

        }



        mCameraActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

                if (it.resultCode == Activity.RESULT_OK) {
                    val imageBitmap = it.data?.extras?.get("data") as Bitmap
                    mBinder.ivImagePerson.setImageBitmap(imageBitmap)
                }
            }


        readUsersInfoFromFireStore()
        mBinder.apply {
            butRegister.setOnClickListener {
                val email = etEmail.text.toString()
                // TODO:Check if password match the repassword
                val password = etPassword.text.toString()
                createUser(email, password)
                saveImageToStorage(email, password)
                finish()

                startActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))
            }

            ivImagePerson.setOnClickListener {
                customImageSelectionDialog()
            }

        }
    }

    private fun customImageSelectionDialog() {
        val dialog = Dialog(this@RegistrationActivity)

        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            Dexter.withContext(this@RegistrationActivity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        // when all the permissions are granted

                        report?.let {
                            // the same idea as hasPermission function that we implement before
                            if (report.areAllPermissionsGranted()) {
                                captureImage()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        // When permissions are denied before
                        //Toast.makeText(this@PermissionsActivity,"Camera permission is required to capture images",Toast.LENGTH_LONG).show()
                        showRationalDialogForPermissions()
                    }

                }).onSameThread()
                .check()
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {

            Dexter.withContext(this@RegistrationActivity)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        launchGallery()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "You have denied the storage permission to select an image.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }

                }).onSameThread()
                .check()
            dialog.dismiss()
        }

        //Start the dialog and display it on screen.
        dialog.show()
    }


    private fun createUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User was created")

                }
            }

    }


    private fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        mCameraActivityResultLauncher.launch(intent)
    }

    /**
     *  Go to settings to let the user enable the camera permission
     */
    private fun goToSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * Launch the gallery application
     */
    private fun launchGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        mGallaryActivityResultLauncher.launch(galleryIntent)

    }

    private fun saveImageToStorage(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            val df = SimpleDateFormat("ddMMyyyyHHmmss")
            val fileName = "Image_${email}_${df.format(Calendar.getInstance().time)}"
            val imageRef = mStorageRef.child("images/${fileName}")

            mBinder.ivImagePerson.isDrawingCacheEnabled = true
            mBinder.ivImagePerson.buildDrawingCache()
            val drwable = mBinder.ivImagePerson.drawable
            val bitmap = drwable.toBitmap()
            val ostr = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostr)
            val dataBytes: ByteArray? = ostr.toByteArray()
            imageRef.putBytes(dataBytes!!)
                .addOnFailureListener {
                    Log.e(TAG, "Failed to upload image:${fileName}")
                }.addOnSuccessListener {

                    it.metadata?.let { md ->
                        saveUserInfoToFireStore(email, password,md.path)
                        Log.d(TAG, "Image:${fileName} was uploaded successfully! @ path:${md.path} , Name:${md.name}")
                    }


                }
        }
        // generate the file identifier (each file must have a unique id)
        // Image{Timestamp}

    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this@RegistrationActivity)
            .setMessage("Message")
            .setPositiveButton(R.string.goToSettings) { dialogInterface: DialogInterface, i: Int ->
                goToSettings()
            }.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.show()


    }

    private fun saveUserInfoToFireStore(email: String, password: String, path:String?) {

        val user = hashMapOf<String, Any?>()
        user.put(EMAIL_KEY, email)
        user.put(PASSWORD_KEY, password)
        user.put(TIME_KEY, Calendar.getInstance().time.toString())
        user.put(IMAGE_KEY, path)

        mDb.collection(USER_COLLECTION).add(user)
            .addOnSuccessListener {
                Toast.makeText(
                    this@RegistrationActivity,
                    "User info [${email},${password}] is saved", Toast.LENGTH_LONG
                ).show()


            }
            .addOnFailureListener {
                Toast.makeText(
                    this@RegistrationActivity,
                    "failed to save user info [${email},${password}]", Toast.LENGTH_LONG
                ).show()
            }


    }

    fun readUsersInfoFromFireStore() {
        val mquery = mDb.collection(USER_COLLECTION)
            //.orderBy(EMAIL_KEY,Query.Direction.DESCENDING)
            //.limit(10)      // read only 10 documents
            .get().addOnSuccessListener { documentSnapshot ->

//                documentSnapshot.toObjects(UserInfo::class.java).forEach({
//                    Log.i(TAG,"Email:${it.email}, Password:${it.password}, Time:${it.Time}")
//                })


                var str: String = ""
                documentSnapshot.documents.forEach({
                    str = ""

                    it.data?.keys?.forEach({ it1 ->
                        str += "${it1}:${it.get(it1).toString()},"

                    })
                    Log.d(TAG, "ID:${it.id}-> ${str}")

                })

            }


    }

    data class UserInfo(
        val email: String? = null,
        val password: String? = null,
        val Time: String? = null
    )


    companion object {
        val TAG = RegistrationActivity::class.java.simpleName
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
        const val TIME_KEY = "time"
        const val IMAGE_KEY = "image"
        const val USER_COLLECTION = "users"

        private const val CAMERA = 1
        private const val GALLERY = 2


    }
}
