package sample.mohamed.newsfeed.utils

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.hide(partially: Boolean = false) {
    this.visibility = if (partially) INVISIBLE else GONE
}

fun View.show() {
    this.visibility = VISIBLE
}