package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy

data class IdentifyFilterStateResult(
    val isSameState: Boolean,
    val request: FilterResultStrategyRequest
)
