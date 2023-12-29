package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.lectureevaluation.viewerreporter.component.ONBOARDING_PAGE_COUNT
import com.suwiki.feature.lectureevaluation.viewerreporter.component.OnboardingBottomSheet
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectureEvaluationRoute(
  padding: PaddingValues,
  viewModel: LectureEvaluationViewModel = hiltViewModel(),
  selectedOpenMajor: String,
  navigateLogin: () -> Unit,
  navigateOpenMajor: (String) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      LectureEvaluationSideEffect.NavigateLogin -> navigateLogin()
      LectureEvaluationSideEffect.NavigateSignUp -> TODO()
    }
  }

  LaunchedEffect(key1 = viewModel) {
    viewModel.checkLoggedInShowBottomSheetIfNeed()
  }

  LaunchedEffect(key1 = selectedOpenMajor) {
    viewModel.updateSelectedOpenMajor(selectedOpenMajor)
  }

  val pagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT })

  LectureEvaluationScreen(
    padding = padding,
    uiState = uiState,
    pagerState = pagerState,
    hideOnboardingBottomSheet = viewModel::hideOnboardingBottomSheet,
    onClickLoginButton = {
      viewModel.hideOnboardingBottomSheet()
      viewModel.navigateLogin()
    },
    onClickTempText = navigateOpenMajor,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectureEvaluationScreen(
  padding: PaddingValues,
  uiState: LectureEvaluationState,
  pagerState: PagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT }),
  hideOnboardingBottomSheet: () -> Unit = {},
  onClickLoginButton: () -> Unit = {},
  onClickSignupButton: () -> Unit = {},
  onClickTempText: (String) -> Unit = {}, // TODO 개설학과 선택 페이지로 임시로 넘어가기 위한 람다입니다. 마음대로 삭제 가능.
) {
  Box(modifier = Modifier.fillMaxSize().padding(padding)) {
    Text(
      modifier = Modifier.suwikiClickable(
        onClick = { onClickTempText(uiState.selectedOpenMajor) },
      ),
      text = uiState.selectedOpenMajor,
    )

    OnboardingBottomSheet(
      uiState = uiState,
      hideOnboardingBottomSheet = hideOnboardingBottomSheet,
      pagerState = pagerState,
      onClickLoginButton = onClickLoginButton,
      onClickSignupButton = onClickSignupButton,
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LectureEvaluationScreenPreview() {
  SuwikiTheme {
    LectureEvaluationScreen(
      padding = PaddingValues(0.dp),
      uiState = LectureEvaluationState(),
    )
  }
}
