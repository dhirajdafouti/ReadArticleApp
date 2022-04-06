package com.project.readarticleapp.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.project.readarticleapp.R
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun test_iaActivityInView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(withId(R.id.main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isActivityHasAppBarInView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        Espresso.onView(withId(R.id.appBar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.appBar)).check(ViewAssertions.matches(ViewMatchers.hasTextColor(R.color.toolbarColor)))

        Espresso.onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(allOf(withId(R.id.appBar), withText("News HeadLines")))

    }

}