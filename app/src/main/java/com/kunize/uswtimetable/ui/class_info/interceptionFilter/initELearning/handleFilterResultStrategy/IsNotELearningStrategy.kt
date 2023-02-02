package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.handleFilterResultStrategy

import android.view.View
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter.IsELearningState
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter.IsNotELearningState
import com.kunize.uswtimetable.util.TimeStringFormatter
import com.kunize.uswtimetable.util.strategy.FilterResultStrategy
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class IsNotELearningStrategy: FilterResultStrategy {
    override fun identifyFilterState(state: FilterState) = state is IsNotELearningState

    override suspend fun invoke(request: FilterState) {
        if(request is IsNotELearningState) {
            setTimeLocationView(request)
        }
    }

    private fun setTimeLocationView(request: IsNotELearningState) {
        with(request) {
            val timeListSplitByDay = TimeStringFormatter().splitTimeForClassInfo(time)
            for (i in timeListSplitByDay.indices) {
                setVisibilityTime(request, View.VISIBLE, i + 1)
                val tempSplit = timeListSplitByDay[i].split("(")
                locationList[i].setText(tempSplit[0])
                dayList[i].text = tempSplit[1][0].toString() + "요일"
                val onlyTime = tempSplit[1].substring(1).replace(")", "").split(",")
                val startTime = onlyTime[0]
                val endTime = onlyTime.last()
                startTimeList[i].setText(startTime)
                endTimeList[i].setText(endTime)
            }
        }
    }

    private fun setVisibilityTime(request: IsNotELearningState, set: Int, idx: Int) {
        with(request) {
            dayList[idx - 1].apply {
                text = "월요일"
                visibility = set
            }
            locationList[idx - 1].apply {
                setText("")
                visibility = set
            }
            startTimeList[idx - 1].apply {
                setText("")
                visibility = set
            }
            endTimeList[idx - 1].apply {
                setText("")
                visibility = set
            }
            deleteTimeList[idx - 1].visibility = set
            clDayList[idx - 1].visibility = set
            clTimeList[idx - 1].visibility = set
        }
    }
}