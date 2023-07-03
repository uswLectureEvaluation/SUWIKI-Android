package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.LectureDetailExamDataDto
import com.suwiki.data.network.dto.LectureDetailExamDto
import com.suwiki.domain.model.EvaluationData
import com.suwiki.domain.model.LectureDetailExamData

fun LectureDetailExamDataDto.toDomain(): LectureDetailExamData {
    return LectureDetailExamData(
        data = data.map { it.toDomain() },
        examDataExist = examDataExist,
        written = written,
    )
}

fun LectureDetailExamDto.toDomain(): EvaluationData {
    return EvaluationData(
        recyclerViewType = 4,
        lectureId = id,
        yearSemester = semester,
        testMethod = examInfo,
        examType = examType,
        difficulty = examDifficulty,
        content = content,
    )
}
