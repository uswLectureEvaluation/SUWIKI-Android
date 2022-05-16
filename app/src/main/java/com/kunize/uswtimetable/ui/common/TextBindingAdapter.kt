package com.kunize.uswtimetable.ui.common

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.kunize.uswtimetable.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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