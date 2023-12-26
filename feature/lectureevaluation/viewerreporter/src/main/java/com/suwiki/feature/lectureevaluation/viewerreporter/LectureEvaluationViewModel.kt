package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LectureEvaluationViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
) : ContainerHost<LectureEvaluationState, LectureEvaluationSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationState, LectureEvaluationSideEffect> = container(LectureEvaluationState())

  // TODO 로그인 이후 네트워크 연결이 없는 상태에서 잘 동작하는지 테스트
  fun showOnboardingBottomSheetIfNeed() = intent {
    viewModelScope.launch {
      if (getUserInfoUseCase().catch { }.lastOrNull()?.isLoggedIn != true) showOnboardingBottomSheet()
    }
  }

  private fun showOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = true) } }
  fun hideOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = false) } }
}
