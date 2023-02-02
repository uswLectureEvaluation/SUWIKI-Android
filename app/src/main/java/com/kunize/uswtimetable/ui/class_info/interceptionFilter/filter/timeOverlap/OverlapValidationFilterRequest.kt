package com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeOverlap

import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest

data class OverlapValidationFilterRequest(
    val timeDataTobeAdded: MutableList<TimeData>,
    val deleteIdx: Int,
    val tempDeleteData: TimeData,
    val currentTimeTable: MutableList<TimeData>
): FilterRequest
