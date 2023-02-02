package com.kunize.uswtimetable.ui.class_info.filter.timeVisible

import android.view.View
import com.kunize.uswtimetable.util.interceptingFilter.Filter
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFail

class VisibleValidationFilter : Filter {

    override fun execute(request: FilterRequest): FilterState {
        return if (request is VisibleValidationFilterRequest) checkVisible(request)
        else UnknownFilterFail
    }

    private fun checkVisible(
        request: VisibleValidationFilterRequest
    ): FilterState {
        with(request) {
            for(deleteTime in deleteTimeList) {
                if(deleteTime.visibility != View.VISIBLE) return VisibleNotValidate
            }
            return FilterState.Validate
        }
    }
}