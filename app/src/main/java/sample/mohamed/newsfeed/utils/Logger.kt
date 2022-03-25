package sample.mohamed.newsfeed.utils

import android.util.Log
import sample.mohamed.newsfeed.BuildConfig

private const val TAG = "SOCIAL_NETWORK"

/**
 * A Custom Logger for the app.
 *
 * This class helps to log without considering the build type.
 * For the DEBUG build type, it logs normally.
 * TBD: Log to Analytics / Crashlytics in case of RELEASE build type.
 */
open class Logger {
    open fun d(message: String) {
        if (BuildConfig.DEBUG) Log.d(TAG, message)
    }

    open fun i(message: String) {
        if (BuildConfig.DEBUG) Log.i(TAG, message)
    }

    open fun e(message: String) {
        if (BuildConfig.DEBUG) Log.e(TAG, message)
    }
}