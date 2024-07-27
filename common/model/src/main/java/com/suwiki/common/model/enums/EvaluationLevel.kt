package com.suwiki.common.model.enums

import androidx.compose.runtime.Stable

@Stable
enum class GradeLevel(val value: Int) {
  EASY(0),
  NORMAL(1),
  DIFFICULT(2),
  ;

  companion object {
    fun valueOf(value: Int): GradeLevel = when (value) {
      0 -> EASY
      1 -> NORMAL
      2 -> DIFFICULT
      else -> throw IllegalArgumentException("존재하지 않는 GradeLevel 입니다.")
    }
  }
}

@Stable
enum class HomeworkLevel(val value: Int) {
  NONE(0),
  NORMAL(1),
  MANY(2),
  ;

  companion object {
    fun valueOf(value: Int): HomeworkLevel = when (value) {
      0 -> NONE
      1 -> NORMAL
      2 -> MANY
      else -> throw IllegalArgumentException("존재하지 않는 HomeworkLevel 입니다.")
    }
  }
}

@Stable
enum class TeamLevel(val value: Int) {
  NOT_EXIST(0),
  EXIST(1),
  ;

  companion object {
    fun valueOf(value: Int): TeamLevel = when (value) {
      0 -> NOT_EXIST
      1 -> EXIST
      else -> throw IllegalArgumentException("존재하지 않는 TeamLevel 입니다.")
    }
  }
}

// TODO api v2 리팩토링 필요
@Stable
enum class ExamLevel(val value: String) {
  EASY("쉬움"),
  NORMAL("보통"),
  DIFFICULT("어려움"),
}

// TODO api v2 리팩토링 필요
@Stable
enum class ExamInfo(val value: String) {
  FAMILY_TREE("족보"),
  TEXTBOOK("교재"),
  PPT("PPT"),
  WRITING("필기"),
  APPLY("응용"),
  PRACTICE("실습"),
  HOMEWORK("과제"),
}

@Stable
enum class ExamType(val value: String) {
  MID_EXAM("중간고사"),
  FINAL_EXAM("기말고사"),
  NOTE_EXAM("쪽지"),
  ETC_EXAM("기타"),
}
