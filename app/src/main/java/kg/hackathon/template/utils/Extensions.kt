package kg.hackathon.template.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Context.toast(@StringRes resId: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(getString(resId), length)
}

fun Context.toast(content: String, length: Int = Toast.LENGTH_SHORT) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, content, length).show()
    }
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(message, length)
}

fun Fragment.navigateTo(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}

fun Fragment.popBack() {
    findNavController().popBackStack()
}

fun Fragment.hideSystemKeyboard() {
    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun String.isInvalidDate(): Boolean {
    val regexPattern = """^\d{4}-\d{2}-\d{2}$""".toRegex()
    return !regexPattern.matches(this)
}