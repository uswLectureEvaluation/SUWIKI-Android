package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy

import com.kunize.uswtimetable.util.interceptingFilter.FilterState

interface FilterResultStrategy {
    fun identifyFilterState(state: FilterState): Boolean

    fun getRequest(state: FilterState): FilterResultStrategyRequest

    suspend operator fun invoke(request: FilterResultStrategyRequest)
}