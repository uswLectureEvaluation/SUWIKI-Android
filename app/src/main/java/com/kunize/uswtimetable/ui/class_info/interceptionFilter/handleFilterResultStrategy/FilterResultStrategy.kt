package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy

import com.kunize.uswtimetable.util.interceptingFilter.FilterState

interface FilterResultStrategy {
    fun identifyFilterState(filter: FilterState): Boolean

    fun invoke()
}