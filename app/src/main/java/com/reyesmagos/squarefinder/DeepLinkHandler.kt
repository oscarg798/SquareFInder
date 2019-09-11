package com.reyesmagos.squarefinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.reyesmagos.squarefinder.detail.deeplink.VenueDetailDeepLinkModule
import com.reyesmagos.squarefinder.detail.deeplink.VenueDetailDeepLinkModuleLoader

@DeepLinkHandler(
    VenueDetailDeepLinkModule::class
)
class DeepLinkHandler: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkDelegate = DeepLinkDelegate(
            VenueDetailDeepLinkModuleLoader()
        )
        deepLinkDelegate.dispatchFrom(this)
        finish()
    }
}
