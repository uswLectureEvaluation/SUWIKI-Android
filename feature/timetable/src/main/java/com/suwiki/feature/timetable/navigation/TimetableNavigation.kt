package com.suwiki.feature.timetable.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.core.model.timetable.Cell
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.extension.encodeToUri
import com.suwiki.feature.timetable.celleditor.CellEditorRoute
import com.suwiki.feature.timetable.createtimetable.CreateTimetableRoute
import com.suwiki.feature.timetable.openlecture.OpenLectureRoute
import com.suwiki.feature.timetable.timetable.TimetableRoute
import kotlinx.serialization.Serializable
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

fun NavController.navigateCellEditor(argument: CellEditorArgument = CellEditorArgument()) {
  navigate(TimetableRoute.cellEditorRoute(Json.encodeToUri(argument)))
}

fun NavGraphBuilder.timetableNavGraph(
  padding: PaddingValues,
  argumentName: String,
  popBackStack: () -> Unit,
  navigateOpenMajor: (String) -> Unit,
  navigateCreateTimetable: () -> Unit,
  navigateOpenLecture: () -> Unit,
  navigateCellEditor: (CellEditorArgument) -> Unit,
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
      navigateCellEditor = navigateCellEditor,
    )
  }

  composable(
    route = TimetableRoute.cellEditorRoute("{${TimetableRoute.CELL_EDITOR_ARGUMENT}}"),
    arguments = listOf(
      navArgument(TimetableRoute.CELL_EDITOR_ARGUMENT) {
        type = NavType.StringType
        nullable = true
      },
    ),
  ) {
    CellEditorRoute(
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
  const val CELL_EDITOR_ARGUMENT = "cell-editor-argument"

  fun cellEditorRoute(openLecture: String) = "cell-editor/$openLecture"
}

@Serializable
data class CellEditorArgument(
  val id: String = "",
  val name: String = "",
  val professorName: String = "",
  val cellList: List<CellArgument> = emptyList(),
  val timetableCellColor: TimetableCellColor = TimetableCellColor.entries.shuffled().first(),
) {
  val isEditMode = id.isNotEmpty()
}

@Serializable
data class CellArgument(
  val location: String = "",
  val day: TimetableDay = TimetableDay.MON,
  val startPeriod: String = "",
  val endPeriod: String = "",
)

internal fun OpenLecture.toCellEditorArgument() = CellEditorArgument(
  name = this.name,
  professorName = this.professorName,
  cellList = this.originalCellList.map { it.toCellArgument() },
)

internal fun Cell.toCellArgument() = CellArgument(
  location = location,
  day = day,
  startPeriod = startPeriod.toString(),
  endPeriod = endPeriod.toString(),
)


