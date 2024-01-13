package com.suwiki.feature.lectureevaluation.editor.examevaluation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiSelectBottomSheet
import com.suwiki.core.designsystem.component.button.SuwikiContainedMediumButton
import com.suwiki.core.designsystem.component.chips.SuwikiOutlinedChip
import com.suwiki.core.designsystem.component.container.SuwikiSelectionContainer
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.enums.ExamInfo
import com.suwiki.core.model.enums.ExamLevel
import com.suwiki.core.model.enums.ExamType
import com.suwiki.core.ui.extension.toText
import com.suwiki.feature.lectureevaluation.editor.R
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyExamEvaluationEditRoute(
  viewModel: MyExamEvaluationEditViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyExamEvaluationEditSideEffect.PopBackStack -> popBackStack()
      MyExamEvaluationEditSideEffect.ShowMyExamEvaluationDeleteToast -> {
        onShowToast(context.getString(R.string.exam_evaluation_delete_toast_msg))
      }

      MyExamEvaluationEditSideEffect.ShowMyExamEvaluationReviseToast -> {
        onShowToast(context.getString(R.string.exam_evaluation_revise_toast_msg))
      }
      is MyExamEvaluationEditSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  MyExamEvaluationEditScreen(
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
    onClickExamEvaluationDeleteButton = viewModel::showDeleteOrLackPointDialog,
    onDismissExamEvaluationDelete = viewModel::hideExamEvaluationDeleteDialog,
    onDismissLackPoint = viewModel::hideLackPointDialog,
    onClickExamEvaluationDeleteConfirm = viewModel::deleteExamEvaluation,
    onClickExamEvaluationReviseButton = viewModel::updateExamEvaluation,
  )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MyExamEvaluationEditScreen(
  scrollState: ScrollState,
  uiState: MyExamEvaluationEditState,
  popBackStack: () -> Unit = {},
  onClickSemesterButton: () -> Unit = {},
  onClickSemesterItem: (Int) -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onClickExamTypeButton: () -> Unit = {},
  onClickExamTypeItem: (Int) -> Unit = {},
  onExamTypeBottomSheetDismissRequest: () -> Unit = {},
  onClickExamLevelChip: (String) -> Unit = {},
  onClickExamInfoChip: (String) -> Unit = {},
  onClickExamEvaluationDeleteButton: () -> Unit = {},
  onClickExamEvaluationDeleteConfirm: () -> Unit = {},
  onExamEvaluationValueChange: (String) -> Unit = { _ -> },
  onDismissExamEvaluationDelete: () -> Unit = {},
  onDismissLackPoint: () -> Unit = {},
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
        .padding(24.dp)
        .verticalScroll(scrollState),
    ) {
      Spacer(modifier = Modifier.height(20.dp))
      SuwikiSelectionContainer(
        title = uiState.selectedSemester ?: stringResource(R.string.word_choose_semester),
        onClick = onClickSemesterButton,
      )
      Spacer(modifier = Modifier.height(14.dp))

      SuwikiSelectionContainer(
        title = uiState.selectedExamType ?: stringResource(R.string.word_choose_test_type),
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
                isChecked = uiState.examLevel == examLevel.value,
                text = examLevel.toText(),
                onClick = { onClickExamLevelChip(examLevel.value) },
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
                onClick = { onClickExamInfoChip(examInfo.value) },
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
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
        .imePadding(),
    ) {
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.text_delete),
        enabled = false,
        onClick = onClickExamEvaluationDeleteButton,
      )
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.text_revise),
        onClick = onClickExamEvaluationReviseButton,
      )
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

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showExamTypeBottomSheet,
    onDismissRequest = onExamTypeBottomSheetDismissRequest,
    onClickItem = { onClickExamTypeItem(it) },
    itemList = ExamType.values().map { it.value }.toPersistentList(),
    title = stringResource(R.string.word_choose_semester),
    selectedPosition = uiState.selectedExamTypePosition,
  )

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showSemesterBottomSheet,
    onDismissRequest = onSemesterBottomSheetDismissRequest,
    onClickItem = { onClickSemesterItem(it) },
    itemList = uiState.semesterList,
    title = stringResource(R.string.word_choose_test_type),
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
fun MyExamEvalutionEditScreenPreview() {
  val scrollState = rememberScrollState()

  SuwikiTheme {
    MyExamEvaluationEditScreen(
      scrollState = scrollState,
      uiState = MyExamEvaluationEditState(),
    )
  }
}
