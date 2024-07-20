package com.suwiki.presentation.navigator

import com.suwiki.presentation.lectureevaluation.navigation.LectureEvaluationRoute
import com.suwiki.presentation.myinfo.navigation.MyInfoRoute
import com.suwiki.presentation.timetable.navigation.TimetableRoute

internal enum class MainTab(
  val iconResId: Int,
  internal val contentDescription: String,
  val route: String,
) {
  TIMETABLE(
    iconResId = R.drawable.ic_bottom_navigation_timetable,
    contentDescription = "시간표",
    route = TimetableRoute.route,
  ),
  LECTURE_EVALUATION(
    iconResId = R.drawable.ic_bottom_navigation_evaluation,
    contentDescription = "강의평가",
    route = LectureEvaluationRoute.route,
  ),
  MY_INFO(
    iconResId = R.drawable.ic_bottom_navigation_myinfo,
    contentDescription = "내 정보",
    route = MyInfoRoute.route,
  ),
  ;

  companion object {
    operator fun contains(route: String): Boolean {
      return entries.map { it.route }.contains(route)
    }

    fun find(route: String): MainTab? {
      return entries.find { it.route == route }
    }
  }
}
