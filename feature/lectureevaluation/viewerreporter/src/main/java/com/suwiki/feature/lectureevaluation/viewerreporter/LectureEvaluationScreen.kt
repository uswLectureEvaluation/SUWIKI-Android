package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiEvaluationAppBar
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiAlignBottomSheet
import com.suwiki.core.designsystem.component.card.SuwikiClassReviewCard
import com.suwiki.core.designsystem.component.searchbar.SuwikiSearchBarWithFilter
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.core.ui.extension.OnBottomReached
import com.suwiki.feature.lectureevaluation.viewerreporter.component.ONBOARDING_PAGE_COUNT
import com.suwiki.feature.lectureevaluation.viewerreporter.component.OnboardingBottomSheet
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
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

  val pagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT })
  val allLectureEvaluationListState = rememberLazyListState()
  if (uiState.selectedFilter.isEmpty()) viewModel.updateAlignItem("최근 올라온 강의")
  LaunchedEffect(key1 = viewModel) {
    viewModel.checkLoggedInShowBottomSheetIfNeed()
  }

  LaunchedEffect(key1 = selectedOpenMajor) {
    viewModel.updateSelectedOpenMajor(selectedOpenMajor)
  }

  allLectureEvaluationListState.OnBottomReached {
    viewModel.getLectureEvaluationList(selectedOpenMajor)
  }

  LectureEvaluationScreen(
    padding = padding,
    uiState = uiState,
    allLectureEvaluationListState = allLectureEvaluationListState,
    pagerState = pagerState,
    hideOnboardingBottomSheet = viewModel::hideOnboardingBottomSheet,
    hideAlignBottomSheet = viewModel::hideAlignBottomSheet,
    showAlignBottomSheet = { viewModel.showAlignBottomSheet() },
    onClickLoginButton = {
      viewModel.hideOnboardingBottomSheet()
      viewModel.navigateLogin()
    },
    onClickSelectedOpenMajor = navigateOpenMajor,
    onValueChangeSearchBar = viewModel::updateSearchValue,
    onClickSearchBarClearButton = { viewModel.updateSearchValue("") },
    onClickAlignBottomSelectedItem = {
      viewModel.updateAlignItem(it)
    },
    selectedItem = uiState.selectedFilter,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectureEvaluationScreen(
  padding: PaddingValues,
  uiState: LectureEvaluationState,
  allLectureEvaluationListState: LazyListState = rememberLazyListState(),
  pagerState: PagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT }),
  hideOnboardingBottomSheet: () -> Unit = {},
  hideAlignBottomSheet: () -> Unit = {},
  showAlignBottomSheet: () -> Unit = {},
  onClickLoginButton: () -> Unit = {},
  onClickSignupButton: () -> Unit = {},
  onClickAlignBottomSelectedItem: (String) -> Unit = {},
  onValueChangeSearchBar: (String) -> Unit = {},
  onClickSearchBarClearButton: () -> Unit = {},
  onClickSelectedOpenMajor: (String) -> Unit = {},
  selectedItem: String,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(padding),
  ) {
    SuwikiEvaluationAppBar(
      title = stringResource(R.string.word_lecture_evaluation),
      major = uiState.selectedOpenMajor,
      onClickMajor = { onClickSelectedOpenMajor(uiState.selectedOpenMajor) },
    )
    SuwikiSearchBarWithFilter(
      placeHolder = stringResource(R.string.word_search_placeholder),
      value = uiState.searchValue,
      onValueChange = onValueChangeSearchBar,
      onClickClearButton = onClickSearchBarClearButton,
      onClickFilterButton = showAlignBottomSheet,
    )
    Text(
      modifier = Modifier
        .padding(start = 24.dp, top = 10.dp),
      text = uiState.selectedFilter,
      style = SuwikiTheme.typography.body2,
      color = Gray95,
    )
    LectureEvaluationLazyColumn(
      listState = allLectureEvaluationListState,
      openLectureEvaluationInfoList = uiState.lectureEvaluationList,
    )
  }
  OnboardingBottomSheet(
    uiState = uiState,
    hideOnboardingBottomSheet = hideOnboardingBottomSheet,
    pagerState = pagerState,
    onClickLoginButton = onClickLoginButton,
    onClickSignupButton = onClickSignupButton,
  )

  SuwikiAlignBottomSheet(
    isSheetOpen = uiState.showAlignBottomSheet,
    hideAlignBottomSheet = hideAlignBottomSheet,
    onClickAlignBottomSheetItem = onClickAlignBottomSelectedItem,
    itemList = persistentListOf(
      "최근 올라온 강의",
      "꿀 강의",
      "만족도 높은 강의",
      "배울게 많은 강의",
      "BEST 강의",
    ),
    bottomSheetTitle = stringResource(R.string.word_sort),
    selectedItem = selectedItem,
  )
}

@Composable
private fun LectureEvaluationLazyColumn(
  listState: LazyListState,
  openLectureEvaluationInfoList: PersistentList<LectureEvaluationAverage?>,
  onClickOpenLectureEvaluationDetail: (String) -> Unit = {},
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(start = 24.dp, end = 24.dp, top = 15.dp),
    state = listState,
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    items(
      items = openLectureEvaluationInfoList,
      key = { it!!.id },
    ) { lectureEvaluation ->
      with(lectureEvaluation) {
        SuwikiClassReviewCard(
          modifier = Modifier,
          className = this!!.lectureInfo.lectureName,
          openMajor = lectureInfo.majorType,
          professor = lectureInfo.professor,
          rating = lectureTotalAvg,
          reviewCount = null,
          classType = lectureInfo.lectureType ?: "",
          onClick = { onClickOpenLectureEvaluationDetail(id.toString()) },
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
      selectedItem = "",
    )
  }
}
