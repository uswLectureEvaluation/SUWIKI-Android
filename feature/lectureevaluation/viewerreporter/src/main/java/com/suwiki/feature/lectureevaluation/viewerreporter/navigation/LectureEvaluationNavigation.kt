package com.suwiki.feature.lectureevaluation.viewerreporter.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.lectureevaluation.viewerreporter.LectureEvaluationRoute

fun NavController.navigateLectureEvaluation(navOptions: NavOptions) {
  navigate(LectureEvaluationRoute.route, navOptions)
}

fun NavGraphBuilder.lectureEvaluationNavGraph(
  padding: PaddingValues,
  argumentName: String,
  navigateLogin: () -> Unit,
  navigateSignUp: () -> Unit,
  navigateOpenMajor: (String) -> Unit,
) {
  composable(route = LectureEvaluationRoute.route) { navBackStackEntry ->
    val selectedOpenMajor = navBackStackEntry.savedStateHandle.get<String>(argumentName) ?: "전체"
    LectureEvaluationRoute(
      padding = padding,
      selectedOpenMajor = selectedOpenMajor,
      navigateLogin = navigateLogin,
      navigateSignUp = navigateSignUp,
      navigateOpenMajor = navigateOpenMajor,
    )
  }
}

object LectureEvaluationRoute {
  const val route = "lecture-evaluation"
}
