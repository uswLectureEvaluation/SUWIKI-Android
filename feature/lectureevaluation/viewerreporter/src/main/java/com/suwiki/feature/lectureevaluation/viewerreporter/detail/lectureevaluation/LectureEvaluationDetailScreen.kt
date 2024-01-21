package com.suwiki.feature.lectureevaluation.viewerreporter.detail.lectureevaluation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.container.LabelColor
import com.suwiki.core.designsystem.component.container.SuwikiExamReviewContainer
import com.suwiki.core.designsystem.component.container.SuwikiReviewStatisticsContainer
import com.suwiki.core.designsystem.component.container.SuwikiUserReviewContainer
import com.suwiki.core.designsystem.component.tabbar.SuwikiTabBar
import com.suwiki.core.designsystem.component.tabbar.TabTitle
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.enums.LectureEvaluationTab
import com.suwiki.core.ui.extension.collectWithLifecycle
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.lectureevaluation.viewerreporter.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private val LECTURE_EVALUATION_PAGE_COUNT = LectureEvaluationTab.entries.size

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectureEvaluationDetailRoute(
  viewModel: LectureEvaluationDetailViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  handleException: (Throwable) -> Unit = {},
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is LectureEvaluationDetailSideEffect.PopBackStack -> popBackStack()
      is LectureEvaluationDetailSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }
  val pagerState = rememberPagerState(pageCount = { LECTURE_EVALUATION_PAGE_COUNT })

  LaunchedEffect(key1 = Unit) {
    viewModel.getLectureEvaluationDetail()
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
    onClickBack = viewModel::popBackStack,
    onClickTab = viewModel::syncPager,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LectureEvaluationDetailScreen(
  uiState: LectureEvaluationDetailState = LectureEvaluationDetailState(),
  pagerState: PagerState = rememberPagerState(pageCount = { LECTURE_EVALUATION_PAGE_COUNT }),
  onClickBack: () -> Unit = {},
  onClickTab: (Int) -> Unit = {},
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(White)
  ) {
    LazyColumn {
      stickyHeader {
        SuwikiAppBarWithTitle(
          showBackIcon = true,
          showCloseIcon = false,
          onClickBack = onClickBack,
        )
      }
      item {
        SuwikiReviewStatisticsContainer(
          lectureType = uiState.lectureInfo.lectureType ?: "강의유형",
          lectureName = uiState.lectureInfo.lectureName,
          openMajor = uiState.lectureInfo.majorType,
          professor = uiState.lectureInfo.professor,
          reviewCount = 3,
          rating = uiState.lectureTotalAvg,
          honeyRating = uiState.lectureHoneyAvg,
          learningRating = uiState.lectureLearningAvg,
          satisfactionRating = uiState.lectureSatisfactionAvg,
          grade = "label",
          gradeLabelColor = LabelColor.BLUE,
          homework = "label",
          homeworkLabelColor = LabelColor.GREEN,
          team = "label",
          teamLabelColor = LabelColor.ORANGE,
        )
      }
      stickyHeader {
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
      item {
        HorizontalPager(
          state = pagerState,
        ) { pager ->
          when (LectureEvaluationTab.entries[pager]) {
            LectureEvaluationTab.LECTURE_EVALUATION -> {
              val evaluations = listOf("1", "2", "3", "4", "5", "6")
              Column(
                modifier = Modifier.fillMaxSize(),
              ) {
                evaluations.forEach { evaluation ->
                  SuwikiUserReviewContainer(
                    isAuthor = false,
                    text = evaluation,
                  )
                }
              }
            }
            LectureEvaluationTab.EXAM_INFO -> {
              val evaluations = listOf("a", "b", "c", "d", "e", "f")
              Column(
                modifier = Modifier.fillMaxSize(),
              ) {
                evaluations.forEach { evaluation ->
                  SuwikiExamReviewContainer(
                    isAuthor = false,
                    difficulty = "어려움",
                    examType = "응용,실습,과제,PPT",
                    text = evaluation,
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
