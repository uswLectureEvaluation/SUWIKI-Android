package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.elearning

import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.elearning.ELearningNotValidateState
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.extensions.showLongToast
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class ELearningNotValidationStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is ELearningNotValidateState

    override suspend fun invoke(request: FilterState) {
        showLongToast("토요일이거나 시간이 없는 과목은 최대 3개까지 추가 가능해요")
    }
}