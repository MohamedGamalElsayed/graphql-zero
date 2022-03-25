package sample.mohamed.newsfeed.utils

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

class SimpleCountingIdlingResource(resourceName: String) : IdlingResource {

  private val mResourceName: String = resourceName

  private val counter = AtomicInteger(0)

  @Volatile
  private var resourceCallback: IdlingResource.ResourceCallback? = null

  override fun getName(): String {
    return mResourceName
  }

  override fun isIdleNow(): Boolean {
    return counter.get() == 0
  }

  override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
    this.resourceCallback = resourceCallback
  }

  fun increment() {
    counter.getAndIncrement()
  }

  fun decrement() {
    val counterVal = counter.decrementAndGet()
    if (counterVal == 0) {
      // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
      if (null != resourceCallback) {
        resourceCallback!!.onTransitionToIdle()
      }
    }

    if (counterVal < 0) {
      throw IllegalArgumentException("Counter has been corrupted!")
    }
  }
}

