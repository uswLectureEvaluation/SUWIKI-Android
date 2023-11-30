package com.suwiki.core.model.user

const val DEFAULT_USER_ID = ""
const val DEFAULT_USER_EMAIL = ""
const val DEFAULT_USER_POINT = 0
const val DEFAULT_USER_WRITTEN_EVALUATION = 0
const val DEFAULT_USER_WRITTEN_EXAM = 0
const val DEFAULT_USER_VIEW_EXAM = 0

data class User(
  val userId: String = DEFAULT_USER_ID,
  val email: String = DEFAULT_USER_EMAIL,
  val point: Int = DEFAULT_USER_POINT,
  val writtenEvaluation: Int = DEFAULT_USER_WRITTEN_EVALUATION,
  val writtenExam: Int = DEFAULT_USER_WRITTEN_EXAM,
  val viewExam: Int = DEFAULT_USER_VIEW_EXAM,
) {
  val isLoggedIn: Boolean
    get() = this != User()
}
