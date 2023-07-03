package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.util.LAST_PAGE

class PageViewModel {
    val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page
    var lectureId: Long = 0

    init {
        _page.value = 1
    }

    fun nextPage() {
        if (_page.value == LAST_PAGE) {
            return
        }
        _page.value = _page.value?.plus(1)
        _page.value = _page.value
    }

    fun resetPage() {
        _page.value = 1
    }

    fun <T> isLastData(tmpEvaluationData: MutableList<T?>) {
        if (tmpEvaluationData.size == 10) {
            tmpEvaluationData.add(null)
        } else {
            _page.value = LAST_PAGE
        }
    }
}
