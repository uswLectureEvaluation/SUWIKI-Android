package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeVisible

import android.widget.TextView
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest

data class VisibleValidationFilterRequest(
    val deleteTimeList: List<TextView>
): FilterRequest
