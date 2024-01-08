package com.suwiki.core.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.suwiki.core.model.enums.LectureAlign
import com.suwiki.core.ui.R
import kotlinx.collections.immutable.toPersistentList

@Composable
fun LectureAlign.toText() = when(this) {
  LectureAlign.RECENT -> stringResource(id = R.string.lecture_align_recent)
  LectureAlign.HONEY -> stringResource(id = R.string.lecture_align_honey)
  LectureAlign.SATISFACTION -> stringResource(id = R.string.lecture_align_satisfaction)
  LectureAlign.LEARNING -> stringResource(id = R.string.lecture_align_learning)
  LectureAlign.BEST -> stringResource(id = R.string.lecture_align_best)
}

val lectureAlignList
  @Composable
  get() = LectureAlign.entries.map { it.toText() }.toPersistentList()
