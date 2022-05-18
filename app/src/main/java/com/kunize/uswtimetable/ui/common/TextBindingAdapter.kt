package com.kunize.uswtimetable.ui.common

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.PostData
import com.kunize.uswtimetable.util.PostData.difficulty
import com.kunize.uswtimetable.util.PostData.homework
import com.kunize.uswtimetable.util.PostData.team
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
fun applyPostData(view: TextView, level: Int, type: PostData.PostDataType) {
    applyTextColor(view, level)
    view.text = when (type) {
        PostData.PostDataType.TEAM -> team[level]
        PostData.PostDataType.HOMEWORK -> homework[level]
        PostData.PostDataType.DIFFICULTY -> difficulty[level]
    }
}

@BindingAdapter("textColorLevel")
fun applyTextColor(view: TextView, level: Int) {
    val color = when(level) {
        0 -> ContextCompat.getColor(view.context, R.color.custom_light_gray)
        1 -> ContextCompat.getColor(view.context, R.color.custom_dark_gray)
        2 -> ContextCompat.getColor(view.context, R.color.custom_red)
        else -> ContextCompat.getColor(view.context, R.color.black)
    }
    view.setTextColor(color)
}