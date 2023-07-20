package com.kunize.uswtimetable.ui.notice

import com.kunize.uswtimetable.ui.common.UiState
import com.suwiki.domain.model.SimpleNotice
import com.suwiki.domain.model.SuwikiError

data class NoticeListState(
    val page: Int = 1,
    val loadFinished: Boolean = false,
    val loading: Boolean = false,
    val data: List<SimpleNotice> = emptyList(),
    val error: SuwikiError? = null,
) : UiState
