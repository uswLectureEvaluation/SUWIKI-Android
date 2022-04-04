package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.dataclass.EvaluationData
import com.kunize.uswtimetable.util.LAST_PAGE

open class BaseInfiniteRecyclerItemViewModel: BaseRecyclerItemViewModel() {
    val page = MutableLiveData<Int>()
    var lectureId: Long = 0

    init {
        page.value = 1
    }

    fun nextPage() {
        if (page.value == LAST_PAGE)
            return
        page.value = page.value?.plus(1)
        page.value = page.value
    }

    fun isLastData(tmpEvaluationData: ArrayList<EvaluationData?>) {
        if (tmpEvaluationData.size == 10)
            tmpEvaluationData.add(null)
        else
            page.value = LAST_PAGE
    }
}