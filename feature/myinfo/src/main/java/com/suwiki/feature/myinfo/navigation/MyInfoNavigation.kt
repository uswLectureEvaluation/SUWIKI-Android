package com.suwiki.feature.myinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.feature.myinfo.MyInfoRoute
import com.suwiki.feature.myinfo.myevaluation.MyEvaluationRoute
import com.suwiki.feature.myinfo.myevaluation.examevaluation.MyExamEvalutionEditRoute
import com.suwiki.feature.myinfo.myevaluation.lectureevaluation.MyLectureEvaluationEditRoute

fun NavController.navigateMyInfo(navOptions: NavOptions) {
  navigate(MyInfoRoute.route, navOptions)
}

fun NavController.navigateMyEvaluation() {
  navigate(MyInfoRoute.myEvaluationRoute)
}

fun NavController.navigateMyLectureEvaluation(lectureEvaluation: MyLectureEvaluation) {
  navigate(MyInfoRoute.myLectureEvaluationEditRoute(lectureEvaluation.toString()))
}

fun NavController.navigateMyExamEvaluation(examEvaluation: MyExamEvaluation) {
  navigate(MyInfoRoute.myExamEvaluationEditRoute(examEvaluation.toString()))
}

fun NavGraphBuilder.myInfoNavGraph(
  padding: PaddingValues,
  popBackStack: () -> Unit = {},
  navigateNotice: () -> Unit = {},
  navigateMyEvaluation: () -> Unit = {},
  navigateMyLectureEvaluationEdit: (MyLectureEvaluation) -> Unit = {},
  navigateMyExamEvaluationEdit: (MyExamEvaluation) -> Unit = {},
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
      padding = padding,
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
  const val myLectureEvaluation = "lecture-evaluation"
  const val myExamEvaluation = "exam-evaluation"
  fun myLectureEvaluationEditRoute(lectureEvaluation: String) = "my-lecture-evaluation-edit/$lectureEvaluation"
  fun myExamEvaluationEditRoute(examEvaluation: String) = "my-exam-evaluation-edit/$examEvaluation"
}
