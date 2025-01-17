package com.suwiki.presentation.lectureevaluation.viewerrepoter

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.common.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.presentation.common.designsystem.component.bottomsheet.SuwikiAgreementBottomSheet
import com.suwiki.presentation.common.designsystem.component.bottomsheet.SuwikiSelectBottomSheet
import com.suwiki.presentation.common.designsystem.component.loading.LoadingScreen
import com.suwiki.presentation.common.designsystem.theme.Gray95
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.common.ui.extension.OnBottomReached
import com.suwiki.presentation.common.ui.extension.lectureAlignList
import com.suwiki.presentation.common.ui.extension.toText
import com.suwiki.presentation.common.ui.util.PRIVACY_POLICY_SITE
import com.suwiki.presentation.common.ui.util.TERMS_SITE
import com.suwiki.presentation.lectureevaluation.R
import com.suwiki.presentation.lectureevaluation.viewerrepoter.component.EvaluationAppBar
import com.suwiki.presentation.lectureevaluation.viewerrepoter.component.EvaluationCard
import com.suwiki.presentation.lectureevaluation.viewerrepoter.component.EvaluationSearchBar
import com.suwiki.presentation.lectureevaluation.viewerrepoter.component.ONBOARDING_PAGE_COUNT
import com.suwiki.presentation.lectureevaluation.viewerrepoter.component.OnboardingBottomSheet
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
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
  navigateLectureEvaluationDetail: (String) -> Unit,
  navigateOpenMajor: (String) -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value

  val uriHandler = LocalUriHandler.current

  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      LectureEvaluationSideEffect.NavigateLogin -> navigateLogin()
      LectureEvaluationSideEffect.NavigateSignUp -> navigateSignUp()
      LectureEvaluationSideEffect.OpenPersonalPolicyWebSite -> uriHandler.openUri(PRIVACY_POLICY_SITE)
      LectureEvaluationSideEffect.OpenTermWebSite -> uriHandler.openUri(TERMS_SITE)
      LectureEvaluationSideEffect.ScrollToTop -> scope.launch {
        awaitFrame()
        listState.animateScrollToItem(0)
      }

      is LectureEvaluationSideEffect.HandleException -> handleException(sideEffect.throwable)
      is LectureEvaluationSideEffect.NavigateOpenMajor -> navigateOpenMajor(sideEffect.selectedOpenMajor)
      is LectureEvaluationSideEffect.NavigateLectureEvaluationDetail -> navigateLectureEvaluationDetail(sideEffect.id)
    }
  }

  val pagerState = rememberPagerState(pageCount = { ONBOARDING_PAGE_COUNT })

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
    onClickTermCheckIcon = viewModel::toggleTermChecked,
    onClickTermArrowIcon = viewModel::openTermWebSite,
    onClickPersonalCheckIcon = viewModel::togglePersonalPolicyChecked,
    onClickPersonalArrowIcon = viewModel::openPersonalPolicyWebSite,
    onClickLectureEvaluationItem = viewModel::navigateLectureEvaluationDetailIfLoggedIn,
    onClickAgreementButton = {
      viewModel.hideAgreementBottomSheet()
      viewModel.hideOnboardingBottomSheet()
      viewModel.navigateSignup()
    },
    onClickSelectedOpenMajor = viewModel::navigateOpenMajor,
    onValueChangeSearchBar = viewModel::updateSearchValue,
    onClickSearchButton = viewModel::searchLectureEvaluation,
    onClickSearchBarClearButton = {
      viewModel.updateSearchValue("")
    },
    onClickAlignBottomSelectedItem = {
      viewModel.hideAlignBottomSheet()
      viewModel.updateAlignItem(it)
    },
  )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
  onClickSearchButton: (String) -> Unit = {},
  onClickSearchBarClearButton: () -> Unit = {},
  onClickSelectedOpenMajor: (String) -> Unit = {},
  onClickTermCheckIcon: () -> Unit = {},
  onClickTermArrowIcon: () -> Unit = {},
  onClickPersonalCheckIcon: () -> Unit = {},
  onClickPersonalArrowIcon: () -> Unit = {},
  onClickAgreementButton: () -> Unit = {},
  onClickLectureEvaluationItem: (String) -> Unit = {},
) {
  val state = rememberCollapsingToolbarScaffoldState()

  CollapsingToolbarScaffold(
    modifier = Modifier
      .fillMaxSize()
      .padding(padding),
    state = state,
    scrollStrategy = ScrollStrategy.EnterAlways,
    toolbar = {
      Column {
        EvaluationAppBar(
          title = stringResource(R.string.word_lecture_evaluation),
          major = uiState.selectedOpenMajor,
          onClickMajor = { onClickSelectedOpenMajor(uiState.selectedOpenMajor) },
        )
        EvaluationSearchBar(
          placeHolder = stringResource(R.string.word_search_placeholder),
          value = uiState.searchValue,
          onValueChange = onValueChangeSearchBar,
          onClickClearButton = onClickSearchBarClearButton,
          onClickFilterButton = showAlignBottomSheet,
          onClickSearchButton = onClickSearchButton,
        )
        Text(
          modifier = Modifier
            .padding(start = 24.dp, top = 10.dp),
          text = uiState.alignValue.toText(),
          style = SuwikiTheme.typography.body2,
          color = Gray95,
        )
      }
    },
  ) {
    if (uiState.showSearchEmptyResultScreen) {
      EmptyText(stringResource(R.string.word_empty_search_result))
    }

    LectureEvaluationLazyColumn(
      listState = listState,
      openLectureEvaluationInfoList = uiState.lectureEvaluationList,
      onClickOpenLectureEvaluationDetail = onClickLectureEvaluationItem,
    )
  }

  if (uiState.isLoading) {
    LoadingScreen()
  }

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

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showAlignBottomSheet,
    onDismissRequest = hideAlignBottomSheet,
    onClickItem = onClickAlignBottomSelectedItem,
    itemList = lectureAlignList,
    title = stringResource(R.string.word_sort),
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
      .padding(horizontal = 24.dp),
    contentPadding = PaddingValues(top = 15.dp, bottom = 24.dp),
    state = listState,
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    items(
      items = openLectureEvaluationInfoList,
      key = { it!!.id },
    ) { lectureEvaluation ->
      with(lectureEvaluation) {
        EvaluationCard(
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
