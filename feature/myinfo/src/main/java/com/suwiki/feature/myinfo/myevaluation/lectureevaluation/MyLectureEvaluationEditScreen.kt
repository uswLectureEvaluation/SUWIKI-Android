package com.suwiki.feature.myinfo.myevaluation.lectureevaluation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.core.ui.extension.toText
import com.suwiki.feature.myinfo.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.Locale

const val MINIMUM_DELETE_POINT = 30

@Composable
fun MyLectureEvaluationEditRoute(
  viewModel: MyLectureEvaluationEditViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
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
      is MyLectureEvaluationEditSideEffect.HandleException -> handleException(sideEffect.throwable)
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.setInitData()
  }

  LaunchedEffect(
    key1 = uiState.honeyRating,
    key2 = uiState.learningRating,
    key3 = uiState.satisfactionRating,
  ) {
    viewModel.updateTotalAvg()
  }

  MyLectureEvaluationEditScreen(
    uiState = uiState,
    scrollState = scrollState,
    popBackStack = viewModel::popBackStack,
    onClickSemesterButton = viewModel::showSemesterBottomSheet,
    onClickSemesterItem = viewModel::clickSemesterItem,
    onSemesterBottomSheetDismissRequest = viewModel::hideSemesterBottomSheet,
    onHoneyRatingValueChange = viewModel::updateHoneyRating,
    onLearningRatingValueChange = viewModel::updateLearningRating,
    onSatisfactionRatingValueChange = viewModel::updateSatisfactionRating,
    onLectureEvaluationValueChange = viewModel::updateMyLectureEvaluationValue,
    onClickLectureEvaluationDeleteButton = viewModel::showMyLectureEvaluationDeleteDialog,
    onDismissLectureEvaluationDelete = viewModel::hideMyLectureEvaluationDeleteDialog,
    onClickGradeChip = viewModel::updateGradeLevel,
    onClickHomeworkChip = viewModel::updateHomeworkLevel,
    onClickTeamChip = viewModel::updateTeamLevel,
    onClickLectureEvaluationDeleteConfirm = viewModel::clickDeleteButton,
    onClickLectureEvaluationReviseButton = viewModel::clickReviseButton,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLectureEvaluationEditScreen(
  uiState: MyLectureEvaluationEditState,
  scrollState: ScrollState,
  popBackStack: () -> Unit,
  onClickSemesterButton: () -> Unit = {},
  onClickSemesterItem: (String) -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onHoneyRatingValueChange: (Float) -> Unit = {},
  onLearningRatingValueChange: (Float) -> Unit = {},
  onSatisfactionRatingValueChange: (Float) -> Unit = {},
  onClickGradeChip: (GradeLevel) -> Unit = {},
  onClickHomeworkChip: (HomeworkLevel) -> Unit = {},
  onClickTeamChip: (TeamLevel) -> Unit = {},
  onLectureEvaluationValueChange: (String) -> Unit = { _ -> },
  onClickLectureEvaluationDeleteButton: () -> Unit = {},
  onClickLectureEvaluationDeleteConfirm: () -> Unit = {},
  onDismissLectureEvaluationDelete: () -> Unit = {},
  onClickLectureEvaluationReviseButton: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .background(White)
      .fillMaxSize(),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.my_class_review_lecture_evaluation),
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
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth(),
      ) {
        SuwikiSelectionContainer(
          title = uiState.selectedSemester,
          onClick = onClickSemesterButton,
        )

        Row(
          modifier = Modifier.wrapContentHeight(),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          SuwikiRatingBar(
            rating = uiState.totalAvg,
          )
          Text(
            text = "%.1f".format(Locale.US, uiState.totalAvg),
            style = SuwikiTheme.typography.body4,
            color = Primary,
          )
        }
      }

      LectureEvaluationEditContainer(
        text = stringResource(R.string.my_class_review_honey_rating),
        verticalAlignment = Alignment.Bottom,
        content = {
          SuwikiSlider(
            modifier = Modifier.weight(1f),
            value = uiState.honeyRating,
            onValueChange = onHoneyRatingValueChange,
          )
        },
      )
      LectureEvaluationEditContainer(
        text = stringResource(R.string.my_class_review_learning_rating),
        verticalAlignment = Alignment.Bottom,
        content = {
          SuwikiSlider(
            modifier = Modifier.weight(1f),
            value = uiState.learningRating,
            onValueChange = onLearningRatingValueChange,
          )
        },
      )
      LectureEvaluationEditContainer(
        text = stringResource(R.string.my_class_review_satisfaction_rating),
        verticalAlignment = Alignment.Bottom,
        content = {
          SuwikiSlider(
            modifier = Modifier.weight(1f),
            value = uiState.satisfactionRating,
            onValueChange = onSatisfactionRatingValueChange,
          )
        },
      )

      Spacer(modifier = Modifier.height(20.dp))

      Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
      ) {
        LectureEvaluationEditContainer(
          text = stringResource(R.string.my_class_review_grade),
          verticalAlignment = Alignment.Bottom,
          content = {
            Row(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
              GradeLevel.entries.zip(ChipColor.entries).forEach { (gradeLevel, color) ->
                SuwikiContainedChip(
                  isChecked = uiState.gradeLevel == gradeLevel,
                  color = color,
                  text = gradeLevel.toText(),
                  onClick = { onClickGradeChip(gradeLevel) },
                )
              }
            }
          },
        )

        LectureEvaluationEditContainer(
          text = stringResource(R.string.my_class_review_homework),
          verticalAlignment = Alignment.Bottom,
          content = {
            Row(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
              HomeworkLevel.entries.zip(ChipColor.entries).forEach { (homeworkLevel, color) ->
                SuwikiContainedChip(
                  isChecked = uiState.homeworkLevel == homeworkLevel,
                  color = color,
                  text = homeworkLevel.toText(),
                  onClick = { onClickHomeworkChip(homeworkLevel) },
                )
              }
            }
          },
        )

        LectureEvaluationEditContainer(
          text = stringResource(R.string.my_class_review_team),
          verticalAlignment = Alignment.Bottom,
          content = {
            Row(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
              TeamLevel.entries.zip(ChipColor.entries.minus(ChipColor.BLUE)).forEach { (teamLevel, color) ->
                SuwikiContainedChip(
                  isChecked = uiState.teamLevel == teamLevel,
                  color = color,
                  text = teamLevel.toText(),
                  onClick = { onClickTeamChip(teamLevel) },
                )
              }
            }
          },
        )
      }

      Spacer(modifier = Modifier.size(24.dp))

      SuwikiReviewInputBox(
        value = uiState.lectureEvaluation,
        hint = stringResource(R.string.my_class_review_input_box_hint),
        onValueChange = onLectureEvaluationValueChange,
      )
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
        .imePadding(),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
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
  }

  if (uiState.showDeleteLectureEvaluationDialog) {
    if (uiState.point > MINIMUM_DELETE_POINT) {
      SuwikiDialog(
        headerText = stringResource(R.string.my_class_review_delete_dialog_header),
        bodyText = stringResource(R.string.my_class_review_delete_dialog_body, uiState.point),
        confirmButtonText = stringResource(R.string.my_class_review_delete),
        dismissButtonText = stringResource(R.string.my_class_review_cancel),
        onDismissRequest = onDismissLectureEvaluationDelete,
        onClickConfirm = onClickLectureEvaluationDeleteConfirm,
        onClickDismiss = onDismissLectureEvaluationDelete,
      )
    } else {
      SuwikiDialog(
        headerText = stringResource(R.string.lack_point_dialog_header),
        bodyText = stringResource(R.string.lack_point_dialog_body, uiState.point),
        confirmButtonText = stringResource(R.string.confirm),
        onDismissRequest = onDismissLectureEvaluationDelete,
        onClickConfirm = onDismissLectureEvaluationDelete,
      )
    }
  }

  SuwikiBottomSheet(
    isSheetOpen = uiState.showSemesterBottomSheet,
    onDismissRequest = onSemesterBottomSheetDismissRequest,
    content = {
      uiState.semesterList.forEach { semester ->
        SuwikiMenuItem(
          title = semester,
          onClick = { onClickSemesterItem(semester) },
        )
      }
    },
  )

  if (uiState.isLoading) {
    LoadingScreen()
  }
}

@Composable
fun LectureEvaluationEditContainer(
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
fun MyLectureEvaluationEditPreview() {
  SuwikiTheme {
    val scrollState = rememberScrollState()

    MyLectureEvaluationEditScreen(
      uiState = MyLectureEvaluationEditState(),
      scrollState = scrollState,
      popBackStack = {},
    )
  }
}
