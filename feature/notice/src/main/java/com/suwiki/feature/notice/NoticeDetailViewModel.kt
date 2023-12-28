package com.suwiki.feature.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.notice.NoticeDetail
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

  private var _noticeDetail = NoticeDetail()
  val noticeDetail = _noticeDetail

  init {
    viewModelScope.launch {
      getNoticeDetailUseCase(1)
        .onSuccess {
          _noticeDetail = it
          hideProgressBar()
        }
        .onFailure {
          showProgressBar()
        }
    }
  }

  private fun showProgressBar() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideProgressBar() = intent { reduce { state.copy(isLoading = false) } }
  fun popBackStack() = intent { postSideEffect(NoticeDetailSideEffect.PopBackStack) }
}
