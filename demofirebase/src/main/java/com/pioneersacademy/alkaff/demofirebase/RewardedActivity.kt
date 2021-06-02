package com.pioneersacademy.alkaff.demofirebase

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.pioneersacademy.alkaff.demofirebase.databinding.ActivityRewardedBinding

class RewardedActivity : AppCompatActivity() {
    private lateinit var mBinder:ActivityRewardedBinding
    private lateinit var rewardedAd: RewardedAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = ActivityRewardedBinding.inflate(layoutInflater)
        setContentView(mBinder.root)

        rewardedAd = RewardedAd(this@RewardedActivity, "ca-app-pub-3940256099942544/5224354917")

        mBinder.apply {
            rewardedAd.loadAd(AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
                override fun onRewardedAdLoaded() {
                    super.onRewardedAdLoaded()
                    adVideoButton.visibility = View.VISIBLE
                }
            })

            adVideoButton.setOnClickListener{
                rewardedAd.show(this@RewardedActivity, object: RewardedAdCallback(){
                    override fun onUserEarnedReward(p0: RewardItem) {
                        adVideoButton.visibility = View.INVISIBLE
                        adText.text = "1 available"
                    }

                })
            }
        }

    }


}