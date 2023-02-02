package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.handleFilterResultStrategy

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter.IsELearningState
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class UnknownELearningFailStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is UnknownFilterFailState

    override suspend fun invoke(request: FilterState) {
        Toast.makeText(
            SuwikiApplication.instance,
            "문제가 있어 시간표를 불러오지 못했어요.",
            Toast.LENGTH_LONG
        ).show()
    }
}