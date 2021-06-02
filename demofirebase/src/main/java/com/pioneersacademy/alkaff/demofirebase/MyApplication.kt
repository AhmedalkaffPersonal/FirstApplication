package com.pioneersacademy.alkaff.demofirebase

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.facebook.ads.*;


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) { }

        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);


    }

    companion object {

    }
}