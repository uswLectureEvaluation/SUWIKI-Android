package com.kunize.uswtimetable.dataclass

object LectureItemViewType {
    const val SHORT = 0 //강의평가 목록
    const val USERLECTURE = 1 //내가 쓴 강의평가
    const val USEREXAM = 2 //내가 쓴 시험정보
    const val LECTURE = 3 //세부 강의평가 정보
    const val EXAM = 4 //세부 시험정보
}

object ExamInfoType {
    const val NO_DATA = 0 //데이터 없음
    const val NEED_USE = 1 //포인트 사용 필요
    const val NOT_INFLATE = 2 //포인트 이미 사용 OR 강의평가 쓰기
}