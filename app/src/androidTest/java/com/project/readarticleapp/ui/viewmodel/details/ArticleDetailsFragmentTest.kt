package com.project.readarticleapp.ui.viewmodel.details

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.project.readarticleapp.R
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArticleDetailsFragmentTest : TestCase() {

    @Test
    fun test_isArticleListFragmentDisplayed(){
        val scenario= launchFragmentInContainer<ArticleDetailsFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.status_loading_wheel))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}