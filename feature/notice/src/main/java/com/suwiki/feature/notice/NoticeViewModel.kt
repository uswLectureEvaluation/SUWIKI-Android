package com.suwiki.feature.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.notice.Notice
import com.suwiki.domain.notice.usecase.GetNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
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

  fun loadNoticeList() = intent {
    showLoadingScreen()
    getNoticeListUseCase(1)
      .onSuccess { notices ->
        reduce { state.copy(noticeList = notices.toPersistentList()) }
      }
      .onFailure {
        postSideEffect(NoticeSideEffect.HandleException(it))
      }
    hideLoadingScreen()
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun navigateNoticeDetail(noticeId: Long) = intent { postSideEffect(NoticeSideEffect.NavigateNoticeDetail(noticeId)) }
  fun popBackStack() = intent { postSideEffect(NoticeSideEffect.PopBackStack) }
}
