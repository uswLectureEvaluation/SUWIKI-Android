package com.kunize.uswtimetable.ui.class_info.interceptionFilter.handleFilterResultStrategy

import android.widget.Toast
import com.kunize.uswtimetable.ui.class_info.interceptionFilter.filter.elearning.ELearningNotValidate
import com.kunize.uswtimetable.util.SuwikiApplication
import com.kunize.uswtimetable.util.interceptingFilter.FilterState

class ELearningNotValidationFilterResult: FilterResultStrategy {
    override fun identifyFilterState(filter: FilterState): Boolean = filter is ELearningNotValidate

    override fun invoke() {
        Toast.makeText(
            SuwikiApplication.instance,
            "토요일이거나 시간이 없는 과목은 하나만 추가 가능합니다!",
            Toast.LENGTH_LONG
        ).show()
    }
}