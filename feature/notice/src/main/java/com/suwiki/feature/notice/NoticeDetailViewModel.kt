package com.suwiki.feature.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.notice.usecase.GetNoticeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor(
  private val getNoticeDetailUseCase: GetNoticeDetailUseCase,
) : ContainerHost<NoticeDetailState, NoticeDetailSideEffect>, ViewModel() {
  override val container: Container<NoticeDetailState, NoticeDetailSideEffect> = container(NoticeDetailState())

  suspend fun loadNoticeDetail() {
    viewModelScope.launch {
      getNoticeDetailUseCase(1)
        .onSuccess { noticeDetail ->
          intent { reduce { state.copy(noticeDetail = noticeDetail) } }
          hideLoadingScreen()
        }
        .onFailure {
          showLoadingScreen()
        }
    }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun popBackStack() = intent { postSideEffect(NoticeDetailSideEffect.PopBackStack) }
}
