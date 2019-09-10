package com.reyesmagos.squarefinder

import android.view.View
import android.widget.ImageView
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reyesmagos.squarefinder.core.CoreApplication
import org.amshove.kluent.shouldEqual
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = CoreApplication::class)
class SplashScreenActivityTest  {

    @Test
    fun `should have a view for the icon`(){
        //Given
        ActivityScenario.launch(SplashScreenActivity::class.java).onActivity {
            //When
            val view = it.findViewById<ImageView>(R.id.ivIcon)

            //then
            view.visibility shouldEqual View.VISIBLE
        }
    }
}
