package com.kunize.uswtimetable.dataclass

data class EvaluationData (
    var name: String = "", //과목명
    var professor: String = "", //교수
    var aver: Float = 0f, //평균 지수
    var satisfaction: Float = 0f, //만족도
    var honey: Float = 0f, //꿀강 지수
    var learning: Float = 0f, //배움 지수
    var type: String = "", //이수 구분(기교, 전핵 ...)
    var yearSemester: String = "", //수강 년도 학기
    var teamMeeting: String = "", //조모임
    var task: String = "", //과제
    var grade: String = "", //학점
    var content: String = "내용이 없습니다." //내용
)