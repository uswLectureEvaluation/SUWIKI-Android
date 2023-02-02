package com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.elearning

import com.kunize.uswtimetable.util.interceptingFilter.Filter
import com.kunize.uswtimetable.util.interceptingFilter.FilterRequest
import com.kunize.uswtimetable.util.interceptingFilter.FilterState
import com.kunize.uswtimetable.util.interceptingFilter.UnknownFilterFail

class ELearningValidationFilter : Filter {

    override fun execute(request: FilterRequest): FilterState {
        return if (request is ELearningValidationFilterRequest) checkELearningValidation(request)
        else UnknownFilterFail
    }

    private fun checkELearningValidation(
        request: ELearningValidationFilterRequest
    ): FilterState {
        with(request) {
            for (newTime in timeDataTobeAdded) {
                if ((newTime.day.contains("토") || newTime.location.contains("이러닝") || newTime.location.isEmpty()) &&
                    currentTimeTable.find { it.location == "이러닝" || it.day == "토" } != null
                ) {
                    return ELearningNotValidate
                }
            }
            return FilterState.Validate
        }
    }
}