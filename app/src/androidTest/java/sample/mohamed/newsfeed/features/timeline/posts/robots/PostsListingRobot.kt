package sample.mohamed.newsfeed.features.timeline.posts.robots

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import sample.mohamed.newsfeed.R
import sample.mohamed.newsfeed.utils.EspressoIdlingResource
import sample.mohamed.newsfeed.utils.TestScreen

class PostsListingRobot {

    fun assert(assertions: Screen.() -> Unit) = apply { Screen().apply(assertions) }

    fun clickOnPostAtPosition(position: Int) = apply {
        onView(allOf(isDisplayed(), withId(R.id.rv_posts_list)))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    fun scrollDown() = apply {
        onView(withId(R.id.rv_posts_list))
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeUp())
            .perform(swipeUp())
    }

    fun scrollUp() = apply {
        onView(withId(R.id.rv_posts_list))
            .perform(swipeDown())
            .perform(swipeDown())
            .perform(swipeDown())
            .perform(swipeDown())
            .perform(swipeDown())
    }

    class Screen: TestScreen() {
        override fun isShown() {
            onView(withId(R.id.fragment_posts_listing))
                .check(matches(isDisplayed()))
        }
        fun progressBarIsShown() {
            onView(withId(R.id.pb_loader))
                .check(matches(isDisplayed()))
        }
        fun progressBarIsHidden() {
            onView(withId(R.id.pb_loader))
                .check(matches(not(isDisplayed())))
        }
        fun recyclerViewIsShown() {
            onView(withId(R.id.rv_posts_list))
                .check(matches(isDisplayed()))
        }
        fun recyclerViewIsHidden() {
            onView(withId(R.id.rv_posts_list))
                .check(matches(not(isDisplayed())))
        }
    }
}