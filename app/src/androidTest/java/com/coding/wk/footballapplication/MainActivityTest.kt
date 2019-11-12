package com.coding.wk.footballapplication

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.coding.wk.footballapplication.R.id.*
import com.coding.wk.footballapplication.activities.main.MainActivity
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    private fun idle(){
        try{
            Thread.sleep(2000)
        }
        catch(e: InterruptedException){
            e.printStackTrace()
        }
    }
    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeTest(){
            InstrumentationRegistry.getTargetContext().deleteDatabase("FavoriteDB.db")
        }
    }
    @Test
    fun testAppBehaviour(){
        idle()
        onView(withId(spinner_last)).check(matches(isDisplayed()))
        onView(withId(rv_last)).check(matches(isDisplayed()))
        onView(withId(view_pager)).perform(swipeLeft())
        idle()
        onView(withId(spinner_next)).check(matches(isDisplayed()))
        onView(withId(rv_next)).check(matches(isDisplayed()))
        onView(withId(rv_next)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_favorite)).check(matches(isDisplayed())).perform(click())
        pressBack()
        onView(withId(spinner_next)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        idle()
        onView(withId(rv_next)).check(matches(isDisplayed()))
        onView(withId(view_pager)).perform(swipeRight())
        idle()
        onView(withId(rv_last)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_favorite)).check(matches(isDisplayed())).perform(click())
        pressBack()
        onView(withId(spinner_last)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        idle()
        onView(withId(rv_last)).check(matches(isDisplayed()))
        onView(withId(search_home)).check(matches(isDisplayed())).perform(click())
        onView(withId(searchview_menu)).check(matches(isDisplayed())).perform(click())
        onView(withId(searchview_menu)).perform(typeText("Barcelona"))
        idle()
        onView(withId(rv_match_search)).check(matches(isDisplayed()))
        pressBack()
        pressBack()
        onView(withId(navigation_view)).check(matches(isDisplayed()))
        onView(withId(teams_menu)).check(matches(isDisplayed())).perform(click())
        onView(withId(spinner_team)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        idle()
        onView(withId(rv_team)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        idle()
        onView(withId(add_favorite)).check(matches(isDisplayed())).perform(click())
        onView(withId(text_overview)).check(matches(isDisplayed()))
        onView(withId(detail_view_pager)).perform(swipeLeft())
        idle()
        onView(withId(rv_all_player)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(back_detail_player)).check(matches(isDisplayed()))
        pressBack()
        pressBack()
        onView(withId(searchview_menu)).check(matches(isDisplayed())).perform(click())
        onView(withId(searchview_menu)).perform(typeText("Barcelona"))
        idle()
        onView(withId(rv_team)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(favorites_menu)).check(matches(isDisplayed())).perform(click())
        idle()
        idle()
        onView(withId(rv_favorites)).check(matches(isDisplayed()))
        onView(withId(rv_favorites)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_favorite)).check(matches(isDisplayed())).perform(click())
        pressBack()
        onView(withId(fav__match_sr)).perform(swipeDown())
        onView(withId(rv_favorites)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_favorite)).check(matches(isDisplayed())).perform(click())
        pressBack()
        onView(withId(fav__match_sr)).perform(swipeDown())
        onView(withId(fav_view_pager)).perform(swipeLeft())
        idle()
        onView(withId(rv_fav_team)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_favorite)).check(matches(isDisplayed())).perform(click())
        pressBack()
        onView(withId(fav_team_sr)).perform(swipeDown())
    }
}