package com.kunize.uswtimetable.util.strategy

import com.kunize.uswtimetable.util.interceptingFilter.FilterState

interface FilterResultStrategy {
    fun identifyFilterState(state: FilterState): Boolean
    suspend operator fun invoke(request: FilterState)
}