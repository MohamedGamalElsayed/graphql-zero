package sample.mohamed.newsfeed.utils

abstract class TestScreen {

  abstract fun isShown()

  /**
   * Used to assert that the screen has changed to the one specified.
   */
  fun hasChangedTo(screen: TestScreen) = screen.isShown()
}