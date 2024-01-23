package com.suwiki.feature.lectureevaluation.viewerreporter.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.core.model.enums.LectureEvaluationTab
import com.suwiki.core.model.exception.UserPointLackException
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.core.ui.extension.encodeToUri
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam.BuyExamUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.exam.GetExamEvaluationListUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.GetLectureEvaluationExtraAverageUseCase
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.GetLectureEvaluationListUseCase
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
      postSideEffect(LectureEvaluationDetailSideEffect.NavigateExamEvaluationEditor(
        Json.encodeToUri(
          MyExamEvaluation(
            id = evaluationId,
            lectureName = state.lectureEvaluationExtraAverage.info.lectureName,
            professor = state.lectureEvaluationExtraAverage.info.professor,
            majorType = state.lectureEvaluationExtraAverage.info.majorType,
            semesterList = state.lectureEvaluationExtraAverage.info.semesterList,
          )
        )
      ))
    }
  }

  fun popBackStack() = intent { postSideEffect(LectureEvaluationDetailSideEffect.PopBackStack) }
}
