package com.suwiki.feature.myinfo.myreview.testreview

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
import com.suwiki.feature.myinfo.myreview.classreview.SuwikiItemsWithText
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyTestReviewEditRoute(
  padding: PaddingValues,
  viewModel: MyTestReviewEditViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
) {
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyTestReviewEditSideEffect.PopBackStack -> popBackStack()
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.setPoint()
  }

  MyTestReviewEditScreen(
    padding = padding,
    scrollState = scrollState,
    uiState = uiState,
    popBackStack = viewModel::popBackStack,
    onClickSemesterButton = viewModel::showSemesterBottomSheet,
    onSemesterBottomSheetDismissRequest = viewModel::hideSemesterBottomSheet,
    onClickTestTypeButton = viewModel::showTestTypeBottomSheet,
    onTestTypeBottomSheetDismissRequest = viewModel::hideTestTypeBottomSheet,
    onClickDifficultyEasy = viewModel::setExamDifficultyEasy,
    onClickDifficultyNormal = viewModel::setExamDifficultyNormal,
    onClickDifficultyHard = viewModel::setExamDifficultyHard,
    onClickTestStudyTypeExamGuides = viewModel::setExamInfoExamGuides,
    onClickTestStudyTypeBook = viewModel::setExamInfoBook,
    onClickTestStudyTypeNotes = viewModel::setExamInfoNotes,
    onClickTestStudyTypePPT = viewModel::setExamInfoPPT,
    onClickTestStudyTypeApply = viewModel::setExamInfoApply,
    onClickTestTypePractice = viewModel::setExamTypePractice,
    onClickTestTypeHomework = viewModel::setExamTypeHomework,
    onTestReviewValueChange = viewModel::updateMyClassReviewValue,
    onClickTestReviewDeleteButton = viewModel::showMyTestReviewDeleteDialog,
    onDismissTestReviewDelete = viewModel::hideMyTestReviewDeleteDialog,
    showMyTestReviewToast = viewModel::showMyTestReviewToast,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTestReviewEditScreen(
  padding: PaddingValues,
  scrollState: ScrollState,
  uiState: MyTestReviewEditState,
  popBackStack: () -> Unit = {},
  onClickSemesterButton: () -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onClickTestTypeButton: () -> Unit = {},
  onTestTypeBottomSheetDismissRequest: () -> Unit = {},
  onClickDifficultyEasy: () -> Unit = {},
  onClickDifficultyNormal: () -> Unit = {},
  onClickDifficultyHard: () -> Unit = {},
  onClickTestStudyTypeExamGuides: () -> Unit = {},
  onClickTestStudyTypeBook: () -> Unit = {},
  onClickTestStudyTypeNotes: () -> Unit = {},
  onClickTestStudyTypePPT: () -> Unit = {},
  onClickTestStudyTypeApply: () -> Unit = {},
  onClickTestTypePractice: () -> Unit = {},
  onClickTestTypeHomework: () -> Unit = {},
  onClickTestReviewDeleteButton: () -> Unit = {},
  onTestReviewValueChange: (String) -> Unit = { _ -> },
  onDismissTestReviewDelete: () -> Unit = {},
  showMyTestReviewToast: (String) -> Unit = {},
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
      onClick = onClickTestTypeButton,
    )
    SuwikiBottomSheet(
      isSheetOpen = uiState.showTestTypeBottomSheet,
      onDismissRequest = onTestTypeBottomSheetDismissRequest,
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
          onClick = onClickTestStudyTypeExamGuides,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "book",
          text = stringResource(R.string.my_test_review_book),
          onClick = onClickTestStudyTypeBook,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "notes",
          text = stringResource(R.string.my_test_review_notes),
          onClick = onClickTestStudyTypeNotes,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "ppt",
          text = stringResource(R.string.my_test_review_ppt),
          onClick = onClickTestStudyTypePPT,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examInfo == "apply",
          text = stringResource(R.string.my_test_review_apply),
          onClick = onClickTestStudyTypeApply,
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
          onClick = onClickTestTypePractice,
        )
      },
      {
        SuwikiOutlinedChip(
          isChecked = uiState.examType == "homework",
          text = stringResource(R.string.my_class_review_homework),
          onClick = onClickTestTypeHomework,
        )
      },
    )
    SuwikiReviewInputBox(
      modifier = Modifier
        .padding(24.dp),
      value = uiState.testReview,
      hint = stringResource(R.string.my_test_review_input_box_hint),
      onValueChange = onTestReviewValueChange,
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
        onClick = onClickTestReviewDeleteButton,
      )
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.my_class_review_input_box_revise),
        onClick = { showMyTestReviewToast("시험정보가 수정되었습니다.") },
      )
    }
    if (uiState.showDeleteTestReviewDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.my_class_review_delete_dialog_header),
        bodyText = stringResource(R.string.my_class_review_delete_dialog_body, uiState.point),
        confirmButtonText = stringResource(R.string.my_class_review_delete),
        dismissButtonText = stringResource(R.string.my_class_review_cancel),
        onDismissRequest = onDismissTestReviewDelete,
        onClickConfirm = { showMyTestReviewToast("시험정보가 삭제되었습니다.") },
        onClickDismiss = onDismissTestReviewDelete,
      )
    }
  }
  SuwikiToast(
    visible = uiState.showDeleteTestReviewToastVisible,
    message = uiState.showDeleteTestReviewToastMessage,
  )
}

@Preview
@Composable
fun MyTestReviewEditScreenPreview() {
  val scrollState = rememberScrollState()

  SuwikiTheme {
    MyTestReviewEditScreen(
      padding = PaddingValues(0.dp),
      scrollState = scrollState,
      uiState = MyTestReviewEditState(),
    )
  }
}
