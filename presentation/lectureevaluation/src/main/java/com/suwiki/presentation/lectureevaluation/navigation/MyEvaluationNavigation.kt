package com.suwiki.presentation.lectureevaluation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.presentation.lectureevaluation.my.MyEvaluationRoute

fun NavController.navigateMyEvaluation() {
  navigate(MyEvaluationRoute.route)
}

fun NavGraphBuilder.myEvaluationNavGraph(
  popBackStack: () -> Unit = {},
  navigateLectureEvaluationEditor: (String) -> Unit = {},
  navigateExamEvaluationEditor: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  composable(route = MyEvaluationRoute.route) { navBackStackEntry ->
    MyEvaluationRoute(
      popBackStack = popBackStack,
      navigateLectureEvaluationEditor = navigateLectureEvaluationEditor,
      navigateExamEvaluationEditor = navigateExamEvaluationEditor,
      handleException = handleException,
    )
  }
}

object MyEvaluationRoute {
  const val route = "my-evaluation"
}
