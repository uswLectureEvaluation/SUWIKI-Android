package com.suwiki.feature.lectureevaluation.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.user.User
import com.suwiki.domain.lectureevaluation.editor.usecase.exam.DeleteExamEvaluationUseCase
import com.suwiki.domain.lectureevaluation.editor.usecase.lecture.DeleteLectureEvaluationUseCase
import com.suwiki.domain.lectureevaluation.my.usecase.GetMyExamEvaluationListUseCase
import com.suwiki.domain.lectureevaluation.my.usecase.GetMyLectureEvaluationListUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyEvaluationViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val getMyLectureEvaluationListUseCase: GetMyLectureEvaluationListUseCase,
  private val getMyExamEvaluationListUseCase: GetMyExamEvaluationListUseCase,
  private val deleteExamEvaluationUseCase: DeleteExamEvaluationUseCase,
  private val deleteLectureEvaluationUseCase: DeleteLectureEvaluationUseCase,
) : ContainerHost<MyEvaluationState, MyEvaluationSideEffect>, ViewModel() {
  override val container: Container<MyEvaluationState, MyEvaluationSideEffect> = container(MyEvaluationState())

  private var lectureEvaluationPage = 1
  private var isLastLectureEvaluation = false
  private var examEvaluationPage = 1
  private var isLastExamEvaluation = false

  private var toDeleteExamId: Long = 0
  private var toDeleteLectureId: Long = 0

  fun getMyLectureEvaluations(needClear: Boolean = false) = intent {
    val currentList = if (needClear) {
      lectureEvaluationPage = 1
      isLastLectureEvaluation = false
      persistentListOf()
    } else {
      state.myLectureEvaluationList
    }

    if (isLastLectureEvaluation) return@intent

    getMyLectureEvaluationListUseCase(lectureEvaluationPage)
      .onSuccess {
        reduce {
          lectureEvaluationPage++
          isLastLectureEvaluation = it.isEmpty()
          state.copy(myLectureEvaluationList = currentList.addAll(it).distinctBy { it.id }.toPersistentList())
        }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
  }

  fun getMyExamEvaluations(needClear: Boolean = false) = intent {
    val currentList = if (needClear) {
      examEvaluationPage = 1
      isLastExamEvaluation = false
      persistentListOf()
    } else {
      state.myExamEvaluationList
    }

    if (isLastExamEvaluation) return@intent

    getMyExamEvaluationListUseCase(examEvaluationPage)
      .onSuccess {
        reduce {
          examEvaluationPage++
          isLastExamEvaluation = it.isEmpty()
          state.copy(myExamEvaluationList = currentList.addAll(it).distinctBy { it.id }.toPersistentList())
        }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
  }

  fun initData() = intent {
    showLoadingScreen()

    getUserInfoUseCase()
      .onEach(::setPoint)
      .catch { postSideEffect(MyEvaluationSideEffect.HandleException(it)) }
      .launchIn(viewModelScope)

    joinAll(getMyLectureEvaluations(true), getMyExamEvaluations(true))
    hideLoadingScreen()
  }

  fun deleteExamEvaluation() = intent {
    deleteExamEvaluationUseCase(toDeleteExamId)
      .onSuccess {
        reduce {
          state.copy(
            point = state.point - 30,
            myExamEvaluationList = state.myExamEvaluationList.filter { it.id != toDeleteExamId }.toPersistentList(),
          )
        }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
  }

  fun deleteLectureEvaluation() = intent {
    deleteLectureEvaluationUseCase(toDeleteLectureId)
      .onSuccess {
        reduce {
          state.copy(
            point = state.point - 30,
            myLectureEvaluationList = state.myLectureEvaluationList.filter { it.id != toDeleteLectureId }.toPersistentList(),
          )
        }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
  }

  private fun setPoint(user: User) = intent { reduce { state.copy(point = user.point) } }

  fun syncPager(currentPage: Int) = intent { reduce { state.copy(currentTabPage = currentPage) } }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }

  fun popBackStack() = intent { postSideEffect(MyEvaluationSideEffect.PopBackStack) }
  fun navigateMyLectureEvaluation(lectureEvaluation: String) = intent {
    postSideEffect(MyEvaluationSideEffect.NavigateLectureEvaluationEditor(lectureEvaluation))
  }

  fun navigateMyExamEvaluation(examEvaluation: String) = intent {
    postSideEffect(MyEvaluationSideEffect.NavigateExamEvaluationEditor(examEvaluation))
  }

  fun showExamDeleteOrLackPointDialog(id: Long) = intent {
    if (state.point >= 30) {
      toDeleteExamId = id
      showExamEvaluationDeleteDialog()
    } else {
      showLackPointDialog()
    }
  }

  fun showLectureDeleteOrLackPointDialog(id: Long) = intent {
    if (state.point >= 30) {
      toDeleteLectureId = id
      showLectureEvaluationDeleteDialog()
    } else {
      showLackPointDialog()
    }
  }

  private fun showExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = true) } }
  fun hideExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = false) } }
  private fun showLectureEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteLectureEvaluationDialog = true) } }
  fun hideLectureEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteLectureEvaluationDialog = false) } }
  private fun showLackPointDialog() = intent { reduce { state.copy(showLackPointDialog = true) } }
  fun hideLackPointDialog() = intent { reduce { state.copy(showLackPointDialog = false) } }
}
