package com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.timeVisible

import android.view.View
import com.kunize.uswtimetable.util.interceptingFilter.Filter
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFailState

class VisibleValidationFilter : Filter {

    override fun invoke(request: FilterRequest): FilterState {
        return if (request is VisibleValidationFilterRequest) checkVisible(request)
        else UnknownFilterFailState
    }

    private fun checkVisible(
        request: VisibleValidationFilterRequest
    ): FilterState {
        with(request) {
            for(deleteTime in deleteTimeList) {
                if(deleteTime.visibility == View.VISIBLE) return FilterState.Validate
            }
            return VisibleNotValidateState
        }
    }
}