package com.suwiki.feature.timetable.addcell.model

import androidx.annotation.StringRes
import com.suwiki.feature.timetable.R

enum class SchoolLevel(
  val query: Int?,
  @StringRes val stringResId: Int,
) {
  ALL(
    query = null,
    stringResId = R.string.word_all,
  ),
  ONE(
    query = 1,
    stringResId = R.string.all_timetable_cell_screen_grade_1,
  ),
  TWO(
    query = 2,
    stringResId = R.string.all_timetable_cell_screen_grade_2,
  ),
  THREE(
    query = 3,
    stringResId = R.string.all_timetable_cell_screen_grade_3,
  ),
  FOUR(
    query = 4,
    stringResId = R.string.all_timetable_cell_screen_grade_4,
  ),
}
