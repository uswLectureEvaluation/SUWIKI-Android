package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeOverlap

import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

data class OverlapNotValidateState(val overlapTime: TimeData): FilterState