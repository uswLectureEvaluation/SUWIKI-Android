package com.suwiki.presentation.lectureevaluation.viewerreporter.detail.component

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import com.suwiki.core.model.lectureevaluation.lecture.LectureInfo
import com.suwiki.presentation.common.designsystem.component.badge.BadgeColor
import com.suwiki.presentation.common.designsystem.component.badge.SuwikiBadge
import com.suwiki.presentation.common.designsystem.component.ratingbar.SuwikiRatingBar
import com.suwiki.presentation.common.designsystem.shadow.cardShadow
import com.suwiki.presentation.common.designsystem.theme.Black
import com.suwiki.presentation.common.designsystem.theme.Blue10
import com.suwiki.presentation.common.designsystem.theme.Blue100
import com.suwiki.presentation.common.designsystem.theme.Gray6A
import com.suwiki.presentation.common.designsystem.theme.GrayDA
import com.suwiki.presentation.common.designsystem.theme.GrayF6
import com.suwiki.presentation.common.designsystem.theme.GrayFB
import com.suwiki.presentation.common.designsystem.theme.Green10
import com.suwiki.presentation.common.designsystem.theme.Green100
import com.suwiki.presentation.common.designsystem.theme.Orange10
import com.suwiki.presentation.common.designsystem.theme.Orange100
import com.suwiki.presentation.common.designsystem.theme.Primary
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.common.designsystem.theme.White
import com.suwiki.presentation.common.ui.R
import com.suwiki.presentation.common.ui.extension.toText
import java.util.Locale

@Composable
fun SuwikiReviewStatisticsContainer(
  modifier: Modifier = Modifier,
  data: LectureEvaluationExtraAverage,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(GrayFB)
      .padding(top = 14.dp, bottom = 24.dp, start = 24.dp, end = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    SuwikiBadge(color = BadgeColor.Gray, text = data.info.lectureType)
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = data.info.lectureName,
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
        text = data.info.majorType,
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
        text = if (data.info.professor == "null") stringResource(id = com.suwiki.presentation.common.ui.R.string.word_none) else data.info.professor,
        style = SuwikiTheme.typography.body7,
        color = Gray6A,
      )
    }

    if (data.totalAvg > 0.0f) {
      Spacer(modifier = Modifier.height(14.dp))
      Row(
        horizontalArrangement = Arrangement.spacedBy(7.dp),
      ) {
        StatisticsLabel(
          color = LabelColor.entries[data.gradeAvg.ordinal],
          name = stringResource(R.string.word_grade),
          value = data.gradeAvg.toText(),
        )
        StatisticsLabel(
          color = LabelColor.entries[data.homeworkAvg.ordinal],
          name = stringResource(R.string.word_homework),
          value = data.homeworkAvg.toText(),
        )
        StatisticsLabel(
          color = LabelColor.entries.minus(LabelColor.BLUE)[data.teamAvg.ordinal],
          name = stringResource(R.string.word_team),
          value = data.teamAvg.toText(),
        )
      }
    }
    Spacer(modifier = Modifier.height(23.dp))
    ReviewGradeCard(
      rating = data.totalAvg,
      honeyRating = data.honeyAvg,
      learningRating = data.learningAvg,
      satisfactionRating = data.satisfactionAvg,
    )
  }
}

@Composable
fun ReviewGradeCard(
  modifier: Modifier = Modifier,
  rating: Float,
  honeyRating: Float,
  learningRating: Float,
  satisfactionRating: Float,
) {
  val reviewCountColor = if (rating > 0) Primary else GrayDA
  val reviewIndicatorColor = if (rating > 0) Black else GrayDA
  Row(
    modifier = modifier
      .fillMaxWidth()
      .cardShadow()
      .clip(RoundedCornerShape(10.dp))
      .background(White)
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(20.dp),
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = "%.1f".format(Locale.US, rating),
        style = SuwikiTheme.typography.header1,
        color = reviewCountColor,
      )
      SuwikiRatingBar(
        rating = rating,
      )
    }
    VerticalDivider(
      color = GrayF6,
      modifier = Modifier
        .height(49.dp)
        .width(1.dp),
    )
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      ReviewProgressLine(
        name = stringResource(id = com.suwiki.presentation.common.ui.R.string.word_honey_rating),
        rating = honeyRating,
        textColor = reviewIndicatorColor,
      )
      ReviewProgressLine(
        name = stringResource(id = R.string.learning_quality),
        rating = learningRating,
        textColor = reviewIndicatorColor,
      )
      ReviewProgressLine(
        name = stringResource(id = R.string.satisfaction_quality),
        rating = satisfactionRating,
        textColor = reviewIndicatorColor,
      )
    }
  }
}

@Composable
fun ReviewProgressLine(
  name: String,
  rating: Float,
  textColor: Color,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = name,
      style = SuwikiTheme.typography.caption5,
      color = textColor,
    )
    LinearProgressIndicator(
      progress = { rating / 5.0f },
      modifier = Modifier
        .weight(1f)
        .height(6.dp)
        .padding(horizontal = 10.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = Primary,
      strokeCap = StrokeCap.Round,
    )
    Text(
      text = "%.1f".format(Locale.US, rating),
      style = SuwikiTheme.typography.caption1,
      color = textColor,
    )
  }
}

enum class LabelColor(
  val backgroundColor: Color,
  val contentColor: Color,
) {
  GREEN(
    backgroundColor = Green10,
    contentColor = Green100,
  ),
  BLUE(
    backgroundColor = Blue10,
    contentColor = Blue100,
  ),
  ORANGE(
    backgroundColor = Orange10,
    contentColor = Orange100,
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
        data = LectureEvaluationExtraAverage(
          id = 0,
          info = LectureInfo(
            semesterList = listOf(),
            professor = "교수",
            majorType = "전공",
            lectureType = "유형",
            lectureName = "이름",
          ),
          totalAvg = 3.0f,
          satisfactionAvg = 3.0f,
          honeyAvg = 3.0f,
          learningAvg = 3.0f,
          teamAvg = TeamLevel.EXIST,
          gradeAvg = GradeLevel.DIFFICULT,
          homeworkAvg = HomeworkLevel.MANY,
        ),
      )
      SuwikiReviewStatisticsContainer(
        data = LectureEvaluationExtraAverage(
          id = 0,
          info = LectureInfo(
            semesterList = listOf(),
            professor = "교수",
            majorType = "전공",
            lectureType = "유형",
            lectureName = "이름",
          ),
          totalAvg = 0.0f,
          satisfactionAvg = 0.0f,
          honeyAvg = 0.0f,
          learningAvg = 0.0f,
          teamAvg = TeamLevel.EXIST,
          gradeAvg = GradeLevel.DIFFICULT,
          homeworkAvg = HomeworkLevel.MANY,
        ),
      )
    }
  }
}
