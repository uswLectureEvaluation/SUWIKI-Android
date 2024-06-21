package com.suwiki.presentation.common.ui.enums

import androidx.annotation.StringRes
import com.suwiki.presentation.common.ui.R

enum class LectureEvaluationTab(
  val position: Int,
  @StringRes val title: Int,
) {
  LECTURE_EVALUATION(
    position = 0,
    title = R.string.word_lecture_evaluation,
  ),
  EXAM_INFO(
    position = 1,
    title = R.string.word_exam_info,
  ),
}
