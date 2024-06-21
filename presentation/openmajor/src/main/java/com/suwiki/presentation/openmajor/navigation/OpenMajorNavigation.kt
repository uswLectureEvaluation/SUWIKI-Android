package com.suwiki.presentation.openmajor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.presentation.openmajor.OpenMajorRoute

fun NavController.navigateOpenMajor(selectedOpenMajor: String = "") {
  navigate(OpenMajorRoute.route(selectedOpenMajor))
}

fun NavGraphBuilder.openMajorNavGraph(
  popBackStack: () -> Unit,
  popBackStackWithArgument: (String) -> Unit,
  handleException: (Throwable) -> Unit,
  onShowToast: (String) -> Unit,
) {
  composable(
    route = OpenMajorRoute.route("{${OpenMajorRoute.ARGUMENT_NAME}}"),
    arguments = listOf(
      navArgument(OpenMajorRoute.ARGUMENT_NAME) {
        type = NavType.StringType
        defaultValue = "전체"
      },
    ),
  ) {
    OpenMajorRoute(
      popBackStack = popBackStack,
      popBackStackWithArgument = popBackStackWithArgument,
      handleException = handleException,
      onShowToast = onShowToast,
    )
  }
}

object OpenMajorRoute {
  const val ARGUMENT_NAME = "selectedOpenMajor"
  fun route(selectedOpenMajor: String = "전체") = "open-major/$selectedOpenMajor"
}
