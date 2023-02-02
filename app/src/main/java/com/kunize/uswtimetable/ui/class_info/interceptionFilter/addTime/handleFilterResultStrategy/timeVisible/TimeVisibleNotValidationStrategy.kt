package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.timeVisible

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.timeVisible.VisibleNotValidateState
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class TimeVisibleNotValidationStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState): Boolean = state is VisibleNotValidateState

    override suspend fun invoke(request: FilterState) {
        Toast.makeText(
            SuwikiApplication.instance,
            "시간 또는 장소를 확인해주세요!",
            Toast.LENGTH_LONG
        ).show()
    }
}