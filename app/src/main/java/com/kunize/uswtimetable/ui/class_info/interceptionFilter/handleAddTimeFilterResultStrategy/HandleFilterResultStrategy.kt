package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy

import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.unknownFailFilterResult.UnknownFailStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class HandleFilterResultStrategy(private val doWhenNotInvalidation: (() -> Unit)?) {
    private val strategies = mutableListOf<FilterResultStrategy>()
    fun addStrategy(strategies: List<FilterResultStrategy>): HandleFilterResultStrategy {
        this.strategies.addAll(strategies)
        return this
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