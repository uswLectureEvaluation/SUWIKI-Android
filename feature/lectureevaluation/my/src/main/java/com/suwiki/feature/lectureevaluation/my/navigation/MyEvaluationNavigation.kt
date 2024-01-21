package com.suwiki.feature.lectureevaluation.my.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.suwiki.feature.lectureevaluation.my.MyEvaluationRoute

fun NavController.navigateMyEvaluation() {
  navigate(MyEvaluationRoute.route)
}

fun NavGraphBuilder.myEvaluationNavGraph(
  popBackStack: () -> Unit = {},
  navigateMyLectureEvaluationEdit: (String) -> Unit = {},
  navigateMyExamEvaluationEdit: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  composable(route = MyEvaluationRoute.route) {
    MyEvaluationRoute(
      popBackStack = popBackStack,
      navigateMyLectureEvaluation = navigateMyLectureEvaluationEdit,
      navigateMyExamEvaluation = navigateMyExamEvaluationEdit,
      handleException = handleException,
    )
  }
}

object MyEvaluationRoute {
  const val route = "my-evaluation"
}
