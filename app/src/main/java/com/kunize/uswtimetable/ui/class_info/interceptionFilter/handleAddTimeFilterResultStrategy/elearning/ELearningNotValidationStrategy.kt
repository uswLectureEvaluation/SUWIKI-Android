package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.elearning

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.elearning.ELearningNotValidateState
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.FilterResultStrategy
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleAddTimeFilterResultStrategy.FilterResultStrategyRequest
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class ELearningNotValidationStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is ELearningNotValidateState

    override fun getRequest(state: FilterState): FilterResultStrategyRequest = FilterResultStrategyRequest.Nothing

    override suspend fun invoke(request: FilterResultStrategyRequest) {
        Toast.makeText(
            SuwikiApplication.instance,
            "토요일이거나 시간이 없는 과목은 하나만 추가 가능합니다!",
            Toast.LENGTH_LONG
        ).show()
    }
}