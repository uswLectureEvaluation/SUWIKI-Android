package com.suwiki.feature.myinfo

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.user.User
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
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

  private var isLoggedIn: Boolean = false

  suspend fun checkLoggedIn() {
    showLoadingScreen()
    /* TODO 에러 처리 */
    getUserInfoUseCase()
      .catch { intent { postSideEffect(MyInfoSideEffect.HandleException(it)) } }
      .collect(::reduceUser)
    hideLoadingScreen()
  }

  private fun reduceUser(user: User) = intent {
    reduce {
      isLoggedIn = user.isLoggedIn
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
  fun navigateMyAccount() = intent {
    postSideEffect(
      if (isLoggedIn) {
        MyInfoSideEffect.NavigateMyAccount
      } else {
        MyInfoSideEffect.ShowNeedLoginToast
      },
    )
  }
  fun navigateMyPoint() = intent { postSideEffect(MyInfoSideEffect.NavigateMyPoint) }
  fun navigateBanHistory() = intent { postSideEffect(MyInfoSideEffect.NavigateBanHistory) }

  fun showOpenLicense() = intent { postSideEffect(MyInfoSideEffect.ShowOpenLicenses) }
}
