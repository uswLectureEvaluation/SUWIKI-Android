package com.suwiki.feature.myinfo.myevaluation.examevaluation

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
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiBottomSheet
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiMenuItem
import com.suwiki.core.designsystem.component.button.SuwikiContainedMediumButton
import com.suwiki.core.designsystem.component.chips.SuwikiOutlinedChip
import com.suwiki.core.designsystem.component.container.SuwikiSelectionContainer
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.enums.ExamLevel
import com.suwiki.core.model.enums.ExamType
import com.suwiki.core.ui.extension.toText
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyExamEvaluationEditRoute(
  viewModel: MyExamEvaluationEditViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyExamEvaluationEditSideEffect.PopBackStack -> popBackStack()
      MyExamEvaluationEditSideEffect.ShowMyExamEvaluationDeleteToast -> {
        onShowToast(context.getString(R.string.my_exam_evaluation_delete_toast_msg))
      }

      MyExamEvaluationEditSideEffect.ShowMyExamEvaluationReviseToast -> {
        onShowToast(context.getString(R.string.my_exam_evaluation_revise_toast_msg))
      }
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.setInitData()
  }

  MyExamEvaluationEditScreen(
    scrollState = scrollState,
    uiState = uiState,
    popBackStack = viewModel::popBackStack,
    onClickSemesterButton = viewModel::showSemesterBottomSheet,
    onClickSemesterItem = viewModel::clickSemesterItem,
    onSemesterBottomSheetDismissRequest = viewModel::hideSemesterBottomSheet,
    onClickExamTypeButton = viewModel::showExamTypeBottomSheet,
    onClickExamTypeItem = viewModel::clickExamTypeItem,
    onExamTypeBottomSheetDismissRequest = viewModel::hideExamTypeBottomSheet,
    onClickExamLevelChip = viewModel::updateExamLevel,
    onClickExamTypeChip = viewModel::updateExamType,
    onExamEvaluationValueChange = viewModel::updateMyExamEvaluationValue,
    onClickExamEvaluationDeleteButton = viewModel::showMyExamEvaluationDeleteDialog,
    onDismissExamEvaluationDelete = viewModel::hideMyExamEvaluationDeleteDialog,
    onClickExamEvaluationDeleteConfirm = viewModel::clickDeleteButton,
    onClickExamEvaluationReviseButton = viewModel::clickReviseButton,
  )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MyExamEvaluationEditScreen(
  scrollState: ScrollState,
  uiState: MyExamEvaluationEditState,
  popBackStack: () -> Unit = {},
  onClickSemesterButton: () -> Unit = {},
  onClickSemesterItem: (String) -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onClickExamTypeButton: () -> Unit = {},
  onClickExamTypeItem: (String) -> Unit = {},
  onExamTypeBottomSheetDismissRequest: () -> Unit = {},
  onClickExamLevelChip: (ExamLevel) -> Unit = {},
  onClickExamTypeChip: (ExamType) -> Unit = {},
  onClickExamEvaluationDeleteButton: () -> Unit = {},
  onClickExamEvaluationDeleteConfirm: () -> Unit = {},
  onExamEvaluationValueChange: (String) -> Unit = { _ -> },
  onDismissExamEvaluationDelete: () -> Unit = {},
  onClickExamEvaluationReviseButton: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .background(White)
      .fillMaxSize(),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.my_test_review_info),
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
        title = if (uiState.selectedSemester == "") stringResource(R.string.my_test_review_choose_semester) else uiState.selectedSemester,
        onClick = onClickSemesterButton,
      )
      Spacer(modifier = Modifier.height(14.dp))

      SuwikiSelectionContainer(
        title = if (uiState.selectedExamType == "") stringResource(R.string.my_test_review_choose_test_type) else uiState.selectedExamType,
        onClick = onClickExamTypeButton,
      )

      Spacer(modifier = Modifier.height(16.dp))

      LectureExamEditContainer(
        text = stringResource(R.string.my_test_review_difficulty),
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
        text = stringResource(R.string.my_test_review_test_type),
        verticalAlignment = Alignment.Top,
        content = {
          FlowRow(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            ExamType.entries.forEach { examType ->
              SuwikiOutlinedChip(
                isChecked = uiState.examType == examType,
                text = examType.toText(),
                onClick = { onClickExamTypeChip(examType) },
              )
            }
          }
        },
      )
      Spacer(modifier = Modifier.height(20.dp))
      SuwikiReviewInputBox(
        modifier = Modifier,
        value = uiState.examEvaluation,
        hint = stringResource(R.string.my_test_review_input_box_hint),
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
        text = stringResource(R.string.my_class_review_input_box_delete),
        enabled = false,
        onClick = onClickExamEvaluationDeleteButton,
      )
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.my_class_review_input_box_revise),
        onClick = onClickExamEvaluationReviseButton,
      )
    }
    if (uiState.showDeleteExamEvaluationDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.my_class_review_delete_dialog_header),
        bodyText = stringResource(R.string.my_class_review_delete_dialog_body, uiState.point),
        confirmButtonText = stringResource(R.string.my_class_review_delete),
        dismissButtonText = stringResource(R.string.my_class_review_cancel),
        onDismissRequest = onDismissExamEvaluationDelete,
        onClickConfirm = onClickExamEvaluationDeleteConfirm,
        onClickDismiss = onDismissExamEvaluationDelete,
      )
    }
  }

  SuwikiBottomSheet(
    isSheetOpen = uiState.showExamTypeBottomSheet,
    onDismissRequest = onExamTypeBottomSheetDismissRequest,
    content = {
      // TODO(REMOVE)
      SuwikiMenuItem(title = "")
      SuwikiMenuItem(
        title = "머신러닝",
        onClick = { onClickExamTypeItem("머신러닝") },
      )
      SuwikiMenuItem(
        title = "머신러닝",
        onClick = { onClickExamTypeItem("머신러닝") },
      )
      SuwikiMenuItem(
        title = "과목명",
        onClick = { onClickExamTypeItem("과목명") },
      )
    },
  )

  SuwikiBottomSheet(
    isSheetOpen = uiState.showSemesterBottomSheet,
    onDismissRequest = onSemesterBottomSheetDismissRequest,
    content = {
      // TODO(REMOVE)
      SuwikiMenuItem(title = "")
      SuwikiMenuItem(
        title = "2023-1",
        onClick = { onClickSemesterItem("2023-1") },
      )
      SuwikiMenuItem(
        title = "2022-2",
        onClick = { onClickSemesterItem("2022-2") },
      )
      SuwikiMenuItem(
        title = "2022-1",
        onClick = { onClickSemesterItem("2022-1") },
      )
    },
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
