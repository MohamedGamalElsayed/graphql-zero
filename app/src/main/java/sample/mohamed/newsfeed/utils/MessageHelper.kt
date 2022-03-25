package sample.mohamed.newsfeed.utils

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import sample.mohamed.newsfeed.R

object MessageHelper {

    fun showMessage(focusedView: View, message: String, type: Type) {

        val snackbar = Snackbar.make(focusedView, message, Snackbar.LENGTH_LONG)

        when (type) {
            Type.ERROR -> {
                snackbar.view.setBackgroundResource(R.color.red)
                snackbar.setActionTextColor(Color.WHITE)
            }
            Type.SUCCESS -> {
                snackbar.view.setBackgroundResource(R.color.green)
                snackbar.setActionTextColor(Color.WHITE)
            }
        }

        val messageText =
            snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        messageText.maxLines = 2
        snackbar.show()
    }

    enum class Type {
        ERROR, SUCCESS
    }
}