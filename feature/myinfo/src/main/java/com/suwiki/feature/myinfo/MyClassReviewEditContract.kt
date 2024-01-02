package com.suwiki.feature.myinfo

data class MyClassReviewEditState(
  val isLoading: Boolean = false,
  val semester: String = "",
  val honeyRating: Float = 5f,
  val learningRating: Float = 0f,
  val satisfactionRating: Float = 0f,
  val gradeGenerousChecked: Boolean = false,
  val gradeNormalChecked: Boolean = false,
  val gradePickyChecked: Boolean = false,
  val homeworkNoneChecked: Boolean = false,
  val homeworkNormalChecked: Boolean = false,
  val homeworkMuchChecked: Boolean = false,
  val teamNoneChecked: Boolean = false,
  val teamExistChecked: Boolean = false,
  val classReview: String = "",
  val showDeleteClassReviewDialog: Boolean = false,
)

sealed interface MyClassReviewEditSideEffect {
  data object PopBackStack : MyClassReviewEditSideEffect
}
