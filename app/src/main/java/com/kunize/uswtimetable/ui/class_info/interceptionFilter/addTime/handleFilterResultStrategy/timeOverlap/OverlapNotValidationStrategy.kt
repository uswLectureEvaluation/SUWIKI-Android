package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timeOverlap

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeOverlap.OverlapNotValidateState
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class OverlapNotValidationStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is OverlapNotValidateState
    override suspend fun invoke(request: FilterState) {

        if(request is OverlapNotValidateState) {
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