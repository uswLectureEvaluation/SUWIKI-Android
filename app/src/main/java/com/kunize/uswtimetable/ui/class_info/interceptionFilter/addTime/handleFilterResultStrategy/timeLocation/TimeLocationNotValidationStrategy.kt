package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timeLocation

import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeLocation.TimeLocationNotValidateState
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.extensions.showLongToast
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class TimeLocationNotValidationStrategy : FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is TimeLocationNotValidateState

    override suspend fun invoke(request: FilterState) {
        showLongToast("시간 또는 장소를 확인해주세요!")
    }
}