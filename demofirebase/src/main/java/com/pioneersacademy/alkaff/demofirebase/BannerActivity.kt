package com.pioneersacademy.alkaff.demofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.pioneersacademy.alkaff.demofirebase.databinding.ActivityBannerBinding

class BannerActivity : AppCompatActivity() {
    lateinit var mBinder:ActivityBannerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(mBinder.root)

        // banner ads

        // TODO: test if setting AdSize programmatically is working without binding
        // Make sure you set the ad size and ad unit ID in the same manner (i.e. set both in XML or both programmatically).
//        adView.adSize = AdSize.BANNER
//        adView.adUnitId = resources.getString(R.string.test_unit_id)
        mBinder.apply {
            val adRequest = AdRequest.Builder().build()

            adView.adListener = object :AdListener(){

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Log.d(TAG,"Ad is failed to load")
                    Log.e(TAG,error.toString())
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.d(TAG,"Ad is Clicked")
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    Log.d(TAG,"Ad is closed")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.d(TAG,"Ad is loaded")
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    Log.d(TAG,"Ad is opened")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.d(TAG,"Ad is Impression")
                }

            }

            adView.loadAd(adRequest)



        }




    }

    companion object{
        val TAG = BannerActivity::class.java.simpleName
    }
}