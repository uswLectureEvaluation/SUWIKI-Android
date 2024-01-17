package com.suwiki.feature.myinfo.banhistory

import androidx.lifecycle.ViewModel
import com.suwiki.domain.user.usecase.GetBanHistoryUseCase
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
class BanHistoryViewModel @Inject constructor(
  private val getBanHistoryUseCase: GetBanHistoryUseCase,
) : ContainerHost<BanHistoryState, BanHistorySideEffect>, ViewModel() {
  override val container: Container<BanHistoryState, BanHistorySideEffect> = container(BanHistoryState())

  fun initData() = intent {
    getBanHistoryUseCase()
      .onSuccess {
        reduce { state.copy(banHistory = it.toPersistentList()) }
      }
      .onFailure {
        postSideEffect(BanHistorySideEffect.HandleException(it))
      }
  }

  fun popBackStack() = intent { postSideEffect(BanHistorySideEffect.PopBackStack) }
}
