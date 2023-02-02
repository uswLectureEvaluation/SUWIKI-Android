package com.kunize.uswtimetable.util.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kunize.uswtimetable.util.SuwikiApplication

/**
 * 편하게 [Toast] 를 만들 수 있게 재공하는 확장 함수
 */

fun Activity.toast(
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = applicationContext,
    message = message,
    length = length
)

fun Fragment.toast(
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = requireContext(),
    message = message,
    length = length
)

fun toast(
    context: Context,
    messageBuilder: Context.() -> String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = context,
    message = messageBuilder(context),
    length = length
)

fun toast(
    context: Context,
    message: String,
    length: Int = Toast.LENGTH_SHORT,
) = toastBuilder(
    context = context,
    message = message,
    length = length
)

/**
 * [Toast] 를 반환하기 위해 [also] 로 [show][Toast.show] 진행
 *
 * @return [Toast] 생성된 토스트 객체
 */
private fun toastBuilder(
    context: Context,
    message: String,
    length: Int,
) = Toast.makeText(context, message, length).also { it.show() }

fun showShortToast(message: String) {
    toast(SuwikiApplication.instance, message, Toast.LENGTH_SHORT)
}

fun showLongToast(message: String) {
    toast(SuwikiApplication.instance, message, Toast.LENGTH_SHORT)
}
