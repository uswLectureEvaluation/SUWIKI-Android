package com.suwiki.feature.lectureevaluation.viewerreporter.detail.lectureevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.GetLectureEvaluationExtraAverageUseCase
import com.suwiki.feature.lectureevaluation.viewerreporter.navigation.LectureEvaluationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LectureEvaluationDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getLectureEvaluationExtraAverageUseCase: GetLectureEvaluationExtraAverageUseCase,
) : ContainerHost<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect> = container(LectureEvaluationDetailState())

  private val evaluationId: Long = savedStateHandle.get<String>(LectureEvaluationRoute.lectureEvaluationDetail)!!.toLong()

  fun getLectureEvaluationDetail() = intent {
    getLectureEvaluationExtraAverageUseCase(evaluationId)
      .onSuccess { lectureEvaluationExtraAverage ->
        Timber.d(lectureEvaluationExtraAverage.toString())
        reduce {
          state.copy(
            lectureInfo = lectureEvaluationExtraAverage.lectureInfo,
            lectureTotalAvg = lectureEvaluationExtraAverage.lectureTotalAvg,
            lectureSatisfactionAvg = lectureEvaluationExtraAverage.lectureSatisfactionAvg,
            lectureHoneyAvg = lectureEvaluationExtraAverage.lectureHoneyAvg,
            lectureLearningAvg = lectureEvaluationExtraAverage.lectureLearningAvg,
            lectureTeamAvg = lectureEvaluationExtraAverage.lectureTeamAvg,
            lectureDifficultyAvg = lectureEvaluationExtraAverage.lectureDifficultyAvg,
            lectureHomeworkAvg = lectureEvaluationExtraAverage.lectureHomeworkAvg,
          )
        }
      }
      .onFailure {
        postSideEffect(LectureEvaluationDetailSideEffect.HandleException(it))
      }
  }
}
