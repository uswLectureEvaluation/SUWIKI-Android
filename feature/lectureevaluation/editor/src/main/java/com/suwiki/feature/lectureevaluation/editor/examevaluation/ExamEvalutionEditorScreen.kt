package com.suwiki.feature.lectureevaluation.editor.examevaluation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.model.enums.ExamInfo
import com.suwiki.core.model.enums.ExamLevel
import com.suwiki.core.model.enums.ExamType
import com.suwiki.feature.common.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.feature.common.designsystem.component.bottomsheet.SuwikiSelectBottomSheet
import com.suwiki.feature.common.designsystem.component.button.SuwikiContainedMediumButton
import com.suwiki.feature.common.designsystem.component.chip.SuwikiOutlinedChip
import com.suwiki.feature.common.designsystem.component.container.SuwikiSelectionContainer
import com.suwiki.feature.common.designsystem.component.loading.LoadingScreen
import com.suwiki.feature.common.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White
import com.suwiki.feature.common.ui.extension.toText
import com.suwiki.feature.lectureevaluation.editor.R
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun ExamEvaluationEditorRoute(
  viewModel: ExamEvaluationEditorViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      ExamEvaluationEditorSideEffect.PopBackStack -> popBackStack()
      ExamEvaluationEditorSideEffect.ShowExamEvaluationDeleteToast -> {
        onShowToast(context.getString(R.string.exam_evaluation_delete_toast_msg))
      }

      is ExamEvaluationEditorSideEffect.HandleException -> handleException(sideEffect.throwable)
      ExamEvaluationEditorSideEffect.ShowInputMoreTextToast -> onShowToast(context.getString(R.string.toast_need_input_30))
      ExamEvaluationEditorSideEffect.ShowSelectExamTypeToast -> onShowToast(context.getString(R.string.toast_select_exam_type))
      ExamEvaluationEditorSideEffect.ShowSelectSemesterToast -> onShowToast(context.getString(R.string.toast_select_semester))
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  ExamEvaluationEditorScreen(
    scrollState = scrollState,
    uiState = uiState,
    popBackStack = viewModel::popBackStack,
    onClickSemesterButton = viewModel::showSemesterBottomSheet,
    onClickSemesterItem = viewModel::updateSemester,
    onSemesterBottomSheetDismissRequest = viewModel::hideSemesterBottomSheet,
    onClickExamTypeButton = viewModel::showExamTypeBottomSheet,
    onClickExamTypeItem = viewModel::updateExamType,
    onExamTypeBottomSheetDismissRequest = viewModel::hideExamTypeBottomSheet,
    onClickExamLevelChip = viewModel::updateExamLevel,
    onClickExamInfoChip = viewModel::updateExamInfo,
    onExamEvaluationValueChange = viewModel::updateMyExamEvaluationValue,
    onClickExamEvaluationReviseButton = viewModel::postOrUpdateExamEvaluation,
  )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExamEvaluationEditorScreen(
  scrollState: ScrollState,
  uiState: ExamEvaluationEditorState,
  popBackStack: () -> Unit = {},
  onClickSemesterButton: () -> Unit = {},
  onClickSemesterItem: (Int) -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onClickExamTypeButton: () -> Unit = {},
  onClickExamTypeItem: (Int) -> Unit = {},
  onExamTypeBottomSheetDismissRequest: () -> Unit = {},
  onClickExamLevelChip: (ExamLevel) -> Unit = {},
  onClickExamInfoChip: (ExamInfo) -> Unit = {},
  onExamEvaluationValueChange: (String) -> Unit = { _ -> },

  onClickExamEvaluationReviseButton: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .background(White)
      .fillMaxSize(),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.word_exam_info),
      showBackIcon = false,
      showCloseIcon = true,
      onClickClose = popBackStack,
    )
    Column(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 24.dp)
        .verticalScroll(scrollState),
    ) {
      Spacer(modifier = Modifier.height(20.dp))
      SuwikiSelectionContainer(
        title = uiState.selectedSemester?.ifEmpty { stringResource(R.string.word_choose_semester) } ?: stringResource(R.string.word_choose_semester),
        onClick = onClickSemesterButton,
      )
      Spacer(modifier = Modifier.height(14.dp))

      SuwikiSelectionContainer(
        title = uiState.selectedExamType?.ifEmpty { stringResource(R.string.word_choose_test_type) }
          ?: stringResource(R.string.word_choose_test_type),
        onClick = onClickExamTypeButton,
      )

      Spacer(modifier = Modifier.height(16.dp))

      LectureExamEditContainer(
        text = stringResource(R.string.word_difficulty),
        verticalAlignment = Alignment.Top,
        content = {
          Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            ExamLevel.entries.forEach { examLevel ->
              SuwikiOutlinedChip(
                isChecked = uiState.examLevel == examLevel,
                text = examLevel.toText(),
                onClick = { onClickExamLevelChip(examLevel) },
              )
            }
          }
        },
      )

      Spacer(modifier = Modifier.height(16.dp))

      LectureExamEditContainer(
        text = stringResource(R.string.word_test_info),
        verticalAlignment = Alignment.Top,
        content = {
          FlowRow(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            ExamInfo.entries.forEach { examInfo ->
              SuwikiOutlinedChip(
                isChecked = examInfo.value in uiState.examInfo,
                text = examInfo.toText(),
                onClick = { onClickExamInfoChip(examInfo) },
              )
            }
          }
        },
      )
      Spacer(modifier = Modifier.height(20.dp))
      SuwikiReviewInputBox(
        modifier = Modifier,
        value = uiState.examEvaluation,
        hint = stringResource(R.string.exam_evaluation_input_box_hint),
        onValueChange = onExamEvaluationValueChange,
      )
    }

    Box(modifier = Modifier.imePadding()) {
      SuwikiContainedMediumButton(
        modifier = Modifier
          .padding(24.dp)
          .fillMaxWidth()
          .height(50.dp)
          .imePadding(),
        text = stringResource(com.suwiki.feature.common.ui.R.string.word_complete),
        enabled = uiState.buttonEnabled,
        clickable = uiState.buttonEnabled,
        onClick = onClickExamEvaluationReviseButton,
      )
    }
  }

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showExamTypeBottomSheet,
    onDismissRequest = onExamTypeBottomSheetDismissRequest,
    onClickItem = { onClickExamTypeItem(it) },
    itemList = ExamType.entries.map { it.value }.toPersistentList(),
    title = stringResource(R.string.word_choose_test_type),
    selectedPosition = uiState.selectedExamTypePosition,
  )

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showSemesterBottomSheet,
    onDismissRequest = onSemesterBottomSheetDismissRequest,
    onClickItem = { onClickSemesterItem(it) },
    itemList = uiState.semesterList,
    title = stringResource(R.string.word_choose_semester),
    selectedPosition = uiState.selectedSemesterPosition,
  )

  if (uiState.isLoading) {
    LoadingScreen()
  }
}

@Composable
fun LectureExamEditContainer(
  text: String,
  verticalAlignment: Alignment.Vertical,
  content: @Composable RowScope.() -> Unit,
) {
  Row(
    verticalAlignment = verticalAlignment,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Text(
      modifier = Modifier.width(52.dp),
      text = text,
      style = SuwikiTheme.typography.body4,
    )
    content()
  }
}

@Preview
@Composable
fun ExamEvaluationEditorScreenPreview() {
  val scrollState = rememberScrollState()

  SuwikiTheme {
    ExamEvaluationEditorScreen(
      scrollState = scrollState,
      uiState = ExamEvaluationEditorState(),
    )
  }
}
