package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiEvaluationAppBar
import com.suwiki.core.designsystem.component.card.SuwikiClassReviewCard
import com.suwiki.core.designsystem.component.searchbar.SuwikiSearchBarWithFilter
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.lectureevaluation.viewerreporter.component.ONBOARDING_PAGE_COUNT
import com.suwiki.feature.lectureevaluation.viewerreporter.component.OnboardingBottomSheet
import com.suwiki.feature.lectureevaluation.viewerreporter.model.LectureEvaluation
import kotlinx.collections.immutable.PersistentList
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
  allOpenMajorListState: LazyListState = rememberLazyListState(),
  pagerState: PagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT }),
  hideOnboardingBottomSheet: () -> Unit = {},
  onClickLoginButton: () -> Unit = {},
  onClickSignupButton: () -> Unit = {},
  onClickTempText: (String) -> Unit = {}, // TODO 개설학과 선택 페이지로 임시로 넘어가기 위한 람다입니다. 마음대로 삭제 가능.
) {
  var normalValue by remember {
    mutableStateOf("")
  }
  Box(
    modifier = Modifier
      .background(White),
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
    ) {
      SuwikiEvaluationAppBar(
        title = "강의평가",
        major = uiState.selectedOpenMajor,
        onClickMajor = { onClickTempText(uiState.selectedOpenMajor) },
      )
      SuwikiSearchBarWithFilter(
        placeHolder = "강의명 혹은 교수명을 검색하세요",
        value = normalValue,
        onValueChange = { normalValue = it },
        onClickClearButton = { normalValue = "" },
      )
      Text(
        modifier = Modifier
          .padding(start = 24.dp, top = 10.dp)
          .fillMaxSize(),
        text = "최근 올라온 강의",
        style = SuwikiTheme.typography.body2,
        color = Gray95,
      )
      LectureEvaluationLazyColumn(
        listState = allOpenMajorListState,
        openMajorList = uiState.filteredAllOpenMajorList,
      )
    }
    OnboardingBottomSheet(
      uiState = uiState,
      hideOnboardingBottomSheet = hideOnboardingBottomSheet,
      pagerState = pagerState,
      onClickLoginButton = onClickLoginButton,
      onClickSignupButton = onClickSignupButton,
    )
  }
}

@Composable
private fun LectureEvaluationLazyColumn(
  listState: LazyListState,
  openMajorList: PersistentList<LectureEvaluation>,
  onClickOpenLectureEvaluationDetail: (String) -> Unit = {},
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    state = listState,
    contentPadding = PaddingValues(top = 12.dp),
  ) {
    items(
      items = openMajorList,
      key = { it.id },
    ) { openMajor ->
      with(openMajor) {
        SuwikiClassReviewCard(
          modifier = Modifier,
          className = "강의명",
          openMajor = "개설학과",
          professor = "교수명",
          rating = 4.0f,
          reviewCount = 3,
          classType = "강의 유형",
          onClick = { onClickOpenLectureEvaluationDetail },
        )
      }
    }
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
