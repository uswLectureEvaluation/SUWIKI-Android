package com.suwiki.feature.lectureevaluation.editor.lectureevaluation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.suwiki.core.designsystem.component.chips.ChipColor
import com.suwiki.core.designsystem.component.chips.SuwikiContainedChip
import com.suwiki.core.designsystem.component.container.SuwikiSelectionContainer
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.ratingbar.SuwikiRatingBar
import com.suwiki.feature.lectureevaluation.editor.lectureevaluation.component.slider.SuwikiSlider
import com.suwiki.core.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.core.ui.extension.toText
import com.suwiki.feature.lectureevaluation.editor.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.util.Locale

@Composable
fun LectureEvaluationEditorRoute(
  viewModel: LectureEvaluationEditorViewModel = hiltViewModel(),
  popBackStack: () -> Unit = {},
  onShowToast: (String) -> Unit = {},
  handleException: (Throwable) -> Unit,
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      LectureEvaluationEditorSideEffect.PopBackStack -> popBackStack()
      LectureEvaluationEditorSideEffect.ShowLectureEvaluationDeleteToast -> {
        onShowToast(context.getString(R.string.lecture_evaluation_delete_toast_msg))
      }
      is LectureEvaluationEditorSideEffect.HandleException -> handleException(sideEffect.throwable)
      LectureEvaluationEditorSideEffect.ShowInputMoreTextToast -> onShowToast(context.getString(R.string.toast_need_input_30))
      LectureEvaluationEditorSideEffect.ShowSelectSemesterToast -> onShowToast(context.getString(R.string.toast_select_semester))
    }
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  LaunchedEffect(
    key1 = uiState.honeyRating,
    key2 = uiState.learningRating,
    key3 = uiState.satisfactionRating,
  ) {
    viewModel.updateTotalAvg()
  }

  LectureEvaluationEditorScreen(
    uiState = uiState,
    scrollState = scrollState,
    popBackStack = viewModel::popBackStack,
    onClickSemesterButton = viewModel::showSemesterBottomSheet,
    onClickSemesterItem = viewModel::updateSemester,
    onSemesterBottomSheetDismissRequest = viewModel::hideSemesterBottomSheet,
    onHoneyRatingValueChange = viewModel::updateHoneyRating,
    onLearningRatingValueChange = viewModel::updateLearningRating,
    onSatisfactionRatingValueChange = viewModel::updateSatisfactionRating,
    onLectureEvaluationValueChange = viewModel::updateMyLectureEvaluationValue,
    onClickGradeChip = viewModel::updateGradeLevel,
    onClickHomeworkChip = viewModel::updateHomeworkLevel,
    onClickTeamChip = viewModel::updateTeamLevel,
    onClickLectureEvaluationReviseButton = viewModel::postOrUpdateLectureEvaluation,
  )
}

@Composable
fun LectureEvaluationEditorScreen(
  uiState: LectureEvaluationEditorState,
  scrollState: ScrollState,
  popBackStack: () -> Unit,
  onClickSemesterButton: () -> Unit = {},
  onClickSemesterItem: (Int) -> Unit = {},
  onSemesterBottomSheetDismissRequest: () -> Unit = {},
  onHoneyRatingValueChange: (Float) -> Unit = {},
  onLearningRatingValueChange: (Float) -> Unit = {},
  onSatisfactionRatingValueChange: (Float) -> Unit = {},
  onClickGradeChip: (GradeLevel) -> Unit = {},
  onClickHomeworkChip: (HomeworkLevel) -> Unit = {},
  onClickTeamChip: (TeamLevel) -> Unit = {},
  onLectureEvaluationValueChange: (String) -> Unit = { _ -> },
  onClickLectureEvaluationReviseButton: () -> Unit = {},
) {
  Column(
    modifier = Modifier
      .background(White)
      .fillMaxSize(),
  ) {
    SuwikiAppBarWithTitle(
      title = stringResource(R.string.word_lecture_evaluation),
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
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth(),
      ) {
        SuwikiSelectionContainer(
          title = uiState.selectedSemester.ifEmpty { stringResource(id = R.string.word_choose_semester) },
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
        text = stringResource(R.string.word_honey_rating),
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
        text = stringResource(R.string.word_learning_rating),
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
        text = stringResource(R.string.word_satisfaction_rating),
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
          text = stringResource(R.string.word_grade),
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
          text = stringResource(R.string.word_homework),
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
          text = stringResource(R.string.word_team),
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
        hint = stringResource(R.string.lecture_evaluation_input_box_hint),
        onValueChange = onLectureEvaluationValueChange,
      )
    }

    Box(modifier = Modifier.imePadding()) {
      SuwikiContainedMediumButton(
        modifier = Modifier
          .padding(24.dp)
          .fillMaxWidth()
          .height(50.dp)
          .imePadding(),
        text = stringResource(com.suwiki.core.ui.R.string.word_complete),
        enabled = uiState.buttonEnabled,
        clickable = uiState.buttonEnabled,
        onClick = onClickLectureEvaluationReviseButton,
      )
    }
  }

  SuwikiSelectBottomSheet(
    isSheetOpen = uiState.showSemesterBottomSheet,
    onDismissRequest = onSemesterBottomSheetDismissRequest,
    onClickItem = { onClickSemesterItem(it) },
    itemList = uiState.semesterList,
    title = stringResource(R.string.word_select_semester),
    selectedPosition = uiState.selectedSemesterPosition,
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
fun LectureEvaluationEditorPreview() {
  SuwikiTheme {
    val scrollState = rememberScrollState()

    LectureEvaluationEditorScreen(
      uiState = LectureEvaluationEditorState(),
      scrollState = scrollState,
      popBackStack = {},
    )
  }
}
