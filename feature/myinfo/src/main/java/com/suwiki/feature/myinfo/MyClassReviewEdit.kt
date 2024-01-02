package com.suwiki.feature.myinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiBottomSheet
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiMenuItem
import com.suwiki.core.designsystem.component.button.SuwikiContainedMediumButton
import com.suwiki.core.designsystem.component.chips.ChipColor
import com.suwiki.core.designsystem.component.chips.SuwikiContainedChip
import com.suwiki.core.designsystem.component.dialog.SuwikiDialog
import com.suwiki.core.designsystem.component.ratingbar.SuwikiRatingBar
import com.suwiki.core.designsystem.component.slider.SuwikiSlider
import com.suwiki.core.designsystem.component.textfield.SuwikiReviewInputBox
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

enum class GradeLabelItem {
  GENEROUS,
  NORMAL,
  PICKY,
}

enum class HomeworkLabelItem {
  NONE,
  NORMAL,
  MUCH,
}

enum class TeamLabelItem {
  NONE,
  EXIST,
}

@Composable
fun MyClassReviewEditRoute(
  padding: PaddingValues,
) {
  val scrollState = rememberScrollState()
  var honeyRating by rememberSaveable { mutableFloatStateOf(5f) }
  var isSemesterBottomSheetExpanded by remember {
    mutableStateOf(false)
  }
//  MyClassReviewEditScreen(
//    padding = padding,
//    honeyRating = honeyRating,
//    uiState = MyClassReviewEditState(),
//    isSemesterBottomSheetExpanded = isSemesterBottomSheetExpanded,
//    onClickSemesterButton = { isSemesterBottomSheetExpanded = true },
//    onSemesterBottomSheetDismissRequest = { isSemesterBottomSheetExpanded = false },
//    scrollState = scrollState
//  ) { honeyRating = if (it < 0.5) 0.5F else it }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyClassReviewEditScreen(
  padding: PaddingValues,
  uiState: MyClassReviewEditState,
  scrollState: ScrollState,
  isSemesterBottomSheetExpanded: Boolean,
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
  onClassReviewValueChange: (String) -> Unit = { _ -> },
  onClickClassReviewReviseButton: () -> Unit = {},
  onClickClassReviewDeleteButton: () -> Unit = {},
  onDismissClassReviewDelete: () -> Unit = {},
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
    )
    Spacer(modifier = Modifier.height(44.dp))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .clickable(onClick = onClickSemesterButton)
          .background(GrayF6)
          .padding(
            horizontal = 9.dp,
            vertical = 6.dp,
          ),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "2023-2",
          color = Gray95,
        )
        Image(
          painter = painterResource(id = R.drawable.ic_dropdown_arrow_down),
          contentDescription = "",
        )
      }
      SuwikiBottomSheet(
        isSheetOpen = isSemesterBottomSheetExpanded,
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
    Row(
      verticalAlignment = Alignment.Bottom,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      Text(
        modifier = Modifier.weight(0.17f),
        text = stringResource(R.string.my_class_review_honey_rating),
        style = SuwikiTheme.typography.body4,
      )
      SuwikiSlider(
        modifier = Modifier.weight(0.82f),
        value = uiState.honeyRating,
        onValueChange = onHoneyRatingValueChange,
      )
    }
    Row(
      verticalAlignment = Alignment.Bottom,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      Text(
        modifier = Modifier.weight(0.17f),
        text = stringResource(R.string.my_class_review_learning_rating),
        style = SuwikiTheme.typography.body4
      )
      SuwikiSlider(
        modifier = Modifier.weight(0.82f),
        value = uiState.learningRating,
        onValueChange = onLearningRatingValueChange,
      )
    }
    Row(
      verticalAlignment = Alignment.Bottom,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      Text(
        modifier = Modifier.weight(0.17f),
        text = stringResource(R.string.my_class_review_satisfaction_rating),
        style = SuwikiTheme.typography.body4,
      )
      SuwikiSlider(
        modifier = Modifier.weight(0.82f),
        value = uiState.satisfactionRating,
        onValueChange = onSatisfactionRatingValueChange,
      )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      Text(
        modifier = Modifier.weight(0.17f),
        text = stringResource(R.string.my_class_review_grade),
        style = SuwikiTheme.typography.body4,
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.82f)
          .padding(start = 16.dp)
      ) {
        SuwikiContainedChip(
          isChecked = uiState.gradeGenerousChecked,
          color = ChipColor.BLUE,
          text = stringResource(R.string.my_class_review_generous),
          onClick = onClickGradeGenerous,
        )
        Spacer(modifier = Modifier.width(4.dp))
        SuwikiContainedChip(
          isChecked = uiState.gradeNormalChecked,
          color = ChipColor.BLUE,
          text = stringResource(R.string.my_class_review_normal),
          onClick = onClickGradeNormal,
        )
        Spacer(modifier = Modifier.width(4.dp))
        SuwikiContainedChip(
          isChecked = uiState.gradePickyChecked,
          color = ChipColor.BLUE,
          text = stringResource(R.string.my_class_review_picky),
          onClick = onClickGradePicky,
        )
      }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      Text(
        modifier = Modifier.weight(0.17f),
        text = stringResource(R.string.my_class_review_homework),
        style = SuwikiTheme.typography.body4,
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.82f)
          .padding(start = 16.dp)
      ) {
        SuwikiContainedChip(
          isChecked = uiState.homeworkNoneChecked,
          color = ChipColor.GREEN,
          text = stringResource(R.string.my_class_review_none),
          onClick = onClickHomeworkNone,
        )
        Spacer(modifier = Modifier.width(4.dp))
        SuwikiContainedChip(
          isChecked = uiState.homeworkNormalChecked,
          color = ChipColor.GREEN,
          text = stringResource(R.string.my_class_review_normal),
          onClick = onClickHomeworkNormal,
        )
        Spacer(modifier = Modifier.width(4.dp))
        SuwikiContainedChip(
          isChecked = uiState.homeworkMuchChecked,
          color = ChipColor.GREEN,
          text = stringResource(R.string.my_class_review_much),
          onClick = onClickHomeworkMuch
        )
      }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      Text(
        modifier = Modifier.weight(0.17f),
        text = stringResource(R.string.my_class_review_team),
        style = SuwikiTheme.typography.body4,
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.82f)
          .padding(start = 16.dp)
      ) {
        SuwikiContainedChip(
          isChecked = uiState.teamNoneChecked,
          color = ChipColor.ORANGE,
          text = stringResource(R.string.my_class_review_none),
          onClick = onClickTeamNone,
        )
        Spacer(modifier = Modifier.width(4.dp))
        SuwikiContainedChip(
          isChecked = uiState.teamExistChecked,
          color = ChipColor.ORANGE,
          text = stringResource(R.string.my_class_review_exist),
          onClick = onClickTeamExist,
        )
      }
    }
    SuwikiReviewInputBox(
      value = uiState.classReview,
      modifier = Modifier
        .weight(1f)
        .padding(24.dp),
      hint = stringResource(R.string.my_class_review_input_box_hint),
      onValueChange = onClassReviewValueChange
    )
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
        onClick = onClickClassReviewDeleteButton,
      )
      SuwikiContainedMediumButton(
        modifier = Modifier
          .weight(1f)
          .height(50.dp),
        text = stringResource(R.string.my_class_review_input_box_revise),
        onClick = onClickClassReviewReviseButton,
      )
    }
    if (uiState.showDeleteClassReviewDialog) {
      SuwikiDialog(
        headerText = stringResource(R.string.my_class_review_delete_dialog_header),
        bodyText = stringResource(R.string.my_class_review_delete_dialog_body),
        confirmButtonText = stringResource(R.string.my_class_review_delete),
        dismissButtonText = stringResource(R.string.my_class_review_cancel),
        onDismissRequest = onDismissClassReviewDelete,
        onClickConfirm = {},
        onClickDismiss = onDismissClassReviewDelete,
      )
    }
  }
}

