package com.suwiki.feature.timetable.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.timetable.TimetableScreen

fun NavController.navigateTimetable(navOptions: NavOptions) {
  navigate(TimetableRoute.route, navOptions)
}

fun NavGraphBuilder.timetableNavGraph(
  padding: PaddingValues,
) {
  composable(route = TimetableRoute.route) {
    TimetableScreen(padding)
  }
}

object TimetableRoute {
  const val route = "timetable"
}
