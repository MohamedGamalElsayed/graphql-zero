package sample.mohamed.newsfeed.features.timeline.posts.robots

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.not
import sample.mohamed.newsfeed.R
import sample.mohamed.newsfeed.utils.EspressoIdlingResource
import sample.mohamed.newsfeed.utils.TestScreen

class PostDetailsRobot {

    fun assert(assertions: Screen.() -> Unit) = apply { Screen().apply(assertions) }

    fun pressBack() = apply { Espresso.pressBack() }

    class Screen: TestScreen() {
        override fun isShown() {
            onView(withId(R.id.fragment_post_details))
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
        fun authorIsVisible() {
            onView(withId(R.id.tv_author))
                .check(matches(isDisplayed()))
        }
        fun titleIsVisible() {
            onView(withId(R.id.tv_title))
                .check(matches(isDisplayed()))
        }
        fun bodyIsVisible() {
            onView(withId(R.id.tv_body))
                .check(matches(isDisplayed()))
        }
    }
}