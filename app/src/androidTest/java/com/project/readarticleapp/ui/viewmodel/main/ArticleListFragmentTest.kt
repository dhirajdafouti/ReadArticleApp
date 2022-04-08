package com.project.readarticleapp.ui.viewmodel.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.project.readarticleapp.R
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArticleListFragmentTest : TestCase() {

    @Test
    fun test_isArticleListFragmentDisplayed() {
        val scenario = launchFragmentInContainer<ArticleListFragment>()
         onView(withId(R.id.swipeRefreshLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_isRecyclerViewDisplayed() {
        val scenario = launchFragmentInContainer<ArticleListFragment>()
        onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun test_isProgressWheelDisplayed() {
        val scenario = launchFragmentInContainer<ArticleListFragment>()
        onView(withId(R.id.status_loading_wheel)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(
            ViewMatchers.Visibility.GONE)))
    }

    @Test
    //TODO:
    fun test_isEmptyListTextVisibleWithTextDisplayed() {
        val scenario = launchFragmentInContainer<ArticleListFragment>()
        onView(withId(R.id.textViewNetworkStatus)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(
            ViewMatchers.Visibility.VISIBLE)))
        onView(withText(R.string.text_no_connectivity)).check(matches(withText("No internet connection")))
    }
}