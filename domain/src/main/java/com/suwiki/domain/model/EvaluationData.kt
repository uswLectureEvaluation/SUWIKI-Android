package com.suwiki.domain.model

data class EvaluationData(
    var recyclerViewType: Int = -1, // viewType
    var name: String = "", // 과목명
    var professor: String = "", // 교수
    var aver: Float = 0f, // 평균 지수
    var satisfaction: Float = 0f, // 만족도
    var honey: Float = 0f, // 꿀강 지수
    var learning: Float = 0f, // 배움 지수
    var type: String = "", // 이수 구분(기교, 전핵 ...)
    var yearSemester: String = "", // 수강 년도 학기
    var teamMeeting: Int = 0, // 조모임
    var task: Int = 0, // 과제
    var grade: Int = 0, // 학점
    var content: String? = "내용이 없습니다.", // 내용
    var difficulty: String = "", // 시험 난이도
    var testMethod: String = "", // 시험 방식
    var lectureId: Long = 0,
    var majorType: String = "",
    val examType: String? = "",
)
