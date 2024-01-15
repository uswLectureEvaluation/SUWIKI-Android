package com.suwiki.feature.timetable.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.ui.extension.encodeToUri
import com.suwiki.feature.timetable.addcell.AddCellRoute
import com.suwiki.feature.timetable.createtimetable.CreateTimetableRoute
import com.suwiki.feature.timetable.openlecture.OpenLectureRoute
import com.suwiki.feature.timetable.timetable.TimetableRoute
import kotlinx.serialization.json.Json

fun NavController.navigateTimetable(navOptions: NavOptions) {
  navigate(TimetableRoute.route, navOptions)
}

fun NavController.navigateCreateTimetable() {
  navigate(TimetableRoute.createRoute)
}

fun NavController.navigateOpenLecture() {
  navigate(TimetableRoute.openLecture)
}

fun NavController.navigateAddCell(openLecture: OpenLecture = OpenLecture()) {
  navigate(TimetableRoute.addCellRoute(Json.encodeToUri(openLecture)))
}

fun NavGraphBuilder.timetableNavGraph(
  padding: PaddingValues,
  argumentName: String,
  popBackStack: () -> Unit,
  navigateOpenMajor: (String) -> Unit,
  navigateCreateTimetable: () -> Unit,
  navigateOpenLecture: () -> Unit,
  navigateAddCell: (OpenLecture) -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
) {
  composable(route = TimetableRoute.route) {
    TimetableRoute(
      padding = padding,
      navigateCreateTimetable = navigateCreateTimetable,
      handleException = handleException,
      onShowToast = onShowToast,
      navigateOpenLecture = navigateOpenLecture,
    )
  }

  composable(route = TimetableRoute.createRoute) {
    CreateTimetableRoute(
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
    )
  }

  composable(route = TimetableRoute.openLecture) { navBackStackEntry ->
    val selectedOpenMajor = navBackStackEntry.savedStateHandle.get<String>(argumentName) ?: "전체"

    OpenLectureRoute(
      selectedOpenMajor = selectedOpenMajor,
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
      navigateOpenMajor = navigateOpenMajor,
      navigateAddCell = navigateAddCell,
    )
  }

  composable(
    route = TimetableRoute.addCellRoute("{${TimetableRoute.ADD_CELL_ARGUMENT}}"),
    arguments = listOf(
      navArgument(TimetableRoute.ADD_CELL_ARGUMENT) {
        type = NavType.StringType
      },
    ),
  ) {
    AddCellRoute(
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
    )
  }
}

object TimetableRoute {
  const val route = "timetable"
  const val createRoute = "$route/create"
  const val openLecture = "open-lecture"
  const val ADD_CELL_ARGUMENT = "open-lecture"

  fun addCellRoute(openLecture: String) = "add-cell/$openLecture"
}
