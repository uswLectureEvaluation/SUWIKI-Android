package com.suwiki.feature.lectureevaluation.viewerreporter.model

import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import kotlinx.collections.immutable.toPersistentList

data class LectureEvaluation(
  val id: Long,
  val lectureName: String,
  val lectureType: String?,
  val majorType: String,
  val professor: String,
  val lectureTotalAvg: Float,
  val reviewCount: Int?,
)

fun List<LectureEvaluationAverage?>.toLectureEvaluation(
  searchValue: String,
) =
  filter {
  if (searchValue.isNotEmpty()) {
    searchValue in it!!.lectureInfo.lectureName
    searchValue in it.lectureInfo.professor
  }
  else {
    true
  }
}.
  map {
  LectureEvaluation(
    id = it!!.id,
    lectureName = it.lectureInfo.lectureName,
    lectureType = it.lectureInfo.lectureType,
    majorType = it.lectureInfo.majorType,
    professor = it.lectureInfo.professor,
    lectureTotalAvg = it.lectureTotalAvg,
    reviewCount = null
  )
}.toPersistentList()

