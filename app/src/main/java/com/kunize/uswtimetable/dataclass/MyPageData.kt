package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName

data class MyPageData(
    val loginId: String,    // 유저 id
    val point: Int,         // 유저가 가진 현재 포인트
    @SerializedName("written_evaluation") val writtenEvaluation: Int, // 작성한 강의 평가 개수
    @SerializedName("written_exam") val writtenExam: Int,   // 작성한 시험 정보 개수
    @SerializedName("view_exam") val viewExam: Int  // 조회한 시험 정보 개수
)
