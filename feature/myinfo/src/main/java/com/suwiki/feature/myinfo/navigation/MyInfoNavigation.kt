package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.suwiki.feature.myinfo.MyInfoRoute
import com.suwiki.feature.myinfo.myevaluation.MyEvaluationRoute
import com.suwiki.feature.myinfo.myevaluation.lectureevaluation.MyLectureEvaluationEditRoute
import com.suwiki.feature.myinfo.myevaluation.examevaluation.MyExamEvalutionEditRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyEvaluation() {
  navigate(MyInfoRoute.myEvaluationRoute)
}

fun NavController.navigateMyLectureEvaluation() {
  navigate(MyInfoRoute.myLectureEvaluationEditRoute)
}

fun NavController.navigateMyExamEvaluation() {
  navigate(MyInfoRoute.myExamEvaluationEditRoute)
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: () -> Unit = {},
  navigateMyLectureEvaluationEdit: () -> Unit = {},
  navigateMyExamEvaluationEdit: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyEvaluation = navigateMyEvaluation,
    )
  }
  composable(route = MyInfoRoute.myEvaluationRoute) {
    MyEvaluationRoute(
      padding = padding,
      popBackStack = popBackStack,
      navigateMyLectureEvaluation = navigateMyLectureEvaluationEdit,
      navigateMyExamEvaluation = navigateMyExamEvaluationEdit,
    )
  }
  composable(route = MyInfoRoute.myLectureEvaluationEditRoute) {
    MyLectureEvaluationEditRoute(
      padding = padding,
      popBackStack = popBackStack,
      onShowToast = onShowToast,
    )
  }
  composable(route = MyInfoRoute.myExamEvaluationEditRoute) {
    MyExamEvalutionEditRoute(
      padding = padding,
      popBackStack = popBackStack,
      onShowToast = onShowToast,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
  const val myEvaluationRoute = "my-evaluation"
  const val myLectureEvaluationEditRoute = "my-lecture-evaluation-edit"
  const val myExamEvaluationEditRoute = "my-exam-evaluation-edit"
}
