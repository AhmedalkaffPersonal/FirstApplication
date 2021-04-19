package com.pioneersacademy.alkaff.firstapplication

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityPermissionsBinding
import com.pioneersacademy.alkaff.firstapplication.databinding.DialogCustomImageSelectionBinding

// TODO: read more about permissions at https://developer.android.com/training/permissions/requesting
class PermissionsActivity : AppCompatActivity() , View.OnClickListener, View.OnLongClickListener {
    private lateinit var mBinding: ActivityPermissionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPermissionsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.ivAddImage.setOnClickListener(this@PermissionsActivity)
        mBinding.ivAddImage.setOnLongClickListener(this@PermissionsActivity)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_add_image -> {
                customImageSelectionDialog()
                return
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        TODO("Not yet implemented")
    }


    /**
     * A function to launch the custom image selection dialog.
     */

    private fun customImageSelectionDialog() {
        val dialog = Dialog(this@PermissionsActivity)

        val binding: DialogCustomImageSelectionBinding =
            DialogCustomImageSelectionBinding.inflate(layoutInflater)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            // TODO: check if the application has the required permissions
            if(checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE))
            {
                captureImage()
            }
            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {

            launchGallery()
            dialog.dismiss()
        }

        //Start the dialog and display it on screen.
        dialog.show()
    }

    private fun checkPermission(permission:String, requestCode:Int):Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(hasPermissions(arrayOf(permission)))
            {
                return true
            }else {

                // TODO: check if the permission was requested before ?
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this,permission))
                    {
                        // The permission was requested before and denied, so we need to give more explanation for the user,
                        // about why do we need that permission
                        Toast.makeText(this,"Camera permission is required to capture images",Toast.LENGTH_LONG).show()

                    }else {
                        // The permission was not requested before
                        requestPermission(permission,requestCode)
                    }

                return false
            }
        }
        return  true
    }


    private fun requestPermission(permission:String,requestCode:Int){
        ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
    }
    /**
     * To check for many permissions
     */
    private fun hasPermissions(permissions:Array<String>): Boolean {
        for (per in permissions)
        {
            if(ActivityCompat.checkSelfPermission(this,per) != PackageManager.PERMISSION_GRANTED)
                return  false
        }

        return  true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("Permissions", "Camera permission has been denied by the user")
                }else {
                    Log.d("Permissions", "Camera permission has been granted by the user")
                    captureImage()
                }
            }
        }
    }

    /**
     * Function to take the image
     */

    private fun captureImage() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
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
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(galleryIntent, GALLERY)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {

                data?.extras?.let {
                    val thumbnail: Bitmap =
                        data.extras!!.get("data") as Bitmap // Bitmap from camera
                    mBinding.ivImage.setImageBitmap(thumbnail) // Set to the imageView.

                    // Replace the add icon with edit icon once the image is selected.
                    mBinding.ivAddImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@PermissionsActivity,
                            R.drawable.ic_baseline_edit_24
                        )
                    )
                }
            } else if (requestCode == GALLERY) {

                data?.let {
                    // Here we will get the select image URI.
                    val selectedPhotoUri = data.data

                    mBinding.ivImage.setImageURI(selectedPhotoUri) // Set the selected image from GALLERY to imageView.

                    // Replace the add icon with edit icon once the image is selected.
                    mBinding.ivAddImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@PermissionsActivity,
                            R.drawable.ic_baseline_edit_24
                        )
                    )
                }
            }
            // END
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
        }
    }


    companion object {
        private const val CAMERA = 1
        private const val GALLERY = 2

        private const val CAMERA_PERMISSION_REQUEST_CODE = 11

    }
}

