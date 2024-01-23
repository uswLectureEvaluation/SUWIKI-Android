package com.suwiki.feature.lectureevaluation.viewerreporter.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.feature.lectureevaluation.viewerreporter.LectureEvaluationRoute
import com.suwiki.feature.lectureevaluation.viewerreporter.detail.LectureEvaluationDetailRoute

fun NavController.navigateLectureEvaluation(navOptions: NavOptions) {
  navigate(LectureEvaluationRoute.route, navOptions)
}

fun NavController.navigateLectureEvaluationDetail(id: String) {
  navigate(LectureEvaluationRoute.detailRoute(id))
}

fun NavGraphBuilder.lectureEvaluationNavGraph(
  padding: PaddingValues,
  argumentName: String,
  popBackStack: () -> Unit,
  navigateLogin: () -> Unit,
  navigateSignUp: () -> Unit,
  navigateOpenMajor: (String) -> Unit,
  navigateLectureEvaluationDetail: (String) -> Unit,
  handleException: (Throwable) -> Unit,
) {
  composable(route = LectureEvaluationRoute.route) { navBackStackEntry ->
    val selectedOpenMajor = navBackStackEntry.savedStateHandle.get<String>(argumentName) ?: "전체"
    LectureEvaluationRoute(
      padding = padding,
      selectedOpenMajor = selectedOpenMajor,
      navigateLogin = navigateLogin,
      navigateSignUp = navigateSignUp,
      navigateOpenMajor = navigateOpenMajor,
      navigateLectureEvaluationDetail = navigateLectureEvaluationDetail,
      handleException = handleException,
    )
  }

  composable(
    route = LectureEvaluationRoute.detailRoute("{${LectureEvaluationRoute.lectureEvaluationDetail}}"),
    arguments = listOf(
      navArgument(LectureEvaluationRoute.lectureEvaluationDetail) {
        type = NavType.StringType
      },
    ),
  ) {
    LectureEvaluationDetailRoute(
      popBackStack = popBackStack,
      handleException = handleException,
    )
  }
}

object LectureEvaluationRoute {
  const val route = "lecture-evaluation"
  const val lectureEvaluationDetail = "lecture-evaluation-detail"

  fun detailRoute(lectureEvaluationDetail: String) = "lecture-evaluation-detail/$lectureEvaluationDetail"
}
