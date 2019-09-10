package com.reyesmagos.squarefinder.map

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reyesmagos.squarefinder.core.CoreApplication
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(application = CoreApplication::class)
class MapActivityTest {

    @Test
    fun `should have a fragment for the map`() {
        //Given
        ActivityScenario.launch(MapActivity::class.java).onActivity {
            //when
            val mapView = it.findViewById<View>(R.id.map)

            //then
            mapView shouldNotBe null
            mapView.visibility shouldBe View.VISIBLE
        }
    }

}
