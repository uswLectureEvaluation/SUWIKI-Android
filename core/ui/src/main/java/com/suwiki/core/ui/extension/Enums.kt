package com.suwiki.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.LectureAlign
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.core.ui.R
import kotlinx.collections.immutable.toPersistentList

@Composable
fun LectureAlign.toText() = when (this) {
  LectureAlign.RECENT -> stringResource(id = R.string.lecture_align_recent)
  LectureAlign.HONEY -> stringResource(id = R.string.lecture_align_honey)
  LectureAlign.SATISFACTION -> stringResource(id = R.string.lecture_align_satisfaction)
  LectureAlign.LEARNING -> stringResource(id = R.string.lecture_align_learning)
  LectureAlign.BEST -> stringResource(id = R.string.lecture_align_best)
}

val lectureAlignList
  @Composable
  get() = LectureAlign.entries.map { it.toText() }.toPersistentList()

@Composable
fun GradeLevel.toText() = when(this) {
  GradeLevel.EASY -> stringResource(R.string.word_grade_easy)
  GradeLevel.NORMAL -> stringResource(R.string.word_grade_normal)
  GradeLevel.DIFFICULT -> stringResource(R.string.word_grade_difficulty)
}

@Composable
fun HomeworkLevel.toText() = when(this) {
  HomeworkLevel.NONE -> stringResource(R.string.word_homework_not_exist)
  HomeworkLevel.NORMAL -> stringResource(R.string.word_homework_normal)
  HomeworkLevel.MANY -> stringResource(R.string.word_homework_many)
}

@Composable
fun TeamLevel.toText() = when(this) {
  TeamLevel.NOT_EXIST -> stringResource(R.string.word_team_not_exist)
  TeamLevel.EXIST -> stringResource(R.string.word_team_exist)
}
