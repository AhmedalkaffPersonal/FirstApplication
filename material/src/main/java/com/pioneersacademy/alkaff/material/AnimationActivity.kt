package com.pioneersacademy.alkaff.material

import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
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

    /*
    You can read more about view Animation library https://github.com/daimajia/AndroidViewAnimations

    The supported animations are:

    Attension
        Flash, Pulse, RubberBand, Shake, Swing, Wobble, Bounce, Tada, StandUp, Wave

    Special
        Hinge, RollIn, RollOut,Landing,TakingOff,DropOut

    Bounce
        BounceIn, BounceInDown, BounceInLeft, BounceInRight, BounceInUp

    Fade
        FadeIn, FadeInUp, FadeInDown, FadeInLeft, FadeInRight
        FadeOut, FadeOutDown, FadeOutLeft, FadeOutRight, FadeOutUp

    Flip
        FlipInX, FlipOutX, FlipOutY

    Rotate
        RotateIn, RotateInDownLeft, RotateInDownRight, RotateInUpLeft, RotateInUpRight
        RotateOut, RotateOutDownLeft, RotateOutDownRight, RotateOutUpLeft, RotateOutUpRight

    Slide
        SlideInLeft, SlideInRight, SlideInUp, SlideInDown
        SlideOutLeft, SlideOutRight, SlideOutUp, SlideOutDown

    Zoom
        ZoomIn, ZoomInDown, ZoomInLeft, ZoomInRight, ZoomInUp
        ZoomOut, ZoomOutDown, ZoomOutLeft, ZoomOutRight, ZoomOutUp
     */
    fun onClick(view: View) {
        when(view.id)
        {
            R.id.button1 ->
            {
                YoYo.with(Techniques.Flash)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(5)
                    .playOn(mBinding.imageView)

            }
            R.id.button2 ->
            {
                YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(2)
                    .playOn(mBinding.imageView)
            }
            R.id.button3 ->
            {
                YoYo.with(Techniques.FadeInUp)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(2)
                    .playOn(mBinding.imageView)
            }
            R.id.button4 ->
            {
                YoYo.with(Techniques.FadeInDown)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(2)
                    .playOn(mBinding.imageView)

            }
            R.id.button5 ->
            {
                YoYo.with(Techniques.FadeInDown)
                    .duration(1000)
                    .interpolate(AccelerateDecelerateInterpolator())
                    .repeat(2)
                    .playOn(mBinding.imageView)
            }
            R.id.button6 ->
            {
                // TODO: text all other animations

            }

        }
    }
}