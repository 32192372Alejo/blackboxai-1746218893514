package com.example.interviewface

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class InterviewfaceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Configure Glide
        Glide.get(this).setDefaultRequestOptions(
            com.bumptech.glide.request.RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
        )
    }
}
