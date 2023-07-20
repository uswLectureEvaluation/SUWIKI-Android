package com.kunize.uswtimetable.ui.lecture_info

import androidx.annotation.StringRes
import com.kunize.uswtimetable.R
import com.mangbaam.presentation.common.CommonRecyclerViewState
import com.suwiki.domain.model.EvaluationData
import com.suwiki.domain.model.LectureDetailInfo
import com.suwiki.domain.model.SuwikiError

data class LectureInfoState(
    val lectureInfo: LectureDetailInfo,
    val written: Boolean = false,
    val showNoExamInfo: Boolean = false,
    val showHideExamInfo: Boolean = false,
    val evaluationsState: CommonRecyclerViewState<EvaluationData> = CommonRecyclerViewState(),
    @StringRes val writeButtonText: Int = R.string.write_evaluation,
    val error: SuwikiError? = null,
)
