package com.suwiki.feature.lectureevaluation.my

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.container.SuwikiEditContainer
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.tabbar.SuwikiTabBar
import com.suwiki.core.designsystem.component.tabbar.TabTitle
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.core.ui.extension.OnBottomReached
import com.suwiki.core.ui.extension.collectWithLifecycle
import com.suwiki.core.ui.extension.encodeToUri
import com.suwiki.feature.lectureevaluation.my.model.MyEvaluationTab
import kotlinx.collections.immutable.PersistentList
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private val MY_EVALUATION_PAGE_COUNT = MyEvaluationTab.entries.size

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyEvaluationRoute(
  viewModel: MyEvaluationViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  navigateMyLectureEvaluation: (String) -> Unit = {},
  navigateMyExamEvaluation: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  val lectureEvaluationListState = rememberLazyListState()
  val examEvaluationListState = rememberLazyListState()

  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyEvaluationSideEffect.PopBackStack -> popBackStack()
      is MyEvaluationSideEffect.NavigateMyLectureEvaluation -> navigateMyLectureEvaluation(sideEffect.lectureEvaluation)
      is MyEvaluationSideEffect.NavigateMyExamEvaluation -> navigateMyExamEvaluation(sideEffect.examEvaluation)
      is MyEvaluationSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }
  val pagerState = rememberPagerState(pageCount = { MY_EVALUATION_PAGE_COUNT })

  lectureEvaluationListState.OnBottomReached {
    viewModel.getMyLectureEvaluations()
  }

  examEvaluationListState.OnBottomReached {
    viewModel.getMyExamEvaluations()
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  LaunchedEffect(key1 = uiState.currentTabPage) {
    pagerState.animateScrollToPage(uiState.currentTabPage)
  }

  snapshotFlow { pagerState.currentPage }.collectWithLifecycle {
    viewModel.syncPager(it)
  }

  MyEvaluationScreen(
    uiState = uiState,
    pagerState = pagerState,
    lectureEvaluationListState = lectureEvaluationListState,
    examEvaluationListState = examEvaluationListState,
    onClickTab = viewModel::syncPager,
    onClickBack = viewModel::popBackStack,
    onClickLectureEvaluationEditButton = viewModel::navigateMyLectureEvaluation,
    onClickExamEvaluationEditButton = viewModel::navigateMyExamEvaluation,
    onClickExamEvaluationDeleteButton = viewModel::showExamDeleteOrLackPointDialog,
    onDismissExamEvaluationDelete = viewModel::hideExamEvaluationDeleteDialog,
    onDismissLackPoint = viewModel::hideLackPointDialog,
    onClickExamEvaluationDeleteConfirm = {
      viewModel.deleteExamEvaluation()
      viewModel.hideExamEvaluationDeleteDialog()
    },
    onClickLectureEvaluationDeleteConfirm = {
      viewModel.deleteLectureEvaluation()
      viewModel.hideLectureEvaluationDeleteDialog()
    },
    onClickLectureEvaluationDeleteButton = viewModel::showLectureDeleteOrLackPointDialog,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyEvaluationScreen(
  uiState: MyEvaluationState,
  pagerState: PagerState = rememberPagerState(pageCount = { MY_EVALUATION_PAGE_COUNT }),
  lectureEvaluationListState: LazyListState = rememberLazyListState(),
  examEvaluationListState: LazyListState = rememberLazyListState(),
  onClickTab: (Int) -> Unit = {},
  onClickBack: () -> Unit = {},
  onClickLectureEvaluationEditButton: (String) -> Unit = {},
  onClickLectureEvaluationDeleteButton: (Long) -> Unit = {},
  onClickExamEvaluationEditButton: (String) -> Unit = {},
  onDismissExamEvaluationDelete: () -> Unit = {},
  onDismissLackPoint: () -> Unit = {},
  onClickExamEvaluationDeleteConfirm: () -> Unit = {},
  onClickExamEvaluationDeleteButton: (Long) -> Unit = {},
  onClickLectureEvaluationDeleteConfirm: () -> Unit = {},
  onDismissLectureEvaluationDelete: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .background(White)
      .fillMaxSize(),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.word_manage_my_evaluation),
      showCloseIcon = false,
      showBackIcon = true,
      onClickBack = onClickBack,
    )
    SuwikiTabBar(
      selectedTabPosition = pagerState.currentPage,
    ) {
      MyEvaluationTab.entries.forEach { tab ->
        with(tab) {
          TabTitle(
            title = stringResource(title),
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
      when (MyEvaluationTab.entries[page]) {
        MyEvaluationTab.LECTURE_EVALUATION -> {
          MyLectureEvaluationLazyColumn(
            itemList = uiState.myLectureEvaluationList,
            listState = lectureEvaluationListState,
            onClickLectureEditButton = onClickLectureEvaluationEditButton,
            onClickDeleteButton = onClickLectureEvaluationDeleteButton,
          )
        }

        MyEvaluationTab.EXAM_INFO -> {
          MyExamEvaluationLazyColumn(
            itemList = uiState.myExamEvaluationList,
            listState = examEvaluationListState,
            onClickExamEditButton = onClickExamEvaluationEditButton,
            onClickDeleteButton = onClickExamEvaluationDeleteButton,
          )
        }
      }
    }
  }

  if (uiState.showDeleteExamEvaluationDialog) {
    SuwikiDialog(
      headerText = stringResource(R.string.delete_dialog_header),
      bodyText = stringResource(R.string.delete_dialog_body, uiState.point),
      confirmButtonText = stringResource(R.string.word_delete),
      dismissButtonText = stringResource(R.string.word_cancel),
      onDismissRequest = onDismissExamEvaluationDelete,
      onClickConfirm = onClickExamEvaluationDeleteConfirm,
      onClickDismiss = onDismissExamEvaluationDelete,
    )
  }

  if (uiState.showLackPointDialog) {
    SuwikiDialog(
      headerText = stringResource(R.string.lack_point_dialog_header),
      bodyText = stringResource(R.string.lack_point_dialog_body, uiState.point),
      confirmButtonText = stringResource(R.string.word_confirm),
      onDismissRequest = onDismissLackPoint,
      onClickConfirm = onDismissLackPoint,
    )
  }

  if (uiState.showDeleteLectureEvaluationDialog) {
    SuwikiDialog(
      headerText = stringResource(R.string.delete_dialog_header),
      bodyText = stringResource(R.string.delete_dialog_body, uiState.point),
      confirmButtonText = stringResource(R.string.word_delete),
      dismissButtonText = stringResource(R.string.word_cancel),
      onDismissRequest = onDismissLectureEvaluationDelete,
      onClickConfirm = onClickLectureEvaluationDeleteConfirm,
      onClickDismiss = onDismissLectureEvaluationDelete,
    )
  }

  if (uiState.isLoading) {
    LoadingScreen()
  }
}

@Composable
fun MyLectureEvaluationLazyColumn(
  modifier: Modifier = Modifier,
  itemList: PersistentList<MyLectureEvaluation>,
  listState: LazyListState,
  onClickLectureEditButton: (String) -> Unit = {},
  onClickDeleteButton: (Long) -> Unit = {},
) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    state = listState,
  ) {
    items(
      items = itemList,
      key = {
        it.id
      },
    ) { item ->
      SuwikiEditContainer(
        semester = item.selectedSemester,
        name = item.lectureInfo.lectureName,
        onClickEditButton = { onClickLectureEditButton(Json.encodeToUri(item)) },
        onClickDeleteButton = { onClickDeleteButton(item.id) },
      )
    }
  }
}

@Composable
fun MyExamEvaluationLazyColumn(
  modifier: Modifier = Modifier,
  itemList: PersistentList<MyExamEvaluation>,
  listState: LazyListState,
  onClickExamEditButton: (String) -> Unit = {},
  onClickDeleteButton: (Long) -> Unit = {},
) {
  LazyColumn(
    modifier = modifier.fillMaxSize(),
    state = listState,
  ) {
    items(
      items = itemList,
      key = {
        it.id
      },
    ) { item ->
      val (examSemester, examName) = item.selectedSemester to item.lectureName

      SuwikiEditContainer(
        semester = examSemester,
        name = examName,
        onClickEditButton = { onClickExamEditButton(Json.encodeToUri(item)) },
        onClickDeleteButton = { onClickDeleteButton(item.id) },
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showSystemUi = true)
@Composable
fun MyEvaluationPreview() {
  var currentPage by remember { mutableIntStateOf(0) }
  SuwikiTheme {
    MyEvaluationScreen(
      uiState = MyEvaluationState(
        currentTabPage = currentPage,
      ),
      onClickTab = { currentPage = it },
    )
  }
}
