package com.suwiki.feature.timetable.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.timetable.addcell.AddTimetableCellRoute
import com.suwiki.feature.timetable.createtimetable.CreateTimetableRoute
import com.suwiki.feature.timetable.timetable.TimetableRoute

fun NavController.navigateTimetable(navOptions: NavOptions) {
  navigate(TimetableRoute.route, navOptions)
}

fun NavController.navigateCreateTimetable() {
  navigate(TimetableRoute.createRoute)
}

fun NavController.navigateAddTimetableCell() {
  navigate(TimetableRoute.addCellRoute)
}

fun NavGraphBuilder.timetableNavGraph(
  padding: PaddingValues,
  argumentName: String,
  popBackStack: () -> Unit,
  navigateOpenMajor: (String) -> Unit,
  navigateCreateTimetable: () -> Unit,
  navigateAddTimetableCell: () -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
) {
  composable(route = TimetableRoute.route) {
    TimetableRoute(
      padding = padding,
      navigateCreateTimetable = navigateCreateTimetable,
      handleException = handleException,
      onShowToast = onShowToast,
      navigateAddTimetableCell = navigateAddTimetableCell,
    )
  }

  composable(route = TimetableRoute.createRoute) {
    CreateTimetableRoute(
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
    )
  }

  composable(route = TimetableRoute.addCellRoute) { navBackStackEntry ->
    val selectedOpenMajor = navBackStackEntry.savedStateHandle.get<String>(argumentName) ?: "전체"

    AddTimetableCellRoute(
      selectedOpenMajor = selectedOpenMajor,
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
      navigateOpenMajor = navigateOpenMajor,
    )
  }
}

object TimetableRoute {
  const val route = "timetable"
  const val createRoute = "$route/create"
  const val addCellRoute = "$route/add-cell"
}
