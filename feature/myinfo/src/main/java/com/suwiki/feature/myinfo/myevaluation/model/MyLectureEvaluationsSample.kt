package com.suwiki.feature.myinfo.myevaluation.model

import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import kotlinx.collections.immutable.persistentListOf

val MyLectureEvaluationsSample = persistentListOf(
  MyLectureEvaluation(
    lectureInfo = LectureInfo(lectureName = "회로이론"),
    selectedSemester = "2023-2",
    totalAvg = 2.5f,
    satisfaction = 2.5f,
    learning = 0.5f,
    honey = 3.5f,
    team = 0,
    difficulty = 0,
    homework = 1,
    content = "샘플 데이터",
  ),
  MyLectureEvaluation(
    lectureInfo = LectureInfo(lectureName = "신호 및 시스템"),
    selectedSemester = "2022-1",
    totalAvg = 2.5f,
    satisfaction = 2.5f,
    learning = 0.5f,
    honey = 3.5f,
    team = 0,
    difficulty = 0,
    homework = 1,
    content = "샘플 데이터",
  ),
  MyLectureEvaluation(
    lectureInfo = LectureInfo(lectureName = "C언어"),
    selectedSemester = "2021-1",
    totalAvg = 2.5f,
    satisfaction = 2.5f,
    learning = 4.5f,
    honey = 1.5f,
    team = 0,
    difficulty = 0,
    homework = 1,
    content = "샘플 데이터",
  ),
)
