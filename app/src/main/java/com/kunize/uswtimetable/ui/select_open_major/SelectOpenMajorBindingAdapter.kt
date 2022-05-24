package com.kunize.uswtimetable.ui.select_open_major

import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.kunize.uswtimetable.R

@BindingAdapter("bookmark_enabled")
fun setCheckBox(
    checkBox: CheckBox,
    isLoggedIn: Boolean
) {
    checkBox.setOnCheckedChangeListener { _, _ ->
        if (!isLoggedIn) {
            checkBox.isChecked = false
            Toast.makeText(checkBox.context, "먼저 로그인 해주세요!", Toast.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("text", "searchText")
fun highlightText(
    textView: TextView,
    text: String,
    searchText: String
) {
    val startIdx = text.indexOf(searchText)
    if (startIdx >= 0) {
        val endIdx = startIdx + searchText.length
        val spanString =
            Spannable.Factory.getInstance().newSpannable(text)
        spanString.setSpan(
            ForegroundColorSpan(textView.context.getColor(R.color.suwiki_blue_900)),
            startIdx,
            endIdx,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spanString
    }
}