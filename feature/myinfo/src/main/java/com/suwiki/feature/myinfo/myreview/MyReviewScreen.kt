package com.suwiki.feature.myinfo.myreview

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.container.SuwikiReviewEditContainer
import com.suwiki.core.designsystem.component.tabbar.SuwikiTabBar
import com.suwiki.core.designsystem.component.tabbar.TabTitle
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.core.ui.extension.collectWithLifecycle
import com.suwiki.feature.myinfo.R
import com.suwiki.feature.myinfo.myreview.model.MyReviewTab
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private val MY_REVIEW_PAGE_COUNT = MyReviewTab.entries.size

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyReviewRoute(
  padding: PaddingValues,
  viewModel: MyReviewViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  navigateMyClassReview: (Int) -> Unit = {},
  navigateMyTestReview: (Int) -> Unit = {},
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyReviewSideEffect.PopBackStack -> popBackStack()
      is MyReviewSideEffect.NavigateMyClassReview -> navigateMyClassReview(sideEffect.point)
      is MyReviewSideEffect.NavigateMyTestReview -> navigateMyTestReview(sideEffect.point)
    }
  }
  val pagerState = rememberPagerState(pageCount = { MY_REVIEW_PAGE_COUNT })

  LaunchedEffect(key1 = Unit) {
    viewModel.loadInitList()
  }

  LaunchedEffect(key1 = uiState.currentPage) {
    pagerState.animateScrollToPage(uiState.currentPage)
  }

  snapshotFlow { pagerState.currentPage }.collectWithLifecycle {
    viewModel.syncPager(it)
  }

  MyReviewScreen(
    padding = padding,
    uiState = uiState,
    pagerState = pagerState,
    onClickTab = viewModel::syncPager,
    onClickBack = viewModel::popBackStack,
    onClickClassReviewEditButton = viewModel::navigateMyClassReview,
    onClickTestReviewEditButton = viewModel::navigateMyTestReview,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyReviewScreen(
  padding: PaddingValues,
  uiState: MyReviewState,
  pagerState: PagerState = rememberPagerState(pageCount = { MY_REVIEW_PAGE_COUNT }),
  onClickTab: (Int) -> Unit = {},
  onClickBack: () -> Unit = {},
  onClickClassReviewEditButton: (Int) -> Unit = {},
  onClickTestReviewEditButton: (Int) -> Unit = {},
) {
  // TODO(REMOVE)
  val myLectureReviewList: PersistentList<String> = persistentListOf("머신러닝", "머신러닝", "과목명", "과목명")
  val myTestReviewList: PersistentList<String> = persistentListOf("머신러닝", "과목명", "과목명")
  Column(
    modifier = Modifier
      .padding(padding)
      .background(White)
      .fillMaxSize()
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.my_info_my_post),
      showCloseIcon = false,
      showBackIcon = true,
      onClickBack = onClickBack,
    )
    SuwikiTabBar(
      selectedTabPosition = pagerState.currentPage,
    ) {
      MyReviewTab.entries.forEach { tab ->
        with(tab) {
          TabTitle(
            title = stringResource(title,
              if (tab == MyReviewTab.LECTUREEVALUATION) uiState.myLectureEvaluationList.size
              else uiState.myExamEvaluationList.size
            ),
            position = position,
            selected = pagerState.currentPage == position,
            onClick = { onClickTab(position) },
          )
        }
      }
    }
    HorizontalPager(
      modifier = Modifier.weight(1f),
      state = pagerState,
    ) { page ->
      when (MyReviewTab.entries[page]) {
        MyReviewTab.LECTUREEVALUATION -> {
          MyReviewLazyColumn(
//            itemList = uiState.myLectureEvaluationList,
            itemList = myLectureReviewList,
            onClickEditButton = { onClickClassReviewEditButton(uiState.point) },
          )
        }
        MyReviewTab.TESTINFO -> {
          MyReviewLazyColumn(
//            itemList = uiState.myExamEvaluationList,
            itemList = myTestReviewList,
            onClickEditButton = { onClickTestReviewEditButton(uiState.point) },
          )
        }
      }
    }
  }
}

@Composable
fun MyReviewLazyColumn(
  modifier: Modifier = Modifier,
  itemList: PersistentList<*>,
  onClickEditButton: () -> Unit = {},
) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
  ) {
    items(items = itemList) { item ->
      when (item) {
        is MyLectureEvaluation -> {
          SuwikiReviewEditContainer(
            semesterText = item.selectedSemester,
            classNameText = item.content,
            onClickEditButton = onClickEditButton,
          )
        }
        is MyExamEvaluation -> {
          item.selectedSemester?.let {
            SuwikiReviewEditContainer(
              semesterText = it,
              classNameText = item.content,
              onClickEditButton = onClickEditButton,
            )
          }
        }
        // TODO(REMOVE)
        is String -> {
          SuwikiReviewEditContainer(
            semesterText = "학기",
            classNameText = item,
            onClickEditButton = onClickEditButton,
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showSystemUi = true)
@Composable
fun MyReviewPreview() {
  var currentPage by remember { mutableIntStateOf(0) }
  SuwikiTheme {
    MyReviewScreen(
      padding = PaddingValues(0.dp),
      uiState = MyReviewState(
        currentPage = currentPage,
      ),
      onClickTab = { currentPage = it },
    )
  }
}
