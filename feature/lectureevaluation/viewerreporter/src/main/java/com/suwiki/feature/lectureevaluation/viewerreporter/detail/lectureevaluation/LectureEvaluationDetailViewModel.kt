package com.suwiki.feature.lectureevaluation.viewerreporter.detail.lectureevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.feature.lectureevaluation.viewerreporter.navigation.LectureEvaluationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LectureEvaluationDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
) : ContainerHost<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect> = container(LectureEvaluationDetailState())

  private val evaluationId: Long = savedStateHandle.get<String>(LectureEvaluationRoute.lectureEvaluationDetail)!!.toLong()

  init {
    Timber.d(evaluationId.toString())
  }
}
