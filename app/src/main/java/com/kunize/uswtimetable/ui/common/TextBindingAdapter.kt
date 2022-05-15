package com.kunize.uswtimetable.ui.common

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("dateTime")
fun applyDateTime(view: TextView, dateTime: LocalDateTime) {
    view.text = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}