package com.suwiki.feature.myinfo.myreview.model

import androidx.annotation.StringRes
import com.suwiki.feature.myinfo.R

enum class MyReviewTab(
  val position: Int,
  @StringRes val title: Int,
) {
  LECTUREEVALUATION(
    position = 0,
    title = R.string.my_review_lecture_evaluation_tab,
  ),
  TESTINFO(
    position = 1,
    title = R.string.my_review_test_evaluation_tab,
  ),
}
