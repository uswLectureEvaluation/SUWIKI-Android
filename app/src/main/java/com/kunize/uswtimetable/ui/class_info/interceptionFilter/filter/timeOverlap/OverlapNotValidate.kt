package com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeOverlap

import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

data class OverlapNotValidate(val overlapTime: TimeData): FilterState