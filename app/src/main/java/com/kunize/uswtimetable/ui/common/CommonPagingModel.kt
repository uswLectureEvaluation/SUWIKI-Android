package com.kunize.uswtimetable.ui.common

import com.kunize.uswtimetable.util.LAST_PAGE

class CommonPagingModel {
    private var _page = DEFAULT_PAGE
    val page: Int
        get() = _page

    fun nextPage() {
        if (page == LAST_PAGE) return
        _page++
    }

    fun resetPage() {
        _page = DEFAULT_PAGE
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}
