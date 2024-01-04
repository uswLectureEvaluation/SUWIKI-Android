package com.suwiki.feature.myinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
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
    viewModelScope.launch {
      showLoadingScreen()
      getUserInfoUseCase().catch {}.collect { user ->
        isLoggedIn = user.isLoggedIn == true
        intent { reduce { state.copy(loginId = user.userId, point = user.point) } }
      }

      if (isLoggedIn) {
        showMyService()
      } else {
        hideMyService()
      }
      hideLoadingScreen()
    }
  }

  private fun showMyService() = intent { reduce { state.copy(showMyInfoCard = true) } }
  private fun hideMyService() = intent { reduce { state.copy(showMyInfoCard = false) } }
  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }

  fun navigateNotice() = intent { postSideEffect(MyInfoSideEffect.NavigateNotice) }
  fun navigateMyReview(point: Int) = intent { postSideEffect(MyInfoSideEffect.NavigateMyReview(point)) }
}
