package com.suwiki.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.ratingbar.SuwikiRatingBar
import com.suwiki.core.designsystem.shadow.cardShadow
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiReviewGradeCard(
  modifier: Modifier = Modifier,
  reviewCount: Int,
  rating: Float,
  honeyRating: Float,
  learningRating: Float,
  satisfactionRating: Float,
  onClick: () -> Unit,
) {
  val reviewCountColor = if (reviewCount > 0) Primary else GrayDA
  val reviewIndicatorColor = if (reviewCount > 0) Black else GrayDA
  Row(
    modifier = modifier
      .fillMaxWidth()
      .cardShadow()
      .clip(RoundedCornerShape(10.dp))
      .suwikiClickable(onClick = onClick)
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
        text = "$rating",
        style = SuwikiTheme.typography.header1,
        color = reviewCountColor,
      )
      SuwikiRatingBar(
        rating = rating,
      )
      if (reviewCount > 0) {
        Text(
          text = stringResource(id = R.string.review_count, reviewCount),
          style = SuwikiTheme.typography.caption7,
          color = Gray95,
        )
      }
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
      SuwikiProgressLine(
        name = stringResource(id = R.string.honey_quality),
        rating = honeyRating,
        textColor = reviewIndicatorColor,
      )
      SuwikiProgressLine(
        name = stringResource(id = R.string.learning_quality),
        rating = learningRating,
        textColor = reviewIndicatorColor,
      )
      SuwikiProgressLine(
        name = stringResource(id = R.string.satisfaction_quality),
        rating = satisfactionRating,
        textColor = reviewIndicatorColor,
      )
    }
  }
}

@Composable
fun SuwikiProgressLine(
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
      modifier = Modifier
        .weight(1f)
        .height(6.dp)
        .padding(horizontal = 10.dp)
        .clip(RoundedCornerShape(4.dp)),
      progress = rating / 5.0f,
      color = Primary,
      strokeCap = StrokeCap.Round,
    )
    Text(
      text = "$rating",
      style = SuwikiTheme.typography.caption1,
      color = textColor,
    )
  }
}

@Preview
@Composable
fun SuwikiReviewGradeCardPreview() {
  SuwikiTheme {
    Column(
      modifier = Modifier.background(Primary),
    ) {
      SuwikiReviewGradeCard(
        modifier = Modifier,
        onClick = {},
        reviewCount = 4,
        rating = 4.3f,
        honeyRating = 3.4f,
        learningRating = 3.4f,
        satisfactionRating = 4.4f,
      )
      Spacer(modifier = Modifier.height(20.dp))
      SuwikiReviewGradeCard(
        modifier = Modifier,
        onClick = {},
        reviewCount = 0,
        rating = 0.0f,
        honeyRating = 0.0f,
        learningRating = 0.0f,
        satisfactionRating = 0.0f,
      )
    }
  }
}
