package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.MyEvaluationDto
import com.suwiki.domain.model.MyEvaluation

fun MyEvaluationDto.toDomain(): MyEvaluation {
  return MyEvaluation(
    id,
    lectureName,
    professor,
    majorType,
    selectedSemester,
    semesterList,
    totalAvg,
    satisfaction,
    learning,
    honey,
    team,
    difficulty,
    homework,
    content,
  )
}
