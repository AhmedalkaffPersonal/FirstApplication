package com.pioneersacademy.alkaff.webservice

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideActivity : AppCompatActivity(), RequestListener<Drawable> {


    private val imageURl =
        "https://media.kasperskydaily.com/wp-content/uploads/sites/85/2019/12/09085647/android-device-identifiers-featured.jpg"


    private lateinit var imageTwo:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)

        val imageOne = findViewById<ImageView>(R.id.image_one)
        imageTwo = findViewById<ImageView>(R.id.image_two)
        val imageThree = findViewById<ImageView>(R.id.image_three)

        Glide.with(this)
            .load(imageURl)
            .into(imageOne)


        Glide.with(this)
            .load(imageURl)
            .fitCenter()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.img_placeholder)
            .listener(this)
            .placeholder(R.drawable.img_placeholder)
            .into(imageTwo)


        Glide.with(this)
            .load(imageURl)
            .override(300, 400)
            .centerCrop()
            .error(R.drawable.img_placeholder)
            .into(imageThree)

    }

    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        imageTwo.setImageResource(R.drawable.img_placeholder)
        return  true
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
       imageTwo.setImageDrawable(resource)
        return  true
    }

}