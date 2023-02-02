package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeOverlap

import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest

data class OverlapValidationFilterRequest(
    val timeDataTobeAdded: MutableList<TimeData>,
    val currentTimeTable: MutableList<TimeData>
): FilterRequest
