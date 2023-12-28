package com.suwiki.feature.lectureevaluation.viewerreporter.navigation

import androidx.compose.foundation.layout.PaddingValues
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
  navigateLogin: () -> Unit,
) {
  composable(route = LectureEvaluationRoute.route) {
    LectureEvaluationRoute(
      padding = padding,
      navigateLogin = navigateLogin,
    )
  }
}

object LectureEvaluationRoute {
  const val route = "lecture-evaluation"
}
