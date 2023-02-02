package com.kunize.uswtimetable.ui.class_info.interceptionFilter.addTime.handleFilterResultStrategy.unknownFailFilterResult

import android.widget.Toast
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class UnknownAddTimeFailStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState): Boolean = state is UnknownFilterFailState

    override suspend fun invoke(request: FilterState) {
        Toast.makeText(
            SuwikiApplication.instance,
            "문제가 있어 시간표를 추가할 수 없어요!",
            Toast.LENGTH_LONG
        ).show()
    }
}