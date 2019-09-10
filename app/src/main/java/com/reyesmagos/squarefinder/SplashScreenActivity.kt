package com.reyesmagos.squarefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.reyesmagos.squarefinder.map.MapActivity
import kotlinx.android.synthetic.main.activity_main.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivIcon?.postDelayed({
            startActivity(Intent(this, MapActivity::class.java))
        },SPLASH_SCREEN_DISMISS_TIME_IN_MILLISECONDS)
    }

    companion object {
        private const val SPLASH_SCREEN_DISMISS_TIME_IN_MILLISECONDS = 3000L
    }
}
