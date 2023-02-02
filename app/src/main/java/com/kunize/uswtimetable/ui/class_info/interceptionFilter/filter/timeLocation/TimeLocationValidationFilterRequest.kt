package com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeLocation

import android.widget.EditText
import android.widget.TextView
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest

data class TimeLocationValidationFilterRequest(
    val deleteIdx: Int,
    val tempDeleteData: TimeData,
    val dayList: List<TextView>,
    val locationList: List<EditText>,
    val startTimeList: List<EditText>,
    val endTimeList: List<EditText>
): FilterRequest
