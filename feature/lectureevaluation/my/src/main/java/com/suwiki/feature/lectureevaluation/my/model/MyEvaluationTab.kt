package com.suwiki.feature.lectureevaluation.my.model

import androidx.annotation.StringRes
import com.suwiki.feature.lectureevaluation.my.R

enum class MyEvaluationTab(
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
