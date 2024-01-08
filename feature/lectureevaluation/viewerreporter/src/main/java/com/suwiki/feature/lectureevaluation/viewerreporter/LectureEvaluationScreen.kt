package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiEvaluationAppBar
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiAlignBottomSheet
import com.suwiki.core.designsystem.component.card.SuwikiClassReviewCard
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.searchbar.SuwikiSearchBarWithFilter
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiAgreementBottomSheet
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.core.ui.extension.OnBottomReached
import com.suwiki.core.ui.extension.lectureAlignList
import com.suwiki.core.ui.extension.toText
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.core.ui.util.PRIVACY_POLICY_SITE
import com.suwiki.core.ui.util.TERMS_SITE
import com.suwiki.feature.lectureevaluation.viewerreporter.component.ONBOARDING_PAGE_COUNT
import com.suwiki.feature.lectureevaluation.viewerreporter.component.OnboardingBottomSheet
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
  navigateSignUp: () -> Unit,
  navigateOpenMajor: (String) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  val uriHandler = LocalUriHandler.current
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      LectureEvaluationSideEffect.NavigateLogin -> navigateLogin()
      LectureEvaluationSideEffect.NavigateSignUp -> navigateSignUp()
      LectureEvaluationSideEffect.OpenPersonalPolicyWebSite -> uriHandler.openUri(PRIVACY_POLICY_SITE)
      LectureEvaluationSideEffect.OpenTermWebSite -> uriHandler.openUri(TERMS_SITE)
    }
  }

  val pagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT })
  val listState = rememberLazyListState()

  LaunchedEffect(key1 = viewModel) {
    viewModel.initData()
  }

  LaunchedEffect(selectedOpenMajor) {
    viewModel.updateSelectedOpenMajor(selectedOpenMajor)
  }

  listState.OnBottomReached {
    viewModel.getLectureEvaluationList(needClear = false)
  }

  LectureEvaluationScreen(
    padding = padding,
    uiState = uiState,
    listState = listState,
    pagerState = pagerState,
    hideOnboardingBottomSheet = viewModel::hideOnboardingBottomSheet,
    hideAgreementBottomSheet = viewModel::hideAgreementBottomSheet,
    hideAlignBottomSheet = viewModel::hideAlignBottomSheet,
    showAlignBottomSheet = { viewModel.showAlignBottomSheet() },
    onClickLoginButton = {
      viewModel.hideOnboardingBottomSheet()
      viewModel.navigateLogin()
    },
    onClickSignupButton = viewModel::showAgreementBottomSheet,
    onClickTempText = navigateOpenMajor,
    onClickTermCheckIcon = viewModel::toggleTermChecked,
    onClickTermArrowIcon = viewModel::openTermWebSite,
    onClickPersonalCheckIcon = viewModel::togglePersonalPolicyChecked,
    onClickPersonalArrowIcon = viewModel::openPersonalPolicyWebSite,
    onClickAgreementButton = {
      viewModel.hideAgreementBottomSheet()
      viewModel.hideOnboardingBottomSheet()
      viewModel.navigateSignup()
    },
    onClickSelectedOpenMajor = navigateOpenMajor,
    onValueChangeSearchBar = {
      viewModel.updateSearchValue(it)
    },
    onClickSearchBarClearButton = {
      viewModel.updateSearchValue("")
    },
    onClickAlignBottomSelectedItem = viewModel::updateAlignItem,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectureEvaluationScreen(
  padding: PaddingValues,
  uiState: LectureEvaluationState,
  listState: LazyListState = rememberLazyListState(),
  pagerState: PagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT }),
  hideOnboardingBottomSheet: () -> Unit = {},
  hideAlignBottomSheet: () -> Unit = {},
  showAlignBottomSheet: () -> Unit = {},
  hideAgreementBottomSheet: () -> Unit = {},
  onClickLoginButton: () -> Unit = {},
  onClickSignupButton: () -> Unit = {},
  onClickAlignBottomSelectedItem: (Int) -> Unit = {},
  onValueChangeSearchBar: (String) -> Unit = {},
  onClickSearchBarClearButton: () -> Unit = {},
  onClickSelectedOpenMajor: (String) -> Unit = {},
  onClickTermCheckIcon: () -> Unit = {},
  onClickTermArrowIcon: () -> Unit = {},
  onClickPersonalCheckIcon: () -> Unit = {},
  onClickPersonalArrowIcon: () -> Unit = {},
  onClickAgreementButton: () -> Unit = {},
  onClickTempText: (String) -> Unit = {}, // TODO 개설학과 선택 페이지로 임시로 넘어가기 위한 람다입니다. 마음대로 삭제 가능.
) {
  val textState = remember {
    mutableStateOf(uiState.searchValue)
  }

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
      value = textState.value,
      onValueChange = { textState.value = it },
      onClickClearButton = {
        onClickSearchBarClearButton()
        textState.value = ""
      },
      onClickFilterButton = showAlignBottomSheet,
      onClickSearchButton = {
        onValueChangeSearchBar(textState.value)
      },
    )
    Text(
      modifier = Modifier
        .padding(start = 24.dp, top = 10.dp),
      text = uiState.alignValue.toText(),
      style = SuwikiTheme.typography.body2,
      color = Gray95,
    )
    if (uiState.showSearchEmptyResultScreen) {
      EmptyText(stringResource(R.string.word_empty_search_result))
    } else {
      LectureEvaluationLazyColumn(
        listState = listState,
        openLectureEvaluationInfoList = uiState.lectureEvaluationList,
      )
    }
  }

  if(uiState.isLoading) {
    LoadingScreen()
  }

    OnboardingBottomSheet(
      uiState = uiState,
      hideOnboardingBottomSheet = hideOnboardingBottomSheet,
      pagerState = pagerState,
      onClickLoginButton = onClickLoginButton,
      onClickSignupButton = onClickSignupButton,
    )

    SuwikiAgreementBottomSheet(
      isSheetOpen = uiState.showAgreementBottomSheet,
      buttonEnabled = uiState.isEnabledAgreementButton,
      isCheckedTerm = uiState.isCheckedTerm,
      onClickTermCheckIcon = onClickTermCheckIcon,
      onClickTermArrowIcon = onClickTermArrowIcon,
      isCheckedPersonalPolicy = uiState.isCheckedPersonalPolicy,
      onClickPersonalCheckIcon = onClickPersonalCheckIcon,
      onClickPersonalArrowIcon = onClickPersonalArrowIcon,
      onClickAgreementButton = onClickAgreementButton,
      onDismissRequest = hideAgreementBottomSheet,
    )
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
    itemList = lectureAlignList,
    bottomSheetTitle = stringResource(R.string.word_sort),
    selectedPosition = uiState.selectedAlignPosition,
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
          classType = lectureInfo.lectureType ?: "",
          onClick = { onClickOpenLectureEvaluationDetail(id.toString()) },
        )
      }
    }
  }
}

@Composable
private fun EmptyText(
  text: String = "",
) {
  Text(
    modifier = Modifier
      .padding(52.dp)
      .fillMaxSize(),
    textAlign = TextAlign.Center,
    text = text,
    style = SuwikiTheme.typography.header4,
    color = Gray95,
  )
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
