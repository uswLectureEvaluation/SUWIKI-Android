package com.suwiki.feature.myinfo.myevaluation.model

import androidx.annotation.StringRes
import com.suwiki.feature.myinfo.R

enum class MyEvaluationTab(
  val position: Int,
  @StringRes val title: Int,
) {
  LECTURE_EVALUATION(
    position = 0,
    title = R.string.my_review_lecture_evaluation_tab,
  ),
  TEST_INFO(
    position = 1,
    title = R.string.my_review_test_evaluation_tab,
  ),
}
