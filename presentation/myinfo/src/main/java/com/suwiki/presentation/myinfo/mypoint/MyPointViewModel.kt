package com.suwiki.presentation.myinfo.mypoint

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.user.User
import com.suwiki.domain.lectureevaluation.my.usecase.GetPurchaseHistoryUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyPointViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val getPurchaseHistoryUseCase: GetPurchaseHistoryUseCase,
) : ContainerHost<MyPointState, MyPointSideEffect>, ViewModel() {
  override val container: Container<MyPointState, MyPointSideEffect> = container(MyPointState())

  fun initData() = intent {
    showLoadingScreen()
    getUserInfoUseCase().collect(::reduceMyPoint)
    getPurchaseHistory()
    hideLoadingScreen()
  }

  private fun reduceMyPoint(user: User) = intent {
    reduce {
      state.copy(
        currentPoint = user.point,
        writtenLectureEvaluations = user.writtenEvaluation,
        writtenExamEvaluations = user.writtenExam,
        viewExam = user.viewExam,
      )
    }
  }

  private fun getPurchaseHistory() = intent {
    getPurchaseHistoryUseCase()
      .onSuccess { purchaseHistory ->
        reduce { state.copy(purchaseHistory = purchaseHistory.toPersistentList()) }
      }
      .onFailure {
        postSideEffect(MyPointSideEffect.HandleException(it))
      }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun popBackStack() = intent { postSideEffect(MyPointSideEffect.PopBackStack) }
}
