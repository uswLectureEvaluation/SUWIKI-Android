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
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.extension.encodeToUri
import com.suwiki.feature.timetable.celleditor.CellEditorRoute
import com.suwiki.feature.timetable.createtimetable.CreateTimetableRoute
import com.suwiki.feature.timetable.openlecture.OpenLectureRoute
import com.suwiki.feature.timetable.timetable.TimetableRoute
import com.suwiki.feature.timetable.timetablelist.TimetableListRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun NavController.navigateTimetable(navOptions: NavOptions) {
  navigate(TimetableRoute.route, navOptions)
}


fun NavController.navigateCreateTimetable() {
  navigate(TimetableRoute.createTimetableRoute)
}

fun NavController.navigateTimetableList() {
  navigate(TimetableRoute.timetableListRoute)
}

fun NavController.navigateOpenLecture() {
  navigate(TimetableRoute.openLectureRoute)
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
  navigateTimetableList: () -> Unit,
  navigateOpenLecture: () -> Unit,
  navigateCellEditor: (CellEditorArgument) -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
) {
  composable(route = TimetableRoute.route) {
    TimetableRoute(
      padding = padding,
      navigateCreateTimetable = navigateCreateTimetable,
      navigateTimetableList = navigateTimetableList,
      handleException = handleException,
      onShowToast = onShowToast,
      navigateOpenLecture = navigateOpenLecture,
      navigateCellEditor = navigateCellEditor,
    )
  }

  composable(route = TimetableRoute.createTimetableRoute) {
    CreateTimetableRoute(
      popBackStack = popBackStack,
      handleException = handleException,
      onShowToast = onShowToast,
    )
  }

  composable(route = TimetableRoute.openLectureRoute) { navBackStackEntry ->
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

  composable(
    route = TimetableRoute.timetableListRoute
  ) {
    TimetableListRoute(
      handleException = handleException,
      popBackStack = popBackStack,
    )
  }
}

object TimetableRoute {
  const val route = "timetable"
  const val createTimetableRoute = "$route/create"
  const val openLectureRoute = "open-lecture"
  const val timetableListRoute = "$route/list"
  const val CELL_EDITOR_ARGUMENT = "cell-editor-argument"

  fun cellEditorRoute(cellEditor: String) = "cell-editor/$cellEditor"
}

@Serializable
data class CellEditorArgument(
  val oldCellId: String = "",
  val name: String = "",
  val professorName: String = "",
  val cellList: List<CellArgument> = emptyList(),
  val timetableCellColor: TimetableCellColor = TimetableCellColor.entries.shuffled().first(),
) {
  val isEditMode = oldCellId.isNotEmpty()
}

@Serializable
data class CellArgument(
  val location: String = "",
  val day: TimetableDay = TimetableDay.MON,
  val startPeriod: String = "",
  val endPeriod: String = "",
)

internal fun OpenLecture.toCellEditorArgument() = CellEditorArgument(
  name = name,
  professorName = professorName,
  cellList = originalCellList.map { it.toCellArgument() },
)

internal fun Cell.toCellArgument() = CellArgument(
  location = location,
  day = day,
  startPeriod = startPeriod.toString(),
  endPeriod = endPeriod.toString(),
)

internal fun TimetableCell.toCellEditorArgument() = CellEditorArgument(
  oldCellId = id,
  name = name,
  professorName = professor,
  cellList = listOf(
    CellArgument(
      location = location,
      day = day,
      startPeriod = startPeriod.toString(),
      endPeriod = endPeriod.toString(),
    ),
  ),
  timetableCellColor = color
)


