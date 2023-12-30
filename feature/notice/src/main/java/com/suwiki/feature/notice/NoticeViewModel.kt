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

  suspend fun loadNoticeList() {
    showLoadingScreen()
    viewModelScope.launch {
      getNoticeListUseCase(1)
        .onSuccess { notices ->
          intent { reduce { state.copy(noticeList = notices.toPersistentList()) } }
          hideLoadingScreen()
        }
        .onFailure {
          intent { reduce { state.copy(noticeList = persistentListOf(Notice())) } }
        }
    }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun navigateNoticeDetail() = intent { postSideEffect(NoticeSideEffect.NavigateNoticeDetail) }
  fun popBackStack() = intent { postSideEffect(NoticeSideEffect.PopBackStack) }
}
