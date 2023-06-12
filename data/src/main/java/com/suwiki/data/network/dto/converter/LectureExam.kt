package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.LectureExamDto
import com.suwiki.domain.model.LectureExam

fun LectureExamDto.toDomain(): LectureExam {
    return LectureExam(
        id,
        lectureName,
        professor,
        majorType,
        selectedSemester,
        semesterList,
        examInfo,
        examType,
        examDifficulty,
        content,
    )
}
