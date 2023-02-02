package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.elearning

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.filter.elearning.ELearningNotValidateState
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class ELearningNotValidationStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is ELearningNotValidateState

    override suspend fun invoke(request: FilterState) {
        Toast.makeText(
            SuwikiApplication.instance,
            "토요일이거나 시간이 없는 과목은 하나만 추가 가능합니다!",
            Toast.LENGTH_LONG
        ).show()
    }
}