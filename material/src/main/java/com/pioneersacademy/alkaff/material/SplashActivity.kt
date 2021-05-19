package com.pioneersacademy.alkaff.material

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.pioneersacademy.alkaff.material.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }



        // load the animation from the resources
        val animation = AnimationUtils.loadAnimation(this@SplashActivity,R.anim.splash_anim)
        // set the animation listener
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        startActivity(Intent(this@SplashActivity,AnimationActivity::class.java))
                        finish()
                    },1000)

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        // assign the animation to the view
        mBinding.apply {
            textView.animation = animation
        }

    }
}