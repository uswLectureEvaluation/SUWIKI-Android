package com.suwiki.presentation.notice.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.domain.notice.usecase.GetNoticeDetailUseCase
import com.suwiki.presentation.notice.navigation.NoticeRoute
import dagger.hilt.android.lifecycle.HiltViewModel
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
  savedStateHandle: SavedStateHandle,
) : ContainerHost<NoticeDetailState, NoticeDetailSideEffect>, ViewModel() {
  override val container: Container<NoticeDetailState, NoticeDetailSideEffect> = container(NoticeDetailState())

  private val noticeId: Long = savedStateHandle.get<String>(NoticeRoute.DETAIL_ARGUMENT_NAME)!!.toLong()

  fun loadNoticeDetail() = intent {
    showLoadingScreen()
    getNoticeDetailUseCase(noticeId)
      .onSuccess { noticeDetail ->
        reduce { state.copy(noticeDetail = noticeDetail) }
      }
      .onFailure {
        postSideEffect(NoticeDetailSideEffect.HandleException(it))
      }
    hideLoadingScreen()
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun popBackStack() = intent { postSideEffect(NoticeDetailSideEffect.PopBackStack) }
}
