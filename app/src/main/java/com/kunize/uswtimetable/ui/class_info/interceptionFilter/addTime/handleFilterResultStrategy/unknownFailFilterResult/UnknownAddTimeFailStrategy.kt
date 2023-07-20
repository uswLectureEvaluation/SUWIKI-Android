package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.unknownFailFilterResult

import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.extensions.showLongToast
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class UnknownAddTimeFailStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState): Boolean = state is UnknownFilterFailState

    override suspend fun invoke(request: FilterState) {
        showLongToast("문제가 있어 시간표를 추가할 수 없어요!")
    }
}