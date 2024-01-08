package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.feature.myinfo.MyInfoRoute
import com.suwiki.feature.myinfo.myevaluation.MyEvaluationRoute
import com.suwiki.feature.myinfo.myevaluation.lectureevaluation.MyLectureEvaluationEditRoute
import com.suwiki.feature.myinfo.myevaluation.examevaluation.MyExamEvalutionEditRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyEvaluation(point: Int) {
  navigate(MyInfoRoute.myEvaluationRoute(point.toString()))
}

fun NavController.navigateMyLectureEvaluation(point: Int) {
  navigate(MyInfoRoute.myLectureEvaluationEditRoute(point.toString()))
}

fun NavController.navigateMyExamEvaluation(point: Int) {
  navigate(MyInfoRoute.myExamEvaluationEditRoute(point.toString()))
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: (Int) -> Unit = {},
  navigateMyLectureEvaluationEdit: (Int) -> Unit = {},
  navigateMyExamEvaluationEdit: (Int) -> Unit = {},
) {
  composable(route = MyInfoRoute.route) {
    MyInfoRoute(
      padding = padding,
      navigateNotice = navigateNotice,
      navigateMyEvaluation = navigateMyEvaluation,
    )
  }
  composable(
    route = MyInfoRoute.myEvaluationRoute("{${MyInfoRoute.myPoint}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myPoint) {
        type = NavType.StringType
      },
    ),
  ) {
    MyEvaluationRoute(
      padding = padding,
      popBackStack = popBackStack,
      navigateMyLectureEvaluation = navigateMyLectureEvaluationEdit,
      navigateMyExamEvaluation = navigateMyExamEvaluationEdit,
    )
  }
  composable(
    route = MyInfoRoute.myLectureEvaluationEditRoute("{${MyInfoRoute.myPoint}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myPoint) {
        type = NavType.StringType
      },
    ),
  ) {
    MyLectureEvaluationEditRoute(
      padding = padding,
      popBackStack = popBackStack,
    )
  }
  composable(
    route = MyInfoRoute.myExamEvaluationEditRoute("{${MyInfoRoute.myPoint}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myPoint) {
        type = NavType.StringType
      },
    ),
  ) {
    MyExamEvalutionEditRoute(
      padding = padding,
      popBackStack = popBackStack,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
  const val myPoint = "point"

  fun myEvaluationRoute(point: String) = "my-evaluation/$point"
  fun myLectureEvaluationEditRoute(point: String) = "my-lecture-evaluation-edit/$point"
  fun myExamEvaluationEditRoute(point: String) = "my-exam-evaluation-edit/$point"
}
