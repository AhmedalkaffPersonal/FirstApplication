package com.pioneersacademy.alkaff.demofirebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.pioneersacademy.alkaff.demofirebase.databinding.ActivityNativeBinding

class NativeAdsActivity : AppCompatActivity() {
    private lateinit var mBinder: ActivityNativeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = ActivityNativeBinding.inflate(layoutInflater)
        setContentView(mBinder.root)

        MobileAds.initialize(this)
        val nativeAdoption = NativeAdOptions.Builder()
            .setMediaAspectRatio(NativeAdOptions.NATIVE_MEDIA_ASPECT_RATIO_ANY)
            .build()

        val adLoader = AdLoader.Builder(this@NativeAdsActivity, "ca-app-pub-3940256099942544/2247696110")
            .forUnifiedNativeAd { googleAd ->
                Log.d(TAG, "Native add is loaded")

                //the native ad will be available inside this method  (unifiedNativeAd)

                val unifiedNativeAdView = layoutInflater.inflate(
                    R.layout.native_ad_layout,
                    null
                ) as UnifiedNativeAdView
                if (googleAd != null) {
                    mapUnifiedNativeAdToLayout(googleAd, unifiedNativeAdView)
                }

                mBinder.idNativeAd.apply {
                    removeAllViews()
                    addView(unifiedNativeAdView)
                }

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    Log.d(TAG, "Native add is Failed to load cause:${adError.cause}, Message:${adError.message}")
                    Log.e(TAG,adError.toString())
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    Log.d(InterstitialActivity.TAG, "Ad was Closed.")
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.d(InterstitialActivity.TAG, "Ad was Clicked.")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.d(InterstitialActivity.TAG, "Ad was loaded.")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.d(InterstitialActivity.TAG, "Ad was Impression.")
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    Log.d(InterstitialActivity.TAG, "Ad was Opened.")
                }

                override fun onAdLeftApplication() {
                    super.onAdLeftApplication()
                    Log.d(InterstitialActivity.TAG, "Ad was Left Application.")
                }
            })
            .withNativeAdOptions(nativeAdoption)
            .build()
        val request = AdRequest.Builder().build()

        adLoader.loadAd(request)


    }

    fun mapUnifiedNativeAdToLayout(adFromGoogle: UnifiedNativeAd, myAdView: UnifiedNativeAdView) {
        val mediaView: MediaView = myAdView.findViewById(R.id.ad_media)
        myAdView.mediaView = mediaView
        myAdView.headlineView = myAdView.findViewById(R.id.ad_headline)
        myAdView.bodyView = myAdView.findViewById(R.id.ad_body)
        myAdView.callToActionView = myAdView.findViewById(R.id.ad_call_to_action)
        myAdView.iconView = myAdView.findViewById(R.id.ad_icon)
        myAdView.priceView = myAdView.findViewById(R.id.ad_price)
        myAdView.starRatingView = myAdView.findViewById(R.id.ad_rating)
        myAdView.storeView = myAdView.findViewById(R.id.ad_store)
        myAdView.advertiserView = myAdView.findViewById(R.id.ad_advertiser)
        (myAdView.headlineView as TextView).text = adFromGoogle.headline
        if (adFromGoogle.body == null) {
            myAdView.bodyView.visibility = View.GONE
        } else {
            (myAdView.bodyView as TextView).text = adFromGoogle.body
        }
        if (adFromGoogle.callToAction == null) {
            myAdView.callToActionView.visibility = View.GONE
        } else {
            (myAdView.callToActionView as Button).text = adFromGoogle.callToAction
        }
        if (adFromGoogle.icon == null) {
            myAdView.iconView.visibility = View.GONE
        } else {
            (myAdView.iconView as ImageView).setImageDrawable(adFromGoogle.icon.drawable)
        }
        if (adFromGoogle.price == null) {
            myAdView.priceView.visibility = View.GONE
        } else {
            (myAdView.priceView as TextView).text = adFromGoogle.price
        }
        if (adFromGoogle.starRating == null) {
            myAdView.starRatingView.visibility = View.GONE
        } else {
            (myAdView.starRatingView as RatingBar).rating = adFromGoogle.starRating.toFloat()
        }
        if (adFromGoogle.store == null) {
            myAdView.storeView.visibility = View.GONE
        } else {
            (myAdView.storeView as TextView).text = adFromGoogle.store
        }
        if (adFromGoogle.advertiser == null) {
            myAdView.advertiserView.visibility = View.GONE
        }else if(adFromGoogle.icon == null){
            myAdView.iconView.visibility = View.GONE
        }else {
            (myAdView.advertiserView as TextView).text = adFromGoogle.advertiser
        }
        myAdView.setNativeAd(adFromGoogle)
    }

    companion object {
        val TAG = NativeAdsActivity::class.java.simpleName
    }
}