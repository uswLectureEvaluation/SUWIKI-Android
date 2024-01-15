package com.suwiki.feature.lectureevaluation.editor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.feature.lectureevaluation.editor.examevaluation.MyExamEvaluationEditRoute
import com.suwiki.feature.lectureevaluation.editor.lectureevaluation.MyLectureEvaluationEditRoute

// TODO 리팩토링
fun NavController.navigateMyLectureEvaluation(lectureEvaluation: String) {
  navigate(MyEvaluationEditRoute.myLectureEvaluationEditRoute(lectureEvaluation))
}

// TODO 리팩토링
fun NavController.navigateMyExamEvaluation(examEvaluation: String) {
  navigate(MyEvaluationEditRoute.myExamEvaluationEditRoute(examEvaluation))
}

fun NavGraphBuilder.myEvaluationEditNavGraph(
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  composable(
    route = MyEvaluationEditRoute.myLectureEvaluationEditRoute("{${MyEvaluationEditRoute.myLectureEvaluation}}"),
    arguments = listOf(
      navArgument(MyEvaluationEditRoute.myLectureEvaluation) {
        type = NavType.StringType
      },
    ),
  ) {
    MyLectureEvaluationEditRoute(
      popBackStack = popBackStack,
      onShowToast = onShowToast,
      handleException = handleException,
    )
  }
  composable(
    route = MyEvaluationEditRoute.myExamEvaluationEditRoute("{${MyEvaluationEditRoute.myExamEvaluation}}"),
    arguments = listOf(
      navArgument(MyEvaluationEditRoute.myExamEvaluation) {
        type = NavType.StringType
      },
    ),
  ) {
    MyExamEvaluationEditRoute(
      popBackStack = popBackStack,
      onShowToast = onShowToast,
      handleException = handleException,
    )
  }
}

object MyEvaluationEditRoute {
  const val myLectureEvaluation = "lecture-evaluation"
  const val myExamEvaluation = "exam-evaluation"
  fun myLectureEvaluationEditRoute(lectureEvaluation: String) = "my-lecture-evaluation-edit/$lectureEvaluation"
  fun myExamEvaluationEditRoute(examEvaluation: String) = "my-exam-evaluation-edit/$examEvaluation"
}
