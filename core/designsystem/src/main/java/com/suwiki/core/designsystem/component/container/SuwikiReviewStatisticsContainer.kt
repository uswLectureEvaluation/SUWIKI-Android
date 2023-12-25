package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.badge.BadgeColor
import com.suwiki.core.designsystem.component.badge.SuwikiBadge
import com.suwiki.core.designsystem.component.card.SuwikiReviewGradeCard
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Blue10
import com.suwiki.core.designsystem.theme.Blue100
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Green10
import com.suwiki.core.designsystem.theme.Green100
import com.suwiki.core.designsystem.theme.Orange10
import com.suwiki.core.designsystem.theme.Orange100
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiReviewStatisticsContainer(
  modifier: Modifier = Modifier,
  lectureType: String,
  lectureName: String,
  openMajor: String,
  professor: String,
  reviewCount: Int,
  rating: Float,
  grade: String? = null,
  gradeLabelColor: LabelColor? = null,
  homework: String? = null,
  homeworkLabelColor: LabelColor? = null,
  team: String? = null,
  teamLabelColor: LabelColor? = null,
  honeyRating: Float,
  learningRating: Float,
  satisfactionRating: Float,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(top = 14.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    SuwikiBadge(color = BadgeColor.Gray, text = lectureType)
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = lectureName,
      style = SuwikiTheme.typography.header3,
      color = Black,
    )
    Spacer(modifier = Modifier.height(2.dp))
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .wrapContentWidth()
        .height(IntrinsicSize.Min),
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Text(
        text = openMajor,
        style = SuwikiTheme.typography.body7,
        color = Gray6A,
      )
      VerticalDivider(
        color = GrayDA,
        modifier = Modifier
          .fillMaxHeight()
          .width(1.dp)
          .padding(vertical = 3.dp),
      )
      Text(
        text = professor,
        style = SuwikiTheme.typography.body7,
        color = Gray6A,
      )
    }

    val isGradeValid = grade != null && gradeLabelColor != null
    val isHomeworkValid = homework != null && homeworkLabelColor != null
    val isTeamValid = team != null && teamLabelColor != null

    if (
      rating > 0.0f &&
      isGradeValid &&
      isHomeworkValid &&
      isTeamValid
    ) {
      Spacer(modifier = Modifier.height(14.dp))
      Row(
        horizontalArrangement = Arrangement.spacedBy(7.dp),
      ) {
        StatisticsLabel(
          color = gradeLabelColor!!,
          name = stringResource(R.string.word_grade),
          value = grade!!,
        )
        StatisticsLabel(
          color = homeworkLabelColor!!,
          name = stringResource(R.string.word_homework),
          value = homework!!,
        )
        StatisticsLabel(
          color = teamLabelColor!!,
          name = stringResource(R.string.word_team),
          value = team!!,
        )
      }
    }
    Spacer(modifier = Modifier.height(23.dp))
    SuwikiReviewGradeCard(
      reviewCount = reviewCount,
      rating = rating,
      honeyRating = honeyRating,
      learningRating = learningRating,
      satisfactionRating = satisfactionRating,
    )
  }
}

enum class LabelColor(
  val backgroundColor: Color,
  val contentColor: Color,
) {
  ORANGE(
    backgroundColor = Orange10,
    contentColor = Orange100,
  ),
  BLUE(
    backgroundColor = Blue10,
    contentColor = Blue100,
  ),
  GREEN(
    backgroundColor = Green10,
    contentColor = Green100,
  ),
}

@Composable
private fun StatisticsLabel(
  modifier: Modifier = Modifier,
  color: LabelColor,
  name: String,
  value: String,
) {
  with(color) {
    Box(
      modifier = modifier
        .clip(RoundedCornerShape(5.dp))
        .background(color = backgroundColor)
        .wrapContentHeight()
        .padding(vertical = 4.dp, horizontal = 6.dp),
    ) {
      Row(
        modifier = Modifier.wrapContentWidth(),
      ) {
        Text(
          text = name,
          style = SuwikiTheme.typography.caption2,
          color = contentColor,
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
          text = value,
          style = SuwikiTheme.typography.caption1,
          color = contentColor,
        )
      }
    }
  }
}

@Preview
@Composable
fun SuwikiReviewStaticsContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiReviewStatisticsContainer(
        lectureType = "강의유형",
        lectureName = "Tilte",
        openMajor = "개설학과",
        professor = "교수명",
        reviewCount = 3,
        rating = 3.0f,
        honeyRating = 3.0f,
        learningRating = 3.0f,
        satisfactionRating = 3.0f,
        grade = "label",
        gradeLabelColor = LabelColor.BLUE,
        homework = "label",
        homeworkLabelColor = LabelColor.GREEN,
        team = "label",
        teamLabelColor = LabelColor.ORANGE,
      )
      SuwikiReviewStatisticsContainer(
        lectureType = "강의유형",
        lectureName = "Tilte",
        openMajor = "개설학과",
        professor = "교수명",
        reviewCount = 0,
        rating = 0.0f,
        honeyRating = 0.0f,
        learningRating = 0.0f,
        satisfactionRating = 0.0f,
      )
    }
  }
}
