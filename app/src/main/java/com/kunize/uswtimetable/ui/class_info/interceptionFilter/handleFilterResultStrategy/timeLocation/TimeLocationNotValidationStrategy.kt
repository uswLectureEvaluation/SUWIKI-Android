package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy.timeLocation

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeLocation.TimeLocationNotValidateState
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy.FilterResultStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy.FilterResultStrategyRequest
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class TimeLocationNotValidationStrategy : FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is TimeLocationNotValidateState

    override fun getRequest(state: FilterState): FilterResultStrategyRequest = FilterResultStrategyRequest.Nothing

    override suspend fun invoke(request: FilterResultStrategyRequest) {
        Toast.makeText(
            SuwikiApplication.instance,
            "시간 또는 장소를 확인해주세요!",
            Toast.LENGTH_LONG
        ).show()
    }
}