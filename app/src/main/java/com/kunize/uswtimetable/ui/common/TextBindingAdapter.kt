package com.kunize.uswtimetable.ui.common

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.PostData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.round

@BindingAdapter("dateTime")
fun applyDateTime(view: TextView, dateTime: LocalDateTime) {
    view.text = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}

@BindingAdapter("point")
fun applyUserPoint(view: TextView, point: Int) {
    if (point >= 0) {
        view.text = view.resources.getString(R.string.detail_point_positive, point)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.custom_red))
    } else {
        view.text = view.resources.getString(R.string.detail_point, point)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.custom_blue))
    }
}

@BindingAdapter("floatText")
fun applyFloatToText(view: TextView, value: Float) {
    view.text = (round(value * 10) / 10).toString() // 소수 둘째 자리에서 반올림
}

@BindingAdapter("postData", "postDataType")
fun applyPostDataText(view: TextView, postData: Int, type: PostData.PostDataType) {
    when (type) {
        PostData.PostDataType.TEAM -> {
            view.text = PostData.team[postData]
            val color = when (postData) {
                0 -> ContextCompat.getColor(view.context, R.color.custom_light_gray)
                1 -> ContextCompat.getColor(view.context, R.color.custom_dark_gray)
                else -> ContextCompat.getColor(view.context, R.color.black)
            }
            view.setTextColor(color)
        }
        PostData.PostDataType.DIFFICULTY -> {
            view.text = PostData.difficulty[postData]
            val color = when (postData) {
                0 -> ContextCompat.getColor(view.context, R.color.custom_light_gray)
                1 -> ContextCompat.getColor(view.context, R.color.custom_dark_gray)
                2 -> ContextCompat.getColor(view.context, R.color.custom_red)
                else -> ContextCompat.getColor(view.context, R.color.black)
            }
            view.setTextColor(color)
        }
        PostData.PostDataType.HOMEWORK -> {
            view.text = PostData.homework[postData]
            val color = when (postData) {
                0 -> ContextCompat.getColor(view.context, R.color.custom_red)
                1 -> ContextCompat.getColor(view.context, R.color.custom_dark_gray)
                2 -> ContextCompat.getColor(view.context, R.color.custom_light_gray)
                else -> ContextCompat.getColor(view.context, R.color.black)
            }
            view.setTextColor(color)
        }
    }
}