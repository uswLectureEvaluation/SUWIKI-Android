package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy

import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy.unknownFailFilterResult.UnknownFailStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy.timetableValidation.TimetableValidationStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class HandleFilterResultStrategy(private val doWhenNotInvalidation: (() -> Unit)?) {
    private val strategies = mutableListOf<FilterResultStrategy>()
    fun addStrategy(strategies: List<FilterResultStrategy>) {
        this.strategies.addAll(strategies)
    }

    private fun getStrategy(filterResult: FilterState): FilterResultStrategy {
        return strategies.asSequence()
            .filter { strategy -> strategy.identifyFilterState(filterResult) }.firstOrNull()
            ?: UnknownFailStrategy()
    }

    suspend operator fun invoke(filterResult: FilterState) {
        val strategy = getStrategy(filterResult)
        doWhenNotInvalidation?.let {
            if (filterResult !is FilterState.Validate)
                it()
        }
        strategy(strategy.getRequest(filterResult))
    }
}