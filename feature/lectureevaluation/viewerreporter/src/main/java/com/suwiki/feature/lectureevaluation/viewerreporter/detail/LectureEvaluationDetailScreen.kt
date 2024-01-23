package com.suwiki.feature.lectureevaluation.viewerreporter.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.button.SuwikiOutlinedButton
import com.suwiki.core.designsystem.component.container.SuwikiExamReviewContainer
import com.suwiki.core.designsystem.component.container.SuwikiUserReviewContainer
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.tabbar.SuwikiTabBar
import com.suwiki.core.designsystem.component.tabbar.TabTitle
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.enums.LectureEvaluationTab
import com.suwiki.core.ui.extension.OnBottomReached
import com.suwiki.core.ui.extension.collectWithLifecycle
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.lectureevaluation.viewerreporter.R
import com.suwiki.feature.lectureevaluation.viewerreporter.detail.component.SuwikiReviewStatisticsContainer
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private val LECTURE_EVALUATION_PAGE_COUNT = LectureEvaluationTab.entries.size

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectureEvaluationDetailRoute(
  viewModel: LectureEvaluationDetailViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit,
  handleException: (Throwable) -> Unit = {},
) {
  val uiState = viewModel.collectAsState().value

  val lectureEvaluationListState = rememberLazyListState()
  val examEvaluationListState = rememberLazyListState()

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is LectureEvaluationDetailSideEffect.PopBackStack -> popBackStack()
      is LectureEvaluationDetailSideEffect.HandleException -> handleException(sideEffect.throwable)
      is LectureEvaluationDetailSideEffect.ShowLackPointToast -> onShowToast(sideEffect.msg)
    }
  }
  val pagerState = rememberPagerState(pageCount = { LECTURE_EVALUATION_PAGE_COUNT })

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  lectureEvaluationListState.OnBottomReached {
    viewModel.getLectureEvaluationList()
  }

  examEvaluationListState.OnBottomReached {
    viewModel.getExamEvaluationList()
  }

  LaunchedEffect(key1 = uiState.currentTabPage) {
    pagerState.animateScrollToPage(uiState.currentTabPage)
  }

  snapshotFlow { pagerState.currentPage }.collectWithLifecycle {
    viewModel.syncPager(it)
  }

  LectureEvaluationDetailScreen(
    uiState = uiState,
    pagerState = pagerState,
    lectureEvaluationListState = lectureEvaluationListState,
    examEvaluationListState = examEvaluationListState,
    onClickBack = viewModel::popBackStack,
    onClickTab = viewModel::syncPager,
    onClickBuyExamButton = viewModel::buyExam,
  )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalToolbarApi::class)
@Composable
fun LectureEvaluationDetailScreen(
  uiState: LectureEvaluationDetailState = LectureEvaluationDetailState(),
  pagerState: PagerState = rememberPagerState(pageCount = { LECTURE_EVALUATION_PAGE_COUNT }),
  lectureEvaluationListState: LazyListState = rememberLazyListState(),
  examEvaluationListState: LazyListState = rememberLazyListState(),
  onClickBack: () -> Unit = {},
  onClickTab: (Int) -> Unit = {},
  onClickBuyExamButton: () -> Unit = {},
) {
  val state = rememberCollapsingToolbarScaffoldState()

  Column {
    SuwikiAppBarWithTitle(
      showBackIcon = true,
      showCloseIcon = false,
      onClickBack = onClickBack,
    )

    CollapsingToolbarScaffold(
      modifier = Modifier
        .fillMaxSize()
        .background(White),
      state = state,
      scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
      toolbar = {
        Column {
          SuwikiReviewStatisticsContainer(
            data = uiState.lectureEvaluationExtraAverage,
          )

          SuwikiTabBar(
            selectedTabPosition = pagerState.currentPage,
          ) {
            LectureEvaluationTab.entries.forEach { tab ->
              with(tab) {
                TabTitle(
                  title = title,
                  position = position,
                  selected = pagerState.currentPage == position,
                  onClick = { onClickTab(position) },
                )
              }
            }
          }
        }
      },
    ) {
      HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
      ) { pager ->
        when (LectureEvaluationTab.entries[pager]) {
          LectureEvaluationTab.LECTURE_EVALUATION -> {
            if (uiState.lectureEvaluationList.isEmpty()) {
              Text(
                modifier = Modifier
                  .padding(52.dp)
                  .fillMaxSize(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.lecture_evaluation_detail_empty_data),
                style = SuwikiTheme.typography.header4,
                color = Gray95,
              )
            }

            LazyColumn(
              modifier = Modifier.fillMaxSize(),
              state = lectureEvaluationListState,
              contentPadding = PaddingValues(bottom = 100.dp),
            ) {
              items(
                items = uiState.lectureEvaluationList,
                key = { it.id },
              ) {
                SuwikiUserReviewContainer(
                  semester = it.selectedSemester,
                  content = it.content,
                  rating = it.totalAvg,
                )
              }
            }
          }

          LectureEvaluationTab.EXAM_INFO -> {
            when {
              uiState.needBuyExam -> {
                Box(modifier = Modifier.fillMaxSize()) {
                  SuwikiOutlinedButton(
                    modifier = Modifier
                      .padding(52.dp),
                    text = stringResource(R.string.lecture_evaluation_detail_screen_buy_exam),
                    onClick = onClickBuyExamButton,
                  )
                }
              }

              uiState.examEvaluationList.isEmpty() -> {
                Text(
                  modifier = Modifier
                    .padding(52.dp)
                    .fillMaxSize(),
                  textAlign = TextAlign.Center,
                  text = stringResource(R.string.lecture_evaluation_detail_empty_data),
                  style = SuwikiTheme.typography.header4,
                  color = Gray95,
                )
              }

              else -> {
                LazyColumn(
                  modifier = Modifier.fillMaxSize(),
                  state = examEvaluationListState,
                  contentPadding = PaddingValues(bottom = 100.dp),
                ) {
                  items(
                    items = uiState.examEvaluationList,
                    key = {
                      it.id
                    },
                  ) {
                    SuwikiExamReviewContainer(
                      difficulty = it.examDifficulty,
                      examType = it.examType,
                      content = it.content,
                      onClickButton = {},
                    )
                  }
                }
              }
            }
          }
        }
      }

      LectureEvaluationWriteButton(
        modifier = Modifier
          .padding(12.dp)
          .align(Alignment.BottomCenter),
        onClick = { /*TODO*/ },
      )
    }
  }

  if (uiState.isLoading) {
    LoadingScreen(
      modifier = Modifier
        .padding(top = 50.dp)
        .background(Color.White),
    )
  }
}

@Composable
fun LectureEvaluationWriteButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .clip(shape = RoundedCornerShape(10.dp))
      .suwikiClickable(onClick = onClick)
      .background(Primary)
      .padding(vertical = 8.dp, horizontal = 16.dp),
  ) {
    Icon(
      modifier = Modifier.align(Alignment.CenterVertically),
      painter = painterResource(R.drawable.ic_write),
      contentDescription = "",
      tint = White,
    )
    Text(
      text = "작성하기",
      color = White,
      style = SuwikiTheme.typography.body2,
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LectureEvaluationDetailScreenPreview() {
  var currentPage by remember { mutableIntStateOf(0) }
  SuwikiTheme {
    LectureEvaluationDetailScreen(
      uiState = LectureEvaluationDetailState(
        currentTabPage = currentPage,
      ),
      onClickTab = { currentPage = it },
    )
  }
}
