package com.pioneersacademy.alkaff.material

import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.pioneersacademy.alkaff.material.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {
    private lateinit var mBinding :ActivityAnimationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    fun onClick(view: View) {
        when(view.id)
        {
            R.id.button1 ->
            {
                YoYo.with(Techniques.FadeIn)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(2)
                    .playOn(mBinding.imageView)

            }
            R.id.button2 ->
            {
                YoYo.with(Techniques.FadeOut)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(2)
                    .playOn(mBinding.imageView)
            }
            R.id.button3 ->
            {
                YoYo.with(Techniques.DropOut)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(2)
                    .playOn(mBinding.imageView)
            }
            R.id.button4 ->
            {
                val animation = AnimationUtils.loadAnimation(applicationContext,
                R.anim.rotoate_clockwise)

                // Note: start the animation if you are working after loading the view
                mBinding.imageView.startAnimation(animation)

            }
            R.id.button5 ->
            {
                YoYo.with(Techniques.RotateIn)
                    .duration(1000)
                    .playOn(mBinding.textView2)


            }
            R.id.button6 ->
            {

            }
            R.id.button7 ->
            {

            }
            R.id.button8 ->
            {

            }
            R.id.button9 ->
            {

            }
        }
    }
}