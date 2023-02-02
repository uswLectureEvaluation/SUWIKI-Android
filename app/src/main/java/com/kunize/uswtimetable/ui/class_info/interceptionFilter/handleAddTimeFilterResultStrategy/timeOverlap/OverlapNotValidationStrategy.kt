package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.timeOverlap

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeOverlap.OverlapNotValidateState
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.FilterResultStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.FilterResultStrategyRequest
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class OverlapNotValidationStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is OverlapNotValidateState

    override fun getRequest(state: FilterState): FilterResultStrategyRequest {
        return if(state is OverlapNotValidateState) OverlapNotValidateFilterResultRequest(state.overlapTime) else FilterResultStrategyRequest.Nothing
    }

    override suspend fun invoke(request: FilterResultStrategyRequest) {

        if(request is OverlapNotValidateFilterResultRequest) {
            with(request) {
                Toast.makeText(
                    SuwikiApplication.instance,
                    "겹치는 시간이 있어요!\n${overlapTime.name} (${overlapTime.day}${overlapTime.startTime} ~ ${overlapTime.endTime})",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}