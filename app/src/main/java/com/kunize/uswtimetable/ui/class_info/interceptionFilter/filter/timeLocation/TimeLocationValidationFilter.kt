package com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeLocation

import android.widget.TextView
import androidx.core.view.isVisible
import com.kunize.uswtimetable.data.local.TimeData
import com.kunize.uswtimetable.util.extensions.textToIntOrNull
import com.kunize.uswtimetable.util.interceptingFilter.Filter
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class TimeLocationValidationFilter : Filter {

    override fun execute(request: FilterRequest): FilterState {
        return if (request is TimeLocationValidationFilterRequest) checkAllTimeIsValid(request)
            else UnknownFilterFailState
    }

    private fun checkAllTimeIsValid(
        request: TimeLocationValidationFilterRequest
    ): FilterState {
        with(request) {
            for (idx in dayList.indices) {
                if (checkTimeIsValid(
                        deleteIdx = deleteIdx,
                        tempDeleteData = tempDeleteData,
                        tvDay = dayList[idx],
                        location = locationList[idx],
                        start = startTimeList[idx].textToIntOrNull(),
                        end = endTimeList[idx].textToIntOrNull()
                    ).not()
                ) return TimeLocationNotValidateState
            }

            return FilterState.Validate
        }
    }

    private fun checkTimeIsValid(
        deleteIdx: Int,
        tempDeleteData: TimeData,
        tvDay: TextView,
        location: TextView,
        start: Int?,
        end: Int?
    ): Boolean {
        if (tvDay.text == "없음") return true
        if (location.isVisible &&
            (location.text.toString().isBlank() || start == null || end == null
                    || (start > end
                    || (start !in 1..15)
                    || (end !in 1..15)))
        ) {
            //TODO 유효하지 않을 때 실행하는 코드
            return false
        }
        return true
    }
}