package com.suwiki.feature.lectureevaluation.viewerreporter.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.core.model.exception.UserPointLackException
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.feature.common.ui.enums.LectureEvaluationTab
import com.suwiki.feature.common.ui.extension.encodeToUri
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam.BuyExamUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam.GetExamEvaluationListUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam.ReportExamUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.GetLectureEvaluationExtraAverageUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.GetLectureEvaluationListUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.ReportLectureUseCase
import com.suwiki.feature.lectureevaluation.viewerreporter.navigation.LectureEvaluationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.joinAll
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LectureEvaluationDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getLectureEvaluationExtraAverageUseCase: GetLectureEvaluationExtraAverageUseCase,
  private val getLectureEvaluationListUseCase: GetLectureEvaluationListUseCase,
  private val getExamEvaluationListUseCase: GetExamEvaluationListUseCase,
  private val reportExamUseCase: ReportExamUseCase,
  private val reportLectureUseCase: ReportLectureUseCase,
  private val buyExamUseCase: BuyExamUseCase,
) : ContainerHost<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationDetailState, LectureEvaluationDetailSideEffect> = container(
    LectureEvaluationDetailState(),
  )

  private val evaluationId: Long = savedStateHandle.get<String>(LectureEvaluationRoute.lectureEvaluationDetail)!!.toLong()
  private var lectureEvaluationPage = 1
  private var isLastLectureEvaluation = false
  private var examEvaluationPage = 1
  private var isLastExamEvaluation = false

  private var lectureReportId: Long = 0
  private var examReportId: Long = 0

  fun showLectureReportDialog(lectureReportId: Long) = intent {
    this@LectureEvaluationDetailViewModel.lectureReportId = lectureReportId
    reduce {
      state.copy(
        showLectureReportDialog = true,
      )
    }
  }

  fun hideLectureReportDialog() = intent {
    reduce {
      state.copy(
        showLectureReportDialog = false,
      )
    }
  }

  fun showExamReportDialog(examReportId: Long) = intent {
    this@LectureEvaluationDetailViewModel.examReportId = examReportId
    reduce {
      state.copy(
        showExamReportDialog = true,
      )
    }
  }

  fun hideExamReportDialog() = intent {
    reduce {
      state.copy(
        showExamReportDialog = false,
      )
    }
  }

  fun reportLecture() = intent {
    reportLectureUseCase(lectureReportId)
      .onFailure { postSideEffect(LectureEvaluationDetailSideEffect.HandleException(it)) }
  }

  fun reportExam() = intent {
    reportExamUseCase(examReportId)
      .onFailure { postSideEffect(LectureEvaluationDetailSideEffect.HandleException(it)) }
  }

  fun syncPager(currentPage: Int) = intent { reduce { state.copy(currentTabPage = currentPage) } }

  fun initData() = intent {
    reduce { state.copy(isLoading = true) }

    joinAll(getLectureEvaluationDetail(), getLectureEvaluationList(needClear = true), getExamEvaluationList(needClear = true))

    reduce { state.copy(isLoading = false) }
  }

  private fun getLectureEvaluationDetail() = intent {
    getLectureEvaluationExtraAverageUseCase(evaluationId)
      .onSuccess { lectureEvaluationExtraAverage ->
        reduce {
          state.copy(
            lectureEvaluationExtraAverage = lectureEvaluationExtraAverage,
          )
        }
      }
      .onFailure {
        postSideEffect(LectureEvaluationDetailSideEffect.HandleException(it))
      }
  }

  fun getLectureEvaluationList(needClear: Boolean = false) = intent {
    val currentList = if (needClear) {
      lectureEvaluationPage = 1
      isLastLectureEvaluation = false
      persistentListOf()
    } else {
      state.lectureEvaluationList
    }

    if (isLastLectureEvaluation) return@intent

    getLectureEvaluationListUseCase(
      GetLectureEvaluationListUseCase.Param(
        lectureId = evaluationId,
        page = lectureEvaluationPage,
      ),
    ).onSuccess {
      reduce {
        lectureEvaluationPage++
        isLastLectureEvaluation = it.data.isEmpty()
        state.copy(
          lectureEvaluationList = currentList.addAll(it.data).distinctBy { it.id }.toPersistentList(),
          isLectureEvaluationWritten = it.written,
        )
      }
    }.onFailure {
      postSideEffect(LectureEvaluationDetailSideEffect.HandleException(it))
    }
  }

  fun getExamEvaluationList(needClear: Boolean = false) = intent {
    val currentList = if (needClear) {
      examEvaluationPage = 1
      isLastExamEvaluation = false
      persistentListOf()
    } else {
      state.examEvaluationList
    }

    if (isLastExamEvaluation) return@intent

    getExamEvaluationListUseCase(
      GetExamEvaluationListUseCase.Param(
        lectureId = evaluationId,
        page = examEvaluationPage,
      ),
    ).onSuccess {
      reduce {
        examEvaluationPage++
        isLastExamEvaluation = it.data.isEmpty()
        state.copy(
          examEvaluationList = currentList.addAll(it.data).distinctBy { it.id }.toPersistentList(),
          needBuyExam = it.needBuyExam,
          isExamEvaluationWritten = it.written,
        )
      }
    }.onFailure {
      postSideEffect(LectureEvaluationDetailSideEffect.HandleException(it))
    }
  }

  fun buyExam() = intent {
    buyExamUseCase(evaluationId)
      .onSuccess {
        getExamEvaluationList(needClear = true)
      }
      .onFailure { throwable ->
        when (throwable) {
          is UserPointLackException -> postSideEffect(LectureEvaluationDetailSideEffect.ShowLackPointToast(throwable.message))
          else -> postSideEffect(LectureEvaluationDetailSideEffect.HandleException(throwable))
        }
      }
  }

  fun navigateEvaluationEditor() = intent {
    if (state.currentTabPage == LectureEvaluationTab.LECTURE_EVALUATION.position) {
      if (state.isLectureEvaluationWritten) {
        postSideEffect(LectureEvaluationDetailSideEffect.ShowAlreadyWriteToast)
        return@intent
      }

      postSideEffect(
        LectureEvaluationDetailSideEffect.NavigateLectureEvaluationEditor(
          Json.encodeToUri(
            MyLectureEvaluation(
              id = evaluationId,
              lectureInfo = state.lectureEvaluationExtraAverage.info,
            ),
          ),
        ),
      )
    } else {
      if (state.isExamEvaluationWritten) {
        postSideEffect(LectureEvaluationDetailSideEffect.ShowAlreadyWriteToast)
        return@intent
      }

      postSideEffect(
        LectureEvaluationDetailSideEffect.NavigateExamEvaluationEditor(
          Json.encodeToUri(
            MyExamEvaluation(
              id = evaluationId,
              lectureName = state.lectureEvaluationExtraAverage.info.lectureName,
              professor = state.lectureEvaluationExtraAverage.info.professor,
              majorType = state.lectureEvaluationExtraAverage.info.majorType,
              semesterList = state.lectureEvaluationExtraAverage.info.semesterList,
            ),
          ),
        ),
      )
    }
  }

  fun popBackStack() = intent { postSideEffect(LectureEvaluationDetailSideEffect.PopBackStack) }
}
