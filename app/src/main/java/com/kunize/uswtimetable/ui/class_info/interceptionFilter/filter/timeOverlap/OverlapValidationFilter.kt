package com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeOverlap

import com.kunize.uswtimetable.util.interceptingFilter.Filter
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class OverlapValidationFilter : Filter {

    override fun invoke(request: FilterRequest): FilterState {
        return if (request is OverlapValidationFilterRequest) checkOverlapTime(request)
        else UnknownFilterFailState
    }

    private fun checkOverlapTime(
        request: OverlapValidationFilterRequest
    ): FilterState {
        with(request) {
            for (newTime in timeDataTobeAdded) {
                for (oldTime in currentTimeTable) {
                    if (newTime.day != oldTime.day) continue

                    val newStartTime = newTime.startTime.toInt()
                    val newEndTime = newTime.endTime.toInt()
                    val oldStartTime = oldTime.startTime.toInt()
                    val oldEndTime = oldTime.endTime.toInt()

                    if (
                        (newStartTime in oldStartTime..oldEndTime) || (newEndTime in oldStartTime..oldEndTime) ||
                        (oldStartTime in newStartTime..newEndTime) || (oldEndTime in newStartTime..newEndTime)
                    ) return OverlapNotValidateState(oldTime)
                }
            }
            return FilterState.Validate
        }
    }
}