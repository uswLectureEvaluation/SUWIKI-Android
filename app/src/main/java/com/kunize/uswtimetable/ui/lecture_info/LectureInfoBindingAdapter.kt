package com.kunize.uswtimetable.ui.lecture_info

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.kunize.uswtimetable.MainActivity.Companion.dp
import com.kunize.uswtimetable.R

object LectureInfoBindingAdapter {
    @BindingAdapter("yearSemesterList")
    @JvmStatic
    fun setList(flexBox: FlexboxLayout, items : java.util.ArrayList<String>) {
        for(item in items) {
            val textView = TextView(flexBox.context)
            textView.text = item
            textView.textSize = 12f
            textView.setTextColor(Color.BLACK)
            textView.setBackgroundResource(R.drawable.rounding_background)
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 8.dp,8.dp,0)
            textView.layoutParams = lp
            flexBox.addView(textView)
        }
    }

    @BindingAdapter("customText")
    @JvmStatic
    fun setText(textView: TextView, string: String) {
        textView.text = string
        when (string) {
            in setOf("없음", "잘줌") -> textView.setTextColor(ContextCompat.getColor(textView.context ,R.color.custom_light_gray))
            in setOf("있음", "보통") -> textView.setTextColor(ContextCompat.getColor(textView.context ,R.color.custom_dark_gray))
            else -> textView.setTextColor(ContextCompat.getColor(textView.context ,R.color.custom_red))
        }
    }
}