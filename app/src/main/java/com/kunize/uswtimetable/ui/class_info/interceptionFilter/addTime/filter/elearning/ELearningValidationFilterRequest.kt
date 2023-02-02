package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.elearning

import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest

data class ELearningValidationFilterRequest(
    val timeDataTobeAdded: MutableList<TimeData>,
    val currentTimeTable: MutableList<TimeData>
): FilterRequest
