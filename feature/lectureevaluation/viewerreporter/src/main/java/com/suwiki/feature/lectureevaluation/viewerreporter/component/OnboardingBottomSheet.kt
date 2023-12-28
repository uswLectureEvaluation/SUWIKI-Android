package com.suwiki.feature.lectureevaluation.viewerreporter.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiBottomSheet
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.button.SuwikiOutlinedButton
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.feature.lectureevaluation.viewerreporter.LectureEvaluationState
import com.suwiki.feature.lectureevaluation.viewerreporter.R

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun OnboardingBottomSheet(
  uiState: LectureEvaluationState,
  hideOnboardingBottomSheet: () -> Unit,
  pagerState: PagerState,
  onClickLoginButton: () -> Unit = {},
  onClickSignupButton: () -> Unit = {},
) {
  SuwikiBottomSheet(
    sheetState = rememberModalBottomSheetState(
      skipPartiallyExpanded = true,
    ),
    isSheetOpen = uiState.showOnboardingBottomSheet,
    onDismissRequest = hideOnboardingBottomSheet,
  ) {
    OnboardingBottomSheetContent(
      pagerState = pagerState,
      onClickLoginButton = onClickLoginButton,
      onClickSignupButton = onClickSignupButton,
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingBottomSheetContent(
  pagerState: PagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT }),
  onClickLoginButton: () -> Unit = {},
  onClickSignupButton: () -> Unit = {},
) {
  Column(
    modifier = Modifier.padding(top = 62.dp, bottom = 50.dp, start = 24.dp, end = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Image(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.ic_logo),
      contentDescription = stringResource(R.string.content_description_logo),
    )

    Spacer(modifier = Modifier.size(107.dp))

    OnboardingPager(pagerState)

    Spacer(modifier = Modifier.size(34.dp))

    OnboardingPagerIndicator(
      pageCount = ONBOARDING_PAGE_COUNT,
      pagerState = pagerState,
    )

    Spacer(modifier = Modifier.size(50.dp))

    SuwikiContainedLargeButton(text = stringResource(R.string.onboarding_button_signup), onClick = onClickSignupButton)
    Spacer(modifier = Modifier.size(12.dp))
    SuwikiOutlinedButton(text = stringResource(R.string.word_login), onClick = onClickLoginButton)
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun OnboardingBottomSheetContentPreview() {
  SuwikiTheme {
    OnboardingBottomSheetContent()
  }
}
