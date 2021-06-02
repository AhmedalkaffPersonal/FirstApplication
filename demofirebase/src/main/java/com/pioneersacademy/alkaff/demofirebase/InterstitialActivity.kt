package com.pioneersacademy.alkaff.demofirebase

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*

import com.pioneersacademy.alkaff.demofirebase.databinding.ActivityInterstitialBinding

class InterstitialActivity : AppCompatActivity() {
    private lateinit var mBinder:ActivityInterstitialBinding
    // Interstitial ads
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinder = ActivityInterstitialBinding.inflate(layoutInflater)
        setContentView(mBinder.root)

        MobileAds.initialize(this@InterstitialActivity)
        val adRequest = AdRequest.Builder().build()

        // Interstitial ads
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd!!.apply {
            setAdUnitId("ca-app-pub-3940256099942544/1033173712")
            loadAd(adRequest)
            adListener = object :AdListener(){
                override fun onAdClosed() {
                    super.onAdClosed()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.d(TAG, "Ad was Clicked.")
                }

                override fun onAdFailedToLoad(adError: LoadAdError?) {
                    super.onAdFailedToLoad(adError)
                    adError?.message?.let { Log.d(TAG, it) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.d(TAG, "Ad was loaded.")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.d(TAG, "Ad was Impression.")
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    Log.d(TAG, "Ad was Opened.")
                }

                override fun onAdLeftApplication() {
                    super.onAdLeftApplication()
                    Log.d(TAG, "Ad was Left Application.")
                }

            }

        }

    }

    fun nextLevel(view: View?) {
        mInterstitialAd?.apply {
           show()
            mBinder.textView.text = "level 2"
        }


    }

    companion object{
        val TAG = InterstitialActivity::class.java.simpleName
    }
}