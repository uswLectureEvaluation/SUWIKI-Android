package com.suwiki.feature.lectureevaluation.viewerreporter.detail.lectureevaluation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LectureEvaluationDetailViewModel @Inject constructor() : ContainerHost<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect>, ViewModel() { // ktlint-disable max-line-length
  override val container: Container<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect> = container(LectureEvaluationDetailState())
}
