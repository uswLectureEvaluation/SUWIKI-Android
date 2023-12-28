package com.suwiki.feature.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.notice.Notice
import com.suwiki.domain.notice.usecase.GetNoticeListUseCase
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
class NoticeViewModel @Inject constructor(
  private val getNoticeListUseCase: GetNoticeListUseCase,
) : ContainerHost<NoticeState, NoticeSideEffect>, ViewModel() {

  override val container: Container<NoticeState, NoticeSideEffect> = container(NoticeState())

  private var _noticeList: List<Notice> = listOf()
  val noticeList = _noticeList

  init {
    viewModelScope.launch {
      getNoticeListUseCase(1)
        .onSuccess { notices ->
          _noticeList = notices
          hideProgressBar()
        }
        .onFailure {
          showProgressBar()
        }
    }
  }

  private fun showProgressBar() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideProgressBar() = intent { reduce { state.copy(isLoading = false) } }
  fun navigateNoticeDetail() = intent { postSideEffect(NoticeSideEffect.NavigateNoticeDetail) }
  fun popBackStack() = intent { postSideEffect(NoticeSideEffect.PopBackStack) }
}
