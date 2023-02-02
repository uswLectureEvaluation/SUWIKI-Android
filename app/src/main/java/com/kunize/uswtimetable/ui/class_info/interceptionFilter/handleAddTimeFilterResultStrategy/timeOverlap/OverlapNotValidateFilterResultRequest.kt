package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.timeOverlap

import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.FilterResultStrategyRequest

data class OverlapNotValidateFilterResultRequest(val overlapTime: TimeData):
    FilterResultStrategyRequest