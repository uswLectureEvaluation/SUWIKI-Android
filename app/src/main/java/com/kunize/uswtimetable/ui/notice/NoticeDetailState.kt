package com.kunize.uswtimetable.ui.notice

import com.kunize.uswtimetable.ui.common.UiState
import com.suwiki.domain.model.Notice
import com.suwiki.domain.model.SuwikiError

data class NoticeDetailState(
    val loading: Boolean = false,
    val notice: Notice? = null,
    val error: SuwikiError? = null,
) : UiState
