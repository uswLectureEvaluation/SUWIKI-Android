package com.suwiki.feature.openmajor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.feature.openmajor.OpenMajorRoute

fun NavController.navigateOpenMajor(selectedOpenMajor: String = "") {
  navigate(OpenMajorRoute.route(selectedOpenMajor))
}

fun NavGraphBuilder.openMajorNavGraph(
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  composable(
    route = OpenMajorRoute.route("{selectedOpenMajor}"),
    arguments = listOf(
      navArgument("selectedOpenMajor") {
        type = NavType.StringType
      },
    ),
  ) {
    OpenMajorRoute(
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }
}

object OpenMajorRoute {
  fun route(selectedOpenMajor: String) = "open-major/$selectedOpenMajor"
}
