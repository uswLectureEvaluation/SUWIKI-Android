package com.kunize.uswtimetable.dataclass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyEvaluationListDto(
    val data: List<MyEvaluationDto>
)

data class MyExamInfoListDto(
    val data: List<MyExamInfoDto>
)

data class MyEvaluationDto(
    val id: Long,
    val lectureName: String, //과목이름
    val professor: String, //교수이름
    val selectedSemester: String,
    val semesterList: String,
    val totalAvg: Float, //총점
    val satisfaction: Float, //만족도
    val learning: Float, //배움지수
    val honey: Float, //꿀강지수
    val team: Int, //조별모임 유무(없음 == 0, 있음 == 1)
    val difficulty: Int, //학점 잘주는가? (까다로움 == 0, 보통 == 1, 학점느님 ==2)
    val homework: Int, //과제양 (없음 ==0, 보통 == 1, 많음 == 2)
    val content: String
) : Serializable

data class MyEvaluationEditDto(
    val semester: String,
    val satisfaction: Float,
    val learning: Float,
    val honey: Float,
    val team: Int,
    val difficulty: Int,
    val homework: Int,
    val content: String
)

data class MyExamInfoDto(
    val id: Long,
    val lectureName: String, //과목 이름
    val professor: String, //교수이름
    @SerializedName("selectedSemester") val semester: String,
    val semesterList: String,
    val examInfo: String, //시험 방식
    val examDifficulty: String, //시험 난이도
    val content: String
) : Serializable {
    fun toMyExamInfo() =
        MyExamInfo(
            id = this.id,
            subject = this.lectureName,
            semester = this.semester,
            semesterList = this.semesterList,
            professor = this.professor,
            examType = this.examInfo,
            examDifficulty = this.examDifficulty,
            content = this.content
        )
}

data class MyExamInfoEditDto(
    val semester: String,
    val examInfo: String,
    val examDifficulty: String,
    val content: String,
): Serializable

data class MyExamInfo(
    val id: Long,
    val subject: String,
    val semester: String,
    val semesterList: String,
    val professor: String,
    val examType: String,
    val examDifficulty: String,
    val content: String,
): Serializable