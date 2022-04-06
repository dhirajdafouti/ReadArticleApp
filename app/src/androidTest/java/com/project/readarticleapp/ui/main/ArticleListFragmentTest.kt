package com.project.readarticleapp.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.project.readarticleapp.R
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArticleListFragmentTest : TestCase() {

    @Test
    fun test_isArticleListFragmentDisplayed(){
        val scenario= launchFragmentInContainer<ArticleListFragment>()
        onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}