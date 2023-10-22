package com.suwiki.domain.model

data class TimetableData(
  val number: Long = 0, // 번호
  val major: String = "", // 개설학과
  val grade: String = "", // 개설학년
  val classNumber: String = "", // 교과목번호
  val classDivideNumber: String = "", // 분반
  val className: String = "", // 과목명
  val classification: String = "", // 이수 구분
  val professor: String = "", // 교수님 성함
  val time: String = "", // 장소,시간,요일 split ')' -> 장소 2군데 걸러줌  , ' ' -> 요일 2군데 걸러줌 ,
  // 교시를 다 읽어온 후 12345... 순서로 증가하는지 확인
  val credit: String = "",
)
