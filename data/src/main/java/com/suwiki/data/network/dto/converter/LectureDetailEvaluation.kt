package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.LectureDetailEvaluationDataDto
import com.suwiki.data.network.dto.LectureDetailEvaluationDto
import com.suwiki.domain.model.EvaluationData
import com.suwiki.domain.model.LectureDetailEvaluationData

fun LectureDetailEvaluationDataDto.toDomain(): LectureDetailEvaluationData {
  return LectureDetailEvaluationData(
    data = data.map { it.toDomain() },
    written = written,
  )
}

fun LectureDetailEvaluationDto.toDomain(): EvaluationData {
  return EvaluationData(
    recyclerViewType = 3,
    lectureId = id,
    yearSemester = semester,
    aver = totalAvg,
    satisfaction = satisfaction,
    learning = learning,
    honey = honey,
    teamMeeting = team,
    grade = difficulty,
    task = homework,
    content = content,
  )
}
