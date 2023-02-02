package com.kunize.uswtimetable.ui.class_info.interceptionFilter.initELearning.filter

import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.kunize.uswtimetable.util.interceptingFilter.Filter
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class CheckELearningFilter() : Filter {

    private fun modifiedTimeIsELearning(time: String) = time == "" || time == "()" || time == "None"
    private fun notModifiedTimeIsELearning(time: String) = time == "None"

    override fun invoke(request: FilterRequest): FilterState {
        return if (request is CheckELearningFilterRequest) checkELearning(request)
        else UnknownFilterFailState
    }

    private fun checkELearning(
        request: CheckELearningFilterRequest
    ): FilterState {
        with(request) {
            val isELearningState = IsELearningState(
                location1 = location1,
                day1 = day1
            )

            val isNotELearningState = IsNotELearningState(
                time = time,
                locationList = locationList,
                dayList = dayList,
                startTimeList = startTimeList,
                endTimeList = endTimeList,
                deleteTimeList = deleteTimeList,
                clTimeList = clTimeList,
                clDayList = clDayList
            )
            return when (isModifiedMode) {
                true -> {
                    if (modifiedTimeIsELearning(time)) isELearningState else isNotELearningState
                }

                else -> {
                    if (notModifiedTimeIsELearning(time)) isELearningState else isNotELearningState
                }
            }
        }
    }
}
