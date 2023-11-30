package com.suwiki.core.model.timetable

data class OpenLecture(
  val number: Long = 0, // 번호
  val major: String = "", // 개설학과
  val grade: String = "", // 개설학년
  val classNumber: String = "", // 교과목번호
  val classDivideNumber: String = "", // 분반
  val className: String = "", // 과목명
  val classification: String = "", // 이수 구분
  val professor: String = "",
  val time: String = "",
  val credit: String = "",
)
