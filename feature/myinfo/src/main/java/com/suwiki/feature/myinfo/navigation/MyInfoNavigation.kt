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
import com.suwiki.feature.myinfo.myevaluation.examevaluation.MyExamEvaluationEditRoute
import com.suwiki.feature.myinfo.myevaluation.lectureevaluation.MyLectureEvaluationEditRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyEvaluation() {
  navigate(MyInfoRoute.myEvaluationRoute)
}

fun NavController.navigateMyLectureEvaluation(lectureEvaluation: String) {
  navigate(MyInfoRoute.myLectureEvaluationEditRoute(lectureEvaluation))
}

fun NavController.navigateMyExamEvaluation(examEvaluation: String) {
  navigate(MyInfoRoute.myExamEvaluationEditRoute(examEvaluation))
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: () -> Unit = {},
  navigateMyLectureEvaluationEdit: (String) -> Unit = {},
  navigateMyExamEvaluationEdit: (String) -> Unit = {},
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
  composable(
    route = MyInfoRoute.myLectureEvaluationEditRoute("{${MyInfoRoute.myLectureEvaluation}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myLectureEvaluation) {
        type = NavType.StringType
      },
    ),
  ) {
    MyLectureEvaluationEditRoute(
      popBackStack = popBackStack,
      onShowToast = onShowToast,
    )
  }
  composable(
    route = MyInfoRoute.myExamEvaluationEditRoute("{${MyInfoRoute.myExamEvaluation}}"),
    arguments = listOf(
      navArgument(MyInfoRoute.myExamEvaluation) {
        type = NavType.StringType
      },
    ),
  ) {
    MyExamEvaluationEditRoute(
      popBackStack = popBackStack,
      onShowToast = onShowToast,
    )
  }
}

object MyInfoRoute {
  const val route = "my-info"
  const val myEvaluationRoute = "my-evaluation"
  const val myLectureEvaluation = "lecture-evaluation"
  const val myExamEvaluation = "exam-evaluation"
  fun myLectureEvaluationEditRoute(lectureEvaluation: String) = "my-lecture-evaluation-edit/$lectureEvaluation"
  fun myExamEvaluationEditRoute(examEvaluation: String) = "my-exam-evaluation-edit/$examEvaluation"
}
