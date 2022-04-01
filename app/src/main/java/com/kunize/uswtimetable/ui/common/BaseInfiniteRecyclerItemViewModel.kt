package com.kunize.uswtimetable.ui.common

import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.util.LAST_PAGE

open class BaseInfiniteRecyclerItemViewModel: BaseRecyclerItemViewModel() {
    val page = MutableLiveData<Int>()
    val delayTime = 200L

    fun nextPage() {
        if (page.value == LAST_PAGE)
            return
        page.value = page.value?.plus(1)
        page.value = page.value
    }
}