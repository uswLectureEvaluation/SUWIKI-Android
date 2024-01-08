package com.suwiki.feature.myinfo.myevaluation.examevaluation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import com.suwiki.core.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.core.designsystem.component.toast.SuwikiToast
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.myinfo.R
import com.suwiki.feature.myinfo.myevaluation.lectureevaluation.SuwikiItemsWithText
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyExamEvalutionEditRoute(
  padding: PaddingValues,
  viewModel: MyExamEvalutionEditViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
) {
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyExamEvalutionEditSideEffect.PopBackStack -> popBackStack()
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.setPoint()
  }

  MyExamEvalutionEditScreen(
    padding = padding,
    scrollState = scrollState,
    uiState = uiState,
    popBackStack = viewModel::popBackStack,
    onClickSemesterButton = viewModel::showSemesterBottomSheet,
    onSemesterBottomSheetDismissRequest = viewModel::hideSemesterBottomSheet,
    onClickExamTypeButton = viewModel::showExamTypeBottomSheet,
    onExamTypeBottomSheetDismissRequest = viewModel::hideExamTypeBottomSheet,
    onClickDifficultyEasy = viewModel::setExamDifficultyEasy,
    onClickDifficultyNormal = viewModel::setExamDifficultyNormal,
    onClickDifficultyHard = viewModel::setExamDifficultyHard,
    onClickExamInfoGuides = viewModel::setExamInfoExamGuides,
    onClickExamInfoBook = viewModel::setExamInfoBook,
    onClickExamInfoNotes = viewModel::setExamInfoNotes,
    onClickExamInfoPPT = viewModel::setExamInfoPPT,
    onClickExamInfoApply = viewModel::setExamInfoApply,
    onClickExamTypePractice = viewModel::setExamTypePractice,
    onClickExamTypeHomework = viewModel::setExamTypeHomework,
    onExamEvalutionValueChange = viewModel::updateMyExamEvalutionValue,
    onClickExamEvalutionDeleteButton = viewModel::showMyExamEvalutionDeleteDialog,
    onDismissExamEvalutionDelete = viewModel::hideMyExamEvalutionDeleteDialog,
    showMyExamEvalutionToast = viewModel::showMyExamEvalutionToast,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExamEvalutionEditScreen(
  padding: PaddingValues,
  scrollState: ScrollState,
  uiState: MyExamEvaluationEditState,
  popBackStack: () -> Unit = {},
  onClickSemesterButton: () -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onClickExamTypeButton: () -> Unit = {},
  onExamTypeBottomSheetDismissRequest: () -> Unit = {},
  onClickDifficultyEasy: () -> Unit = {},
  onClickDifficultyNormal: () -> Unit = {},
  onClickDifficultyHard: () -> Unit = {},
  onClickExamInfoGuides: () -> Unit = {},
  onClickExamInfoBook: () -> Unit = {},
  onClickExamInfoNotes: () -> Unit = {},
  onClickExamInfoPPT: () -> Unit = {},
  onClickExamInfoApply: () -> Unit = {},
  onClickExamTypePractice: () -> Unit = {},
  onClickExamTypeHomework: () -> Unit = {},
  onClickExamEvalutionDeleteButton: () -> Unit = {},
  onExamEvalutionValueChange: (String) -> Unit = { _ -> },
  onDismissExamEvalutionDelete: () -> Unit = {},
  showMyExamEvalutionToast: (String) -> Unit = {},
) {
  Column(
    modifier = Modifier
      .padding(padding)
      .background(White)
      .fillMaxSize()
      .verticalScroll(scrollState),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.my_test_review_info),
      showBackIcon = false,
      showCloseIcon = true,
      onClickClose = popBackStack,
    )
    Spacer(modifier = Modifier.height(44.dp))
    SuwikiSelectionContainer(
      modifier = Modifier.padding(start = 24.dp),
      title = stringResource(R.string.my_test_review_choose_semester),
      onClick = onClickSemesterButton,
    )
    SuwikiBottomSheet(
      isSheetOpen = uiState.showSemesterBottomSheet,
      onDismissRequest = onSemesterBottomSheetDismissRequest,
      content = {
        SuwikiMenuItem(title = "")
        SuwikiMenuItem(title = "2023-1")
        SuwikiMenuItem(title = "2022-2")
        SuwikiMenuItem(title = "2022-1")
      },
    )
    Spacer(modifier = Modifier.height(14.dp))
    SuwikiSelectionContainer(
      modifier = Modifier.padding(start = 24.dp),
      title = stringResource(R.string.my_test_review_choose_test_type),
      onClick = onClickExamTypeButton,
    )
    SuwikiBottomSheet(
      isSheetOpen = uiState.showExamTypeBottomSheet,
      onDismissRequest = onExamTypeBottomSheetDismissRequest,
      content = {
        SuwikiMenuItem(title = "")
        SuwikiMenuItem(title = "머신러닝")
        SuwikiMenuItem(title = "머신러닝")
        SuwikiMenuItem(title = "과목명")
      },
    )
    Spacer(modifier = Modifier.height(16.dp))
    SuwikiItemsWithText(
      text = stringResource(R.string.my_test_review_difficulty),
      textWeight = 0.16f,
      itemsWeight = 0.83f,
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examDifficulty == "easy",
          text = stringResource(R.string.my_test_review_easy),
          onClick = onClickDifficultyEasy,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examDifficulty == "normal",
          text = stringResource(R.string.my_class_review_normal),
          onClick = onClickDifficultyNormal,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examDifficulty == "hard",
          text = stringResource(R.string.my_test_review_hard),
          onClick = onClickDifficultyHard,
        )
      },
    )
    Spacer(modifier = Modifier.height(20.dp))
    SuwikiItemsWithText(
      text = stringResource(R.string.my_test_review_test_type),
      textWeight = 0.16f,
      itemsWeight = 0.83f,
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "exam_guides",
          text = stringResource(R.string.my_test_review_exam_guides),
          onClick = onClickExamInfoGuides,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "book",
          text = stringResource(R.string.my_test_review_book),
          onClick = onClickExamInfoBook,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "notes",
          text = stringResource(R.string.my_test_review_notes),
          onClick = onClickExamInfoNotes,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "ppt",
          text = stringResource(R.string.my_test_review_ppt),
          onClick = onClickExamInfoPPT,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "apply",
          text = stringResource(R.string.my_test_review_apply),
          onClick = onClickExamInfoApply,
        )
      },
    )
    Spacer(modifier = Modifier.height(6.dp))
    SuwikiItemsWithText(
      text = "",
      textWeight = 0.16f,
      itemsWeight = 0.83f,
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examType == "practice",
          text = stringResource(R.string.my_test_review_practice),
          onClick = onClickExamTypePractice,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examType == "homework",
          text = stringResource(R.string.my_class_review_homework),
          onClick = onClickExamTypeHomework,
        )
      },
    )
    SuwikiReviewInputBox(
      modifier = Modifier
        .padding(24.dp),
      value = uiState.examEvalution,
      hint = stringResource(R.string.my_test_review_input_box_hint),
      onValueChange = onExamEvalutionValueChange,
    )
    Spacer(modifier = Modifier.weight(1f))
    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 22.dp),
    ) {
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.my_class_review_input_box_delete),
        enabled = false,
        onClick = onClickExamEvalutionDeleteButton,
      )
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.my_class_review_input_box_revise),
        onClick = { showMyExamEvalutionToast("시험정보가 수정되었습니다.") },
      )
    }
    if (uiState.showDeleteExamEvalutionDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.my_class_review_delete_dialog_header),
        bodyText = stringResource(R.string.my_class_review_delete_dialog_body, uiState.point),
        confirmButtonText = stringResource(R.string.my_class_review_delete),
        dismissButtonText = stringResource(R.string.my_class_review_cancel),
        onDismissRequest = onDismissExamEvalutionDelete,
        onClickConfirm = { showMyExamEvalutionToast("시험정보가 삭제되었습니다.") },
        onClickDismiss = onDismissExamEvalutionDelete,
      )
    }
  }
  SuwikiToast(
    visible = uiState.showDeleteExamEvalutionToastVisible,
    message = uiState.showDeleteExamEvalutionToastMessage,
  )
}

@Preview
@Composable
fun MyExamEvalutionEditScreenPreview() {
  val scrollState = rememberScrollState()

  SuwikiTheme {
    MyExamEvalutionEditScreen(
      padding = PaddingValues(0.dp),
      scrollState = scrollState,
      uiState = MyExamEvaluationEditState(),
    )
  }
}
