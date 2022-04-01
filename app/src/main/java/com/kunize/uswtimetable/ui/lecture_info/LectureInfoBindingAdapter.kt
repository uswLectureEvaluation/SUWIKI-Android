package com.kunize.uswtimetable.ui.lecture_info

import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.kunize.uswtimetable.MainActivity.Companion.dp
import com.kunize.uswtimetable.R
import kotlin.math.roundToInt

object LectureInfoBindingAdapter {
    @BindingAdapter("yearSemesterList")
    @JvmStatic
    fun setList(flexBox: FlexboxLayout, items: String?) {
        if (items == null || flexBox.size != 0)
            return
        for (item in items.split(",")) {
            val textView = TextView(flexBox.context)
            textView.text = item.replace(" ", "")
            textView.textSize = 12f
            textView.setTextColor(Color.BLACK)
            textView.setBackgroundResource(R.drawable.rounding_light_gray_background)
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(0, 8.dp, 8.dp, 0)
            textView.layoutParams = lp
            flexBox.addView(textView)
        }
    }

    @BindingAdapter("lectureTotalAvg")
    @JvmStatic
    fun unregistered(textView: TextView, value: Float) {
        if(value == 0f)
        {
            textView.text = textView.context.getString(R.string.hyphen)
        }
    }

    @BindingAdapter("lectureTeamAvg")
    @JvmStatic
    fun setTeamText(textView: TextView, value: Number) {
        val colorId: Int
        val textId: Int
        when (value.toFloat().roundToInt()) {
            0 -> {
                textId = R.string.not_exist
                colorId = R.color.custom_light_gray
            }
            else -> {
                textId = R.string.exist
                colorId = R.color.custom_dark_gray
            }
        }
        textView.text = textView.context.getString(textId)
        textView.setTextColor(ContextCompat.getColor(textView.context, colorId))
    }

    @BindingAdapter("lectureDifficultyAvg")
    @JvmStatic
    fun setDifficultText(textView: TextView, value: Number) {
        val colorId: Int
        val textId: Int
        when (value.toFloat().roundToInt()) {
            0 -> {
                textId = R.string.good
                colorId = R.color.custom_light_gray
            }
            1 -> {
                textId = R.string.normal
                colorId = R.color.custom_dark_gray
            }
            else -> {
                textId = R.string.picky
                colorId = R.color.custom_red
            }
        }
        textView.text = textView.context.getString(textId)
        textView.setTextColor(ContextCompat.getColor(textView.context, colorId))
    }

    @BindingAdapter("lectureHomeworkAvg")
    @JvmStatic
    fun setHomeworkText(textView: TextView, value: Number) {
        val colorId: Int
        val textId: Int
        when (value.toFloat().roundToInt()) {
            0 -> {
                textId = R.string.not_exist
                colorId = R.color.custom_light_gray
            }
            1 -> {
                textId = R.string.normal
                colorId = R.color.custom_dark_gray
            }
            else -> {
                textId = R.string.many
                colorId = R.color.custom_red
            }
        }
        textView.text = textView.context.getString(textId)
        textView.setTextColor(ContextCompat.getColor(textView.context, colorId))
    }
}