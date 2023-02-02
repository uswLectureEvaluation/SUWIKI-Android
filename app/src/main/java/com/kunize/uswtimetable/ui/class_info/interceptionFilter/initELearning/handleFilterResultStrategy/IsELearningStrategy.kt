package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.handleFilterResultStrategy

import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter.IsELearningState
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class IsELearningStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is IsELearningState

    override suspend fun invoke(request: FilterState) {
        if(request is IsELearningState) {
            with(request) {
                location1.setText("이러닝")
                day1.text = "없음"
            }
        }
    }
}