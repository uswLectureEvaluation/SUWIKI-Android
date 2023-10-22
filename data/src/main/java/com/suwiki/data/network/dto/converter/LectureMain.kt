package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.LectureMainDto
import com.suwiki.domain.model.LectureMain

fun LectureMainDto.toDomain(): LectureMain {
  return LectureMain(
    id = id,
    semester = semester,
    professor = professor,
    majorType = majorType,
    lectureType = lectureType,
    lectureName = lectureName,
    lectureTotalAvg = lectureTotalAvg,
    lectureSatisfactionAvg = lectureSatisfactionAvg,
    lectureHoneyAvg = lectureHoneyAvg,
    lectureLearningAvg = lectureLearningAvg,
  )
}
