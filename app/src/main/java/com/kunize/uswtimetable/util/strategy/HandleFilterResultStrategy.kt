package com.kunize.uswtimetable.util.strategy

import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.unknownFailFilterResult.UnknownAddTimeFailStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class HandleFilterResultStrategy(
    private val unknownFailStrategy: FilterResultStrategy,
    private val doWhenNotInvalidation: (() -> Unit)? = null
) {
    private val strategies = mutableListOf<FilterResultStrategy>()


    fun addStrategy(strategies: List<FilterResultStrategy>): HandleFilterResultStrategy {
        this.strategies.addAll(strategies)
        return this
    }

    private fun getStrategy(filterResult: FilterState): FilterResultStrategy {
        return strategies.asSequence()
            .filter { strategy -> strategy.identifyFilterState(filterResult) }.firstOrNull()
            ?: unknownFailStrategy
    }

    suspend operator fun invoke(filterResult: FilterState) {
        val strategy = getStrategy(filterResult)
        doWhenNotInvalidation?.let {
            if (filterResult !is FilterState.Validate)
                it()
        }
        strategy(filterResult)
    }
}