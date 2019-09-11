package com.reyesmagos.squarefinder.core

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.startDeepLinkIntent(deepLink: String, arguments: Bundle? = null) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(deepLink)
    arguments?.let {
        intent.putExtras(it)
    }
    startActivity(intent)
}
