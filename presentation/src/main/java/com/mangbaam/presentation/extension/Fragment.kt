package com.mangbaam.presentation.extension

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Fragment.startActivity(
    finishCallingActivity: Boolean = false,
    noinline block: (Intent.() -> Unit)? = null,
) {
    val intent = Intent(requireContext(), T::class.java)
    block?.let { it(intent) }
    startActivity(intent)
    if (finishCallingActivity) requireActivity().finish()
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.toast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}
