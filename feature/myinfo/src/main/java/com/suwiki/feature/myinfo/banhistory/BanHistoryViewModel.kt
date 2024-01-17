package com.suwiki.feature.myinfo.banhistory

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BanHistoryViewModel @Inject constructor() : ContainerHost<BanHistoryState, BanHistorySideEffect>, ViewModel() {
  override val container: Container<BanHistoryState, BanHistorySideEffect> = container(BanHistoryState())

  fun popBackStack() = intent { postSideEffect(BanHistorySideEffect.PopBackStack) }
}
