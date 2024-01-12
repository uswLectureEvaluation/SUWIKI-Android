package com.suwiki.feature.timetable.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.timetable.createtimetable.CreateTimetableRoute
import com.suwiki.feature.timetable.timetable.TimetableRoute

fun NavController.navigateTimetable(navOptions: NavOptions) {
  navigate(TimetableRoute.route, navOptions)
}

fun NavController.navigateCreateTimetable() {
  navigate(TimetableRoute.createRoute)
}

fun NavGraphBuilder.timetableNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit,
  navigateCreateTimetable: () -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
) {
  composable(route = TimetableRoute.route) {
    TimetableRoute(
      padding = padding,
      navigateCreateTimetable = navigateCreateTimetable,
    )
  }

  composable(route = TimetableRoute.createRoute) {
    CreateTimetableRoute(
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
    )
  }
}

object TimetableRoute {
  const val route = "timetable"
  const val createRoute = "$route/create"
}
