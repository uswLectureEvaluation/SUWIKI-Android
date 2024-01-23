package com.suwiki.feature.lectureevaluation.editor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.suwiki.feature.lectureevaluation.editor.examevaluation.ExamEvaluationEditorRoute
import com.suwiki.feature.lectureevaluation.editor.lectureevaluation.LectureEvaluationEditorRoute

// TODO 리팩토링
fun NavController.navigateLectureEvaluationEditor(lectureEvaluation: String) {
  navigate(EvaluationEditorRoute.lectureEvaluationEditorRoute(lectureEvaluation))
}

// TODO 리팩토링
fun NavController.navigateExamEvaluationEditor(examEvaluation: String) {
  navigate(EvaluationEditorRoute.examEvaluationEditorRoute(examEvaluation))
}

fun NavGraphBuilder.myEvaluationEditNavGraph(
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  composable(
    route = EvaluationEditorRoute.lectureEvaluationEditorRoute("{${EvaluationEditorRoute.lectureEvaluation}}"),
    arguments = listOf(
      navArgument(EvaluationEditorRoute.lectureEvaluation) {
        type = NavType.StringType
      },
    ),
  ) {
    LectureEvaluationEditorRoute(
      popBackStack = popBackStack,
      onShowToast = onShowToast,
      handleException = handleException,
    )
  }
  composable(
    route = EvaluationEditorRoute.examEvaluationEditorRoute("{${EvaluationEditorRoute.examEvaluation}}"),
    arguments = listOf(
      navArgument(EvaluationEditorRoute.examEvaluation) {
        type = NavType.StringType
      },
    ),
  ) {
    ExamEvaluationEditorRoute(
      popBackStack = popBackStack,
      onShowToast = onShowToast,
      handleException = handleException,
    )
  }
}

object EvaluationEditorRoute {
  const val lectureEvaluation = "lecture-evaluation"
  const val examEvaluation = "exam-evaluation"
  fun lectureEvaluationEditorRoute(lectureEvaluation: String) = "lecture-evaluation-editor/$lectureEvaluation"
  fun examEvaluationEditorRoute(examEvaluation: String) = "exam-evaluation-editor/$examEvaluation"
}
