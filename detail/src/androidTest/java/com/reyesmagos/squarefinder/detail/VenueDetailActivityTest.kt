package com.reyesmagos.squarefinder.detail

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.reyesmagos.squarefinder.core.ARGUMENT_VIEW_VENUE
import com.reyesmagos.squarefinder.core.models.ViewVenue
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class VenueDetailActivityTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<VenueDetailActivity> =
        object : ActivityTestRule<VenueDetailActivity>(VenueDetailActivity::class.java) {

            override fun getActivityIntent(): Intent {
                val intent = Intent()
                intent.putExtra(ARGUMENT_VIEW_VENUE, ViewVenue("My coffee","1123",1.1,2.2))
                return intent
            }
        }

    @Test
    fun shouldHaveAViewForTheCoffeeName() {
        Espresso.onView(ViewMatchers.withId(R.id.tvName))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun shouldShowTheCoffeShopName(){
        Espresso.onView(withText("My coffee"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun shouldShowTheCoffeeShopAddress(){
        Espresso.onView(allOf(ViewMatchers.withId(R.id.tvAddress), withText("1123")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
