package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.handleFilterResultStrategy

import com.kunize.uswtimetable.util.extensions.showLongToast
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class UnknownELearningFailStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is UnknownFilterFailState

    override suspend fun invoke(request: FilterState) {
        showLongToast("문제가 있어 시간표를 불러오지 못했어요.")
    }
}