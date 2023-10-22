package com.mangbaam.presentation.extension

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

inline fun <reified T : Activity> Activity.startActivity(
  finishCallingActivity: Boolean = false,
  noinline block: (Intent.() -> Unit)? = null,
) {
  val intent = Intent(this, T::class.java)
  block?.let { it(intent) }
  startActivity(intent)
  if (finishCallingActivity) finish()
}

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, message, duration).show()
}

fun Activity.toast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
  Toast.makeText(this, message, duration).show()
}

fun Activity.hideKeyboard() {
  val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java) ?: return
  val view = currentFocus ?: View(this)
  imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}
