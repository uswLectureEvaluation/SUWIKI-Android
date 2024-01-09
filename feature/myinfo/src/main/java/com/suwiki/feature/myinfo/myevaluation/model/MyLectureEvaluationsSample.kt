package com.suwiki.feature.myinfo.myevaluation.model

import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import kotlinx.collections.immutable.persistentListOf

val MyLectureEvaluationsSample = persistentListOf(
  MyLectureEvaluation(
    selectedSemester = "2023-2",
    lectureInfo = LectureInfo(lectureName = "회로이론")
  ),
  MyLectureEvaluation(
    selectedSemester = "2022-1",
    lectureInfo = LectureInfo(lectureName = "신호 및 시스템")
  ),
  MyLectureEvaluation(
    selectedSemester = "2021-1",
    lectureInfo = LectureInfo(lectureName = "C언어")
  ),
)
