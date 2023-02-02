package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.timeVisible

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeVisible.VisibleNotValidateState
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.FilterResultStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.FilterResultStrategyRequest
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class TimeVisibleNotValidationStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState): Boolean = state is VisibleNotValidateState

    override fun getRequest(state: FilterState): FilterResultStrategyRequest = FilterResultStrategyRequest.Nothing

    override suspend fun invoke(request: FilterResultStrategyRequest) {
        Toast.makeText(
            SuwikiApplication.instance,
            "시간 또는 장소를 확인해주세요!",
            Toast.LENGTH_LONG
        ).show()
    }
}