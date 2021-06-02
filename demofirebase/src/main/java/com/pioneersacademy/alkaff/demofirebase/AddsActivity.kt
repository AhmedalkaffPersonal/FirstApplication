package com.pioneersacademy.alkaff.demofirebase

import android.app.NativeActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.pioneersacademy.alkaff.demofirebase.databinding.ActivityAddsBinding


class AddsActivity : AppCompatActivity() {
    lateinit var mBinder:ActivityAddsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = ActivityAddsBinding.inflate(layoutInflater)
        setContentView(mBinder.root)
        MobileAds.initialize(this@AddsActivity) {}
    }


    fun openAdActivity(view: View) {
        when(view.id){
            R.id.banner_ad_button ->
                startActivity(Intent(this@AddsActivity,BannerActivity::class.java))
            R.id.interstitial_ad_button ->
                startActivity(Intent(this@AddsActivity,InterstitialActivity::class.java))
            R.id.native_ad_button ->
                startActivity(Intent(this@AddsActivity,NativeAdsActivity::class.java))
            R.id.native_ad_template_button ->
                startActivity(Intent(this@AddsActivity,NativeAdsWithTemplateActivity::class.java))
            R.id.rewarded_ad_button ->
                startActivity(Intent(this@AddsActivity,RewardedActivity::class.java))

        }
    }
}