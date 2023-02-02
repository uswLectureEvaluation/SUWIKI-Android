package com.kunize.uswtimetable.ui.class_info.filter.timeOverlap

import com.kunize.uswtimetable.ui.class_info.filter.timeVisible.VisibleNotValidate
import com.kunize.uswtimetable.ui.class_info.filter.timeVisible.VisibleValidationFilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.Filter
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFail

class OverlapValidationFilter : Filter {

    override fun execute(request: FilterRequest): FilterState {
        return if (request is VisibleValidationFilterRequest) checkOverlapTime(request)
        else UnknownFilterFail
    }

    private fun checkOverlapTime(
        request: VisibleValidationFilterRequest
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
                    ) return VisibleNotValidate(oldTime)
                }
            }
            return FilterState.Validate
        }
    }
}