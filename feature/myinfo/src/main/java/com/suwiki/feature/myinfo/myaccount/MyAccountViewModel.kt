package com.suwiki.feature.myinfo.myaccount

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.user.User
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.domain.user.usecase.LogoutUseCase
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
class MyAccountViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val logoutUseCase: LogoutUseCase,
) : ContainerHost<MyAccountState, MyAccountSideEffect>, ViewModel() {
  override val container: Container<MyAccountState, MyAccountSideEffect> = container(MyAccountState())

  fun initData() = intent {
    getUserInfoUseCase()
      .catch { e -> postSideEffect(MyAccountSideEffect.HandleException(e)) }
      .collect(::updateUserIdAndEmail)
  }

  fun logout() = intent {
    logoutUseCase()
      .onSuccess { popBackStack() }
      .onFailure { postSideEffect(MyAccountSideEffect.HandleException(it)) }
  }

  private fun updateUserIdAndEmail(user: User) = intent {
    if (user.isLoggedIn.not()) {
      popBackStack()
      return@intent
    }

    reduce {
      state.copy(
        userId = user.userId,
        userEmail = user.email,
      )
    }
  }

  fun popBackStack() = intent { postSideEffect(MyAccountSideEffect.PopBackStack) }
  fun navigateResetPassword() = intent { postSideEffect(MyAccountSideEffect.NavigateResetPassword) }
  fun navigateQuit() = intent { postSideEffect(MyAccountSideEffect.NavigateQuit) }
}
