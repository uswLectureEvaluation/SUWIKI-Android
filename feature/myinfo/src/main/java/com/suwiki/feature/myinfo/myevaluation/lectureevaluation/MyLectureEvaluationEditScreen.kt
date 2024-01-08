package com.suwiki.feature.myinfo.myevaluation.lectureevaluation

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
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.suwiki.core.designsystem.component.chips.ChipColor
import com.suwiki.core.designsystem.component.chips.SuwikiContainedChip
import com.suwiki.core.designsystem.component.container.SuwikiSelectionContainer
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.ratingbar.SuwikiRatingBar
import com.suwiki.core.designsystem.component.slider.SuwikiSlider
import com.suwiki.core.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.core.designsystem.component.toast.SuwikiToast
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MyLectureEvaluationEditRoute(
  padding: PaddingValues,
  viewModel: MyLectureEvaluationEditViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      MyLectureEvaluationEditSideEffect.PopBackStack -> popBackStack()
      MyLectureEvaluationEditSideEffect.ShowMyLectureEvaluationDeleteToast -> {
        onShowToast(context.getString(R.string.my_lecture_evaluation_delete_toast_msg))
      }
      MyLectureEvaluationEditSideEffect.ShowMyLectureEvaluationReviseToast -> {
        onShowToast(context.getString(R.string.my_lecture_evaluation_revise_toast_msg))
      }
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.loadMyPoint()
  }

  MyLectureEvaluationEditScreen(
    padding = padding,
    uiState = uiState,
    scrollState = scrollState,
    popBackStack = viewModel::popBackStack,
    onClickSemesterButton = viewModel::showSemesterBottomSheet,
    onSemesterBottomSheetDismissRequest = viewModel::hideSemesterBottomSheet,
    onHoneyRatingValueChange = viewModel::updateHoneyRating,
    onLearningRatingValueChange = viewModel::updateLearningRating,
    onSatisfactionRatingValueChange = viewModel::updateSatisfactionRating,
    onLectureEvaluationValueChange = viewModel::updateMyLectureEvaluationValue,
    onClickLectureEvaluationDeleteButton = viewModel::showMyLectureEvaluationDeleteDialog,
    onDismissLectureEvaluationDelete = viewModel::hideMyLectureEvaluationDeleteDialog,
    onClickGradeGenerous = viewModel::setDifficultyGenerous,
    onClickGradeNormal = viewModel::setDifficultyNormal,
    onClickGradePicky = viewModel::setDifficultyPicky,
    onClickHomeworkNone = viewModel::setHomeworkNone,
    onClickHomeworkNormal = viewModel::setHomeworkNormal,
    onClickHomeworkMuch = viewModel::setHomeworkMuch,
    onClickTeamNone = viewModel::setTeamNone,
    onClickTeamExist = viewModel::setTeamExist,
    onClickLectureEvaluationDeleteConfirm = viewModel::clickDeleteButton,
    onClickLectureEvaluationReviseButton = viewModel::clickReviseButton
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLectureEvaluationEditScreen(
  padding: PaddingValues,
  uiState: MyLectureEvaluationEditState,
  scrollState: ScrollState,
  popBackStack: () -> Unit,
  onClickSemesterButton: () -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onHoneyRatingValueChange: (Float) -> Unit = {},
  onLearningRatingValueChange: (Float) -> Unit = {},
  onSatisfactionRatingValueChange: (Float) -> Unit = {},
  onClickGradeGenerous: () -> Unit = {},
  onClickGradeNormal: () -> Unit = {},
  onClickGradePicky: () -> Unit = {},
  onClickHomeworkNone: () -> Unit = {},
  onClickHomeworkNormal: () -> Unit = {},
  onClickHomeworkMuch: () -> Unit = {},
  onClickTeamNone: () -> Unit = {},
  onClickTeamExist: () -> Unit = {},
  onLectureEvaluationValueChange: (String) -> Unit = { _ -> },
  onClickLectureEvaluationDeleteButton: () -> Unit = {},
  onClickLectureEvaluationDeleteConfirm: () -> Unit = {},
  onDismissLectureEvaluationDelete: () -> Unit = {},
  onClickLectureEvaluationReviseButton: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .padding(padding)
      .background(White)
      .fillMaxSize()
      .verticalScroll(scrollState),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.my_class_review_lecture_evaluation),
      showBackIcon = false,
      showCloseIcon = true,
      onClickClose = popBackStack,
    )
    Spacer(modifier = Modifier.height(44.dp))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      SuwikiSelectionContainer(
        title = "2023-2",
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
      Row(
        modifier = Modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        SuwikiRatingBar(
          rating = 3.4f,
        )
        Text(
          text = "3.4",
          style = SuwikiTheme.typography.body4,
          color = Primary,
        )
      }
    }
    SuwikiSliderWithText(
      text = stringResource(R.string.my_class_review_honey_rating),
      sliderValue = uiState.honeyRating,
      onValueChange = onHoneyRatingValueChange,
    )
    SuwikiSliderWithText(
      text = stringResource(R.string.my_class_review_learning_rating),
      sliderValue = uiState.learningRating,
      onValueChange = onLearningRatingValueChange,
    )
    SuwikiSliderWithText(
      text = stringResource(R.string.my_class_review_satisfaction_rating),
      sliderValue = uiState.satisfactionRating,
      onValueChange = onSatisfactionRatingValueChange,
    )
    Spacer(modifier = Modifier.height(20.dp))
    SuwikiItemsWithText(
      text = stringResource(R.string.my_class_review_grade),
      textWeight = 0.17f,
      itemsWeight = 0.82f,
      {
        SuwikiContainedChip(
          isChecked = uiState.difficulty == 2,
          color = ChipColor.BLUE,
          text = stringResource(R.string.my_class_review_generous),
          onClick = onClickGradeGenerous,
        )
      },
      {
        SuwikiContainedChip(
          isChecked = uiState.difficulty == 1,
          color = ChipColor.BLUE,
          text = stringResource(R.string.my_class_review_normal),
          onClick = onClickGradeNormal,
        )
      },
      {
        SuwikiContainedChip(
          isChecked = uiState.difficulty == 0,
          color = ChipColor.BLUE,
          text = stringResource(R.string.my_class_review_picky),
          onClick = onClickGradePicky,
        )
      },
    )
    Spacer(modifier = Modifier.height(20.dp))
    SuwikiItemsWithText(
      text = stringResource(R.string.my_class_review_homework),
      textWeight = 0.17f,
      itemsWeight = 0.82f,
      {
        SuwikiContainedChip(
          isChecked = uiState.homework == 0,
          color = ChipColor.GREEN,
          text = stringResource(R.string.my_class_review_none),
          onClick = onClickHomeworkNone,
        )
      },
      {
        SuwikiContainedChip(
          isChecked = uiState.homework == 1,
          color = ChipColor.GREEN,
          text = stringResource(R.string.my_class_review_normal),
          onClick = onClickHomeworkNormal,
        )
      },
      {
        SuwikiContainedChip(
          isChecked = uiState.homework == 2,
          color = ChipColor.GREEN,
          text = stringResource(R.string.my_class_review_much),
          onClick = onClickHomeworkMuch,
        )
      },
    )
    Spacer(modifier = Modifier.height(20.dp))
    SuwikiItemsWithText(
      text = stringResource(R.string.my_class_review_team),
      textWeight = 0.17f,
      itemsWeight = 0.82f,
      {
        SuwikiContainedChip(
          isChecked = uiState.team == 0,
          color = ChipColor.ORANGE,
          text = stringResource(R.string.my_class_review_none),
          onClick = onClickTeamNone,
        )
      },
      {
        SuwikiContainedChip(
          isChecked = uiState.team == 1,
          color = ChipColor.ORANGE,
          text = stringResource(R.string.my_class_review_exist),
          onClick = onClickTeamExist,
        )
      },
    )
    SuwikiReviewInputBox(
      value = uiState.lectureEvaluation,
      modifier = Modifier.padding(24.dp),
      hint = stringResource(R.string.my_class_review_input_box_hint),
      onValueChange = onLectureEvaluationValueChange,
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
        onClick = onClickLectureEvaluationDeleteButton,
      )
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.my_class_review_input_box_revise),
        onClick = onClickLectureEvaluationReviseButton,
      )
    }
    if (uiState.showDeleteLectureEvaluationDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.my_class_review_delete_dialog_header),
        bodyText = stringResource(R.string.my_class_review_delete_dialog_body, uiState.point),
        confirmButtonText = stringResource(R.string.my_class_review_delete),
        dismissButtonText = stringResource(R.string.my_class_review_cancel),
        onDismissRequest = onDismissLectureEvaluationDelete,
        onClickConfirm = onClickLectureEvaluationDeleteConfirm,
        onClickDismiss = onDismissLectureEvaluationDelete,
      )
    }
  }
  SuwikiToast(
    visible = uiState.showDeleteLectureEvaluationToastVisible,
    message = uiState.showDeleteLectureEvaluationToastMessage,
  )
  if (uiState.isLoading) {
    LoadingScreen()
  }
}

@Composable
fun SuwikiSliderWithText(
  text: String,
  sliderValue: Float,
  onValueChange: (Float) -> Unit,
) {
  Row(
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp),
  ) {
    Text(
      modifier = Modifier.weight(0.17f),
      text = text,
      style = SuwikiTheme.typography.body4,
    )
    SuwikiSlider(
      modifier = Modifier.weight(0.82f),
      value = sliderValue,
      onValueChange = onValueChange,
    )
  }
}

@Composable
fun SuwikiItemsWithText(
  text: String,
  textWeight: Float,
  itemsWeight: Float,
  vararg items: @Composable () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp),
  ) {
    Text(
      modifier = Modifier.weight(textWeight),
      text = text,
      style = SuwikiTheme.typography.body4,
    )
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .weight(itemsWeight)
        .padding(start = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      items.forEach { it() }
    }
  }
}

@Preview
@Composable
fun MyLectureEvaluationEditPreview() {
  SuwikiTheme {
    val scrollState = rememberScrollState()

    MyLectureEvaluationEditScreen(
      padding = PaddingValues(0.dp),
      uiState = MyLectureEvaluationEditState(),
      scrollState = scrollState,
      popBackStack = {},
    )
  }
}
