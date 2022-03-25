package sample.mohamed.newsfeed.features.timeline.posts.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import sample.mohamed.newsfeed.features.timeline.TimelineActivity
import sample.mohamed.newsfeed.features.timeline.posts.robots.PostDetailsRobot
import sample.mohamed.newsfeed.features.timeline.posts.robots.PostsListingRobot
import sample.mohamed.newsfeed.utils.EspressoIdlingResource

class PostsScenarioTest {
    @get:Rule
    val activityTestRule = ActivityTestRule(TimelineActivity::class.java)

    @Test
    fun postListingToDetailsScenario() {
        val postsListingRobot = PostsListingRobot()
        postsListingRobot
            .assert {
                isShown()
                progressBarIsShown()
                recyclerViewIsHidden()
            }
        register()
        postsListingRobot
            .scrollDown()
            .assert {
                recyclerViewIsShown()
                progressBarIsHidden()
            }
            .scrollUp()
        unregister()
        postsListingRobot.clickOnPostAtPosition(1)

        val postDetailsRobot = PostDetailsRobot()
        postDetailsRobot
            .assert {
                isShown()
                progressBarIsShown()
            }
        register()
        postDetailsRobot
            .assert {
                progressBarIsHidden()
                authorIsVisible()
                titleIsVisible()
                bodyIsVisible()
            }
            .pressBack()
        postsListingRobot
            .assert {
                isShown()
                progressBarIsHidden()
            }
            .clickOnPostAtPosition(1)
        postDetailsRobot
            .assert {
                progressBarIsHidden()
                authorIsVisible()
                titleIsVisible()
                bodyIsVisible()
            }
        unregister()
    }

    private fun register() =
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)

    private fun unregister() =
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
}