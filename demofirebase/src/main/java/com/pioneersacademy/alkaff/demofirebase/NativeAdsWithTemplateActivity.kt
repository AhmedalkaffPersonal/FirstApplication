package com.pioneersacademy.alkaff.demofirebase

import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*

/*
read more @ https://developers.google.com/admob/android/native/templates
*/
class NativeAdsWithTemplateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ads_with_template)

        MobileAds.initialize(this)


        // To take the current activity background color
        val array: TypedArray =
            theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorBackground))
        val backgroundColor: Int = array.getColor(0, 0xFF00FF)
        val background = ColorDrawable(backgroundColor)



        val adLoader = AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
            .forUnifiedNativeAd { nativeAd ->
                val styles =
                    NativeTemplateStyle.Builder().withMainBackgroundColor(background).build()
                val template: TemplateView = findViewById(R.id.my_template)
                template.setStyles(styles)
                // set the native ad to the template
                // (the same idea as mapUnifiedNativeAdToLayout in NativeAdsActivity)
                template.setNativeAd(nativeAd)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                    Log.d(NativeAdsActivity.TAG, "Native add is Failed to load cause:${adError.cause}, Message:${adError.message}")
                    Log.e(NativeAdsActivity.TAG,adError.toString())
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    Log.d(NativeAdsActivity.TAG, "Ad was Closed.")
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    Log.d(NativeAdsActivity.TAG, "Ad was Clicked.")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    Log.d(NativeAdsActivity.TAG, "Ad was loaded.")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.d(NativeAdsActivity.TAG, "Ad was Impression.")
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    Log.d(NativeAdsActivity.TAG, "Ad was Opened.")
                }

                override fun onAdLeftApplication() {
                    super.onAdLeftApplication()
                    Log.d(NativeAdsActivity.TAG, "Ad was Left Application.")
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

    }
}