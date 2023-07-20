package com.kunize.uswtimetable.ui.evaluation

import com.mangbaam.presentation.common.CommonRecyclerViewState
import com.suwiki.domain.model.LectureMain

data class EvaluationState(
    val selectedIndex: Int = 0,
    val evaluationSortBy: EvaluationSortBy = EvaluationSortBy.Recent,
    val lectureMainsState: CommonRecyclerViewState<LectureMain> = CommonRecyclerViewState(),
)