@Preview
@Composable
fun MyClassReviewEditPreview() {
  SuwikiTheme {
    val scrollState = rememberScrollState()
    var honeyRating by rememberSaveable { mutableFloatStateOf(2.5f) }
    var learningRating by rememberSaveable { mutableFloatStateOf(2.5f) }
    var satisfactionRating by rememberSaveable { mutableFloatStateOf(2.5f) }
    var isSemesterBottomSheetExpanded by remember { mutableStateOf(false) }
    var gradeGenerousChecked by rememberSaveable { mutableStateOf(false) }
    var gradeNormalChecked by rememberSaveable { mutableStateOf(true) }
    var gradePickyChecked by rememberSaveable { mutableStateOf(false) }
    var homeworkNoneChecked by rememberSaveable { mutableStateOf(false) }
    var homeworkNormalChecked by rememberSaveable { mutableStateOf(false) }
    var homeworkMuchChecked by rememberSaveable { mutableStateOf(true) }
    var teamNoneChecked by rememberSaveable { mutableStateOf(true) }
    var teamExistChecked by rememberSaveable { mutableStateOf(false) }
    var classReview by rememberSaveable { mutableStateOf("") }
    var showDeleteClassReviewDialog by remember { mutableStateOf(false) }

    fun clickGradeItem(
      clickItem: GradeLabelItem,
    ) {
      gradeGenerousChecked = false
      gradeNormalChecked = false
      gradePickyChecked = false

      when (clickItem) {
        GradeLabelItem.GENEROUS -> { gradeGenerousChecked = true }
        GradeLabelItem.NORMAL -> { gradeNormalChecked = true }
        GradeLabelItem.PICKY -> { gradePickyChecked = true }
      }
    }

    fun clickHomeworkItem(
      clickItem: HomeworkLabelItem,
    ) {
      homeworkNoneChecked = false
      homeworkNormalChecked = false
      homeworkMuchChecked = false

      when (clickItem) {
        HomeworkLabelItem.NONE -> { homeworkNoneChecked = true }
        HomeworkLabelItem.NORMAL -> { homeworkNormalChecked = true }
        HomeworkLabelItem.MUCH -> { homeworkMuchChecked = true }
      }
    }

    fun clickTeamItem(
      clickItem: TeamLabelItem,
    ) {
      teamNoneChecked = false
      teamExistChecked = false

      when (clickItem) {
        TeamLabelItem.NONE -> { teamNoneChecked = true }
        TeamLabelItem.EXIST -> { teamExistChecked = true }
      }
    }

    MyClassReviewEditScreen(
      padding = PaddingValues(0.dp),
      uiState = MyClassReviewEditState(
        honeyRating = honeyRating,
        learningRating = learningRating,
        satisfactionRating = satisfactionRating,
        gradeGenerousChecked = gradeGenerousChecked,
        gradeNormalChecked = gradeNormalChecked,
        gradePickyChecked = gradePickyChecked,
        homeworkNoneChecked = homeworkNoneChecked,
        homeworkNormalChecked = homeworkNormalChecked,
        homeworkMuchChecked = homeworkMuchChecked,
        teamNoneChecked = teamNoneChecked,
        teamExistChecked = teamExistChecked,
        classReview = classReview,
        showDeleteClassReviewDialog = showDeleteClassReviewDialog,
      ),
      scrollState = scrollState,
      isSemesterBottomSheetExpanded = isSemesterBottomSheetExpanded,
      onClickSemesterButton = { isSemesterBottomSheetExpanded = true },
      onSemesterBottomSheetDismissRequest = { isSemesterBottomSheetExpanded = false },
      onHoneyRatingValueChange = { honeyRating = if (it < 0.5) 0.5F else it },
      onLearningRatingValueChange = { learningRating = if (it < 0.5) 0.5F else it },
      onSatisfactionRatingValueChange = { satisfactionRating = if (it < 0.5) 0.5F else it },
      onClickGradeGenerous = { clickGradeItem(GradeLabelItem.GENEROUS) },
      onClickGradeNormal = { clickGradeItem(GradeLabelItem.NORMAL) },
      onClickGradePicky = { clickGradeItem(GradeLabelItem.PICKY) },
      onClickHomeworkNone = { clickHomeworkItem(HomeworkLabelItem.NONE) },
      onClickHomeworkNormal = { clickHomeworkItem(HomeworkLabelItem.NORMAL) },
      onClickHomeworkMuch = { clickHomeworkItem(HomeworkLabelItem.MUCH) },
      onClickTeamNone = { clickTeamItem(TeamLabelItem.NONE) },
      onClickTeamExist = { clickTeamItem(TeamLabelItem.EXIST) },
      onClassReviewValueChange = { classReview = it },
      onClickClassReviewReviseButton = { /*TODO("PopBackStack")*/ },
      onClickClassReviewDeleteButton = { showDeleteClassReviewDialog = true },
      onDismissClassReviewDelete = { showDeleteClassReviewDialog = false },
    )
  }
}
