package com.suwiki.feature.myinfo.myreview

import androidx.annotation.StringRes
import com.suwiki.feature.myinfo.R

enum class MyReviewTab(
  val position: Int,
  @StringRes val title: Int,
) {
  LECTUREEVALUATION(
    position = 0,
    title = R.string.my_class_review_lecture_evaluation,
  ),
  TESTINFO(
    position = 1,
    title = R.string.my_test_review_info,
  ),
}
