package com.suwiki.feature.myinfo

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.user.User
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyInfoViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
) : ContainerHost<MyInfoState, MyInfoSideEffect>, ViewModel() {
  override val container: Container<MyInfoState, MyInfoSideEffect> = container(MyInfoState())

  suspend fun checkLoggedIn() {
    showLoadingScreen()
    /* TODO 에러 처리 */
    getUserInfoUseCase().collect(::reduceUser)
    hideLoadingScreen()
  }

  private fun reduceUser(user: User) = intent {
    reduce {
      state.copy(
        showMyInfoCard = user.isLoggedIn,
        loginId = user.userId,
        point = user.point,
      )
    }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }

  fun navigateNotice() = intent { postSideEffect(MyInfoSideEffect.NavigateNotice) }
  fun navigateMyEvaluation() = intent { postSideEffect(MyInfoSideEffect.NavigateMyEvaluation) }
}
