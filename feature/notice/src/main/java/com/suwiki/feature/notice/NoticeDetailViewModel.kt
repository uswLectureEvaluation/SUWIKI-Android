package com.suwiki.feature.notice

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel @Inject constructor() : ContainerHost<NoticeDetailState, NoticeDetailSideEffect>, ViewModel() {
  override val container: Container<NoticeDetailState, NoticeDetailSideEffect> = container(NoticeDetailState())

  private var isLoading: Boolean = false
  fun checkNoticeDetailLoaded() {
    // TODO(공지사항 상세정보 로딩 완료 확인)

    if (isLoading) {
      showProgressBar()
    } else {
      hideProgressBar()
    }
  }

  private fun showProgressBar() = intent { reduce { state.copy(isLoading = false) } }
  private fun hideProgressBar() = intent { reduce { state.copy(isLoading = true) } }
  fun navigateNoticeDetail() = intent { postSideEffect(NoticeDetailSideEffect.NavigateBack) }
}
