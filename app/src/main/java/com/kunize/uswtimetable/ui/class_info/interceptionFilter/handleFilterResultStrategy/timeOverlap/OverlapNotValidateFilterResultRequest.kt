package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy.timeOverlap

import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy.FilterResultStrategyRequest

data class OverlapNotValidateFilterResultRequest(val overlapTime: TimeData):
    FilterResultStrategyRequest