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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiBottomSheet
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiBottomSheetButton
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiMenuItem
import com.suwiki.core.designsystem.component.button.SuwikiContainedMediumButton
import com.suwiki.core.designsystem.component.chips.SuwikiOutlinedChip
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.core.designsystem.component.toast.SuwikiToast
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

enum class DifficultyLabelItem {
  EASY,
  NORMAL,
  HARD,
}

enum class TestStudyTypeLabelItem {
  EXAMGUIDES,
  BOOK,
  NOTES,
  PPT,
  APPLY,
}

enum class TestTypeLabelItem {
  PRACTICE,
  HOMEWORK,
}

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
    showMyTestReviewToast = viewModel::showMyTestReviewToast
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
    SuwikiBottomSheetButton(
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
    SuwikiBottomSheetButton(
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
    Row(
      modifier = Modifier
        .padding(horizontal = 24.dp)
        .fillMaxWidth(),
    ) {
      Text(
        modifier = Modifier
          .weight(0.16f),
        text = stringResource(R.string.my_test_review_difficulty),
        style = SuwikiTheme.typography.body4,
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.83f)
          .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        SuwikiOutlinedChip(
          isChecked = uiState.examDifficulty == "easy",
          text = stringResource(R.string.my_test_review_easy),
          onClick = onClickDifficultyEasy,
        )
        SuwikiOutlinedChip(
          isChecked = uiState.examDifficulty == "normal",
          text = stringResource(R.string.my_class_review_normal),
          onClick = onClickDifficultyNormal,
        )
        SuwikiOutlinedChip(
          isChecked = uiState.examDifficulty == "hard",
          text = stringResource(R.string.my_test_review_hard),
          onClick = onClickDifficultyHard,
        )
      }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
      modifier = Modifier
        .padding(horizontal = 24.dp)
        .fillMaxWidth(),
    ) {
      Text(
        modifier = Modifier
          .weight(0.16f),
        text = stringResource(R.string.my_test_review_test_type),
        style = SuwikiTheme.typography.body4,
      )
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.83f)
          .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          SuwikiOutlinedChip(
            isChecked = uiState.examInfo == "exam_guides",
            text = stringResource(R.string.my_test_review_exam_guides),
            onClick = onClickTestStudyTypeExamGuides,
          )
          SuwikiOutlinedChip(
            isChecked = uiState.examInfo == "book",
            text = stringResource(R.string.my_test_review_book),
            onClick = onClickTestStudyTypeBook,
          )
          SuwikiOutlinedChip(
            isChecked = uiState.examInfo == "notes",
            text = stringResource(R.string.my_test_review_notes),
            onClick = onClickTestStudyTypeNotes,
          )
          SuwikiOutlinedChip(
            isChecked = uiState.examInfo == "ppt",
            text = stringResource(R.string.my_test_review_ppt),
            onClick = onClickTestStudyTypePPT,
          )
          SuwikiOutlinedChip(
            isChecked = uiState.examInfo == "apply",
            text = stringResource(R.string.my_test_review_apply),
            onClick = onClickTestStudyTypeApply,
          )
        }
        Row(
          horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          SuwikiOutlinedChip(
            isChecked = uiState.examType == "practice",
            text = stringResource(R.string.my_test_review_practice),
            onClick = onClickTestTypePractice,
          )
          SuwikiOutlinedChip(
            isChecked = uiState.examType == "homework",
            text = stringResource(R.string.my_class_review_homework),
            onClick = onClickTestTypeHomework,
          )
        }
      }
    }
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
  var testReview by rememberSaveable { mutableStateOf("") }
  var isSemesterBottomSheetExpanded by remember { mutableStateOf(false) }
  var isTestTypeBottomSheetExpanded by remember { mutableStateOf(false) }
  var difficultyEasyChecked by rememberSaveable { mutableStateOf(false) }
  var difficultyNormalChecked by rememberSaveable { mutableStateOf(false) }
  var difficultyHardChecked by rememberSaveable { mutableStateOf(true) }
  var testTypeExamGuidesChecked by rememberSaveable { mutableStateOf(false) }
  var testTypeBookChecked by rememberSaveable { mutableStateOf(false) }
  var testTypeNotesChecked by rememberSaveable { mutableStateOf(true) }
  var testTypePPTChecked by rememberSaveable { mutableStateOf(false) }
  var testTypeApplyChecked by rememberSaveable { mutableStateOf(false) }
  var testTypePracticeChecked by rememberSaveable { mutableStateOf(true) }
  var testTypeHomeworkChecked by rememberSaveable { mutableStateOf(false) }
  var isShowDeleteTestReviewDialog by remember { mutableStateOf(false) }

  fun clickDifficultyItem(
    clickItemType: DifficultyLabelItem,
  ) {
    difficultyEasyChecked = false
    difficultyNormalChecked = false
    difficultyHardChecked = false

    when (clickItemType) {
      DifficultyLabelItem.EASY -> { difficultyEasyChecked = true }
      DifficultyLabelItem.NORMAL -> { difficultyNormalChecked = true }
      DifficultyLabelItem.HARD -> { difficultyHardChecked = true }
    }
  }

  fun clickTestStudyTypeItem(
    clickItemType: TestStudyTypeLabelItem,
  ) {
    testTypeExamGuidesChecked = false
    testTypeBookChecked = false
    testTypeNotesChecked = false
    testTypePPTChecked = false
    testTypeApplyChecked = false

    when (clickItemType) {
      TestStudyTypeLabelItem.EXAMGUIDES -> { testTypeExamGuidesChecked = true }
      TestStudyTypeLabelItem.BOOK -> { testTypeBookChecked = true }
      TestStudyTypeLabelItem.NOTES -> { testTypeNotesChecked = true }
      TestStudyTypeLabelItem.PPT -> { testTypePPTChecked = true }
      TestStudyTypeLabelItem.APPLY -> { testTypeApplyChecked = true }
    }
  }

  fun clickTestTypeItem(
    clickItemType: TestTypeLabelItem,
  ) {
    testTypePracticeChecked = false
    testTypeHomeworkChecked = false

    when (clickItemType) {
      TestTypeLabelItem.PRACTICE -> { testTypePracticeChecked = true }
      TestTypeLabelItem.HOMEWORK -> { testTypeHomeworkChecked = true }
    }
  }

  SuwikiTheme {
    MyTestReviewEditScreen(
      padding = PaddingValues(0.dp),
      scrollState = scrollState,
      uiState = MyTestReviewEditState(
        testReview = testReview,
        difficultyEasyChecked = difficultyEasyChecked,
        difficultyNormalChecked = difficultyNormalChecked,
        difficultyHardChecked = difficultyHardChecked,
        testTypeExamGuidesChecked = testTypeExamGuidesChecked,
        testTypeBookChecked = testTypeBookChecked,
        testTypeNotesChecked = testTypeNotesChecked,
        testTypePPTChecked = testTypePPTChecked,
        testTypeApplyChecked = testTypeApplyChecked,
        testTypePracticeChecked = testTypePracticeChecked,
        testTypeHomeworkChecked = testTypeHomeworkChecked,
        showDeleteTestReviewDialog = isShowDeleteTestReviewDialog,
        showSemesterBottomSheet = isSemesterBottomSheetExpanded,
        showTestTypeBottomSheet = isTestTypeBottomSheetExpanded,
      ),
      onClickSemesterButton = { isSemesterBottomSheetExpanded = true },
      onSemesterBottomSheetDismissRequest = { isSemesterBottomSheetExpanded = false },
      onClickTestTypeButton = { isTestTypeBottomSheetExpanded = true },
      onTestTypeBottomSheetDismissRequest = { isTestTypeBottomSheetExpanded = false },
      onClickDifficultyEasy = { clickDifficultyItem(DifficultyLabelItem.EASY) },
      onClickDifficultyNormal = { clickDifficultyItem(DifficultyLabelItem.NORMAL) },
      onClickDifficultyHard = { clickDifficultyItem(DifficultyLabelItem.HARD) },
      onClickTestStudyTypeExamGuides = { clickTestStudyTypeItem(TestStudyTypeLabelItem.EXAMGUIDES) },
      onClickTestStudyTypeBook = { clickTestStudyTypeItem(TestStudyTypeLabelItem.BOOK) },
      onClickTestStudyTypeNotes = { clickTestStudyTypeItem(TestStudyTypeLabelItem.NOTES) },
      onClickTestStudyTypePPT = { clickTestStudyTypeItem(TestStudyTypeLabelItem.PPT) },
      onClickTestStudyTypeApply = { clickTestStudyTypeItem(TestStudyTypeLabelItem.APPLY) },
      onClickTestTypePractice = { clickTestTypeItem(TestTypeLabelItem.PRACTICE) },
      onClickTestTypeHomework = { clickTestTypeItem(TestTypeLabelItem.HOMEWORK) },
      onTestReviewValueChange = { testReview = it },
      onClickTestReviewDeleteButton = { isShowDeleteTestReviewDialog = true },
      onDismissTestReviewDelete = { isShowDeleteTestReviewDialog = false },
    )
  }
}
