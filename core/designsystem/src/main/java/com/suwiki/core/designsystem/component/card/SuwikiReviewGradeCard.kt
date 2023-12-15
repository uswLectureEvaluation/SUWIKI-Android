package com.suwiki.core.designsystem.component.card

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiReviewGradeCard(
  modifier: Modifier = Modifier,
  reviewCount: Int,
  ratingCount: Float,
  honeyQualityCount: Float,
  learningQualityCount: Float,
  satisfactionCount: Float,
  onClick: () -> Unit,
  pressedBackgroundColor: Color,
) {
  val reviewCountColor = if (reviewCount > 0) Primary else GrayDA
  val reviewIndicatorColor = if (reviewCount > 0) Black else GrayDA
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .suwikiClickable(
        rippleEnabled = true,
        rippleColor = pressedBackgroundColor,
        onClick = onClick,
      )
      .shadow(4.dp),
    contentAlignment = Alignment.Center,
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(),
      shape = RoundedCornerShape(10.dp),
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp)
          .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
      ) {
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(
            text = "$ratingCount",
            style = SuwikiTheme.typography.header1,
            color = reviewCountColor,
          )
          RatingBar(ratingCount.toInt())
          if (reviewCount > 0) {
            Text(
              text = "$reviewCount${stringResource(id = R.string.review_count)}",
              style = SuwikiTheme.typography.caption7,
              color = Gray95,
            )
          }
        }
        VerticalDivider(
          color = GrayF6,
          modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .padding(vertical = 3.dp),
        )
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          SuwikiProgressLine(stringResource(id = R.string.honey_quality), "$honeyQualityCount", reviewIndicatorColor, honeyQualityCount)
          SuwikiProgressLine(stringResource(id = R.string.learning_quality), "$learningQualityCount", reviewIndicatorColor, learningQualityCount)
          SuwikiProgressLine(stringResource(id = R.string.satisfaction_quality), "$satisfactionCount", reviewIndicatorColor, satisfactionCount)
        }
      }
    }
  }
}

@Composable
fun SuwikiProgressLine(
  text: String,
  text2: String,
  textColor: Color,
  progress: Float,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = text,
      style = SuwikiTheme.typography.caption5,
      color = textColor,
    )
    LinearProgressIndicator(
      modifier = Modifier
        .weight(1f)
        .height(6.dp)
        .padding(start = 8.dp)
        .clip(RoundedCornerShape(4.dp)),
      progress = progress / 5.0f,
      color = Primary,
      strokeCap = StrokeCap.Round,
    )
    Text(
      text = text2,
      style = SuwikiTheme.typography.caption5,
      color = textColor,
      modifier = Modifier.padding(start = 8.dp),
    )
  }
}

@Composable
fun RatingBar(selectedRating: Int) {
  Row {
    for (i in 1..5) {
      StarIcon(
        isFilled = i <= selectedRating,
      )
    }
  }
}

@Composable
fun StarIcon(isFilled: Boolean) {
  val iconColor = if (isFilled) Primary else GrayDA
  Icon(
    imageVector = Icons.Default.Star,
    contentDescription = null,
    tint = iconColor,
    modifier = Modifier
      .clip(CircleShape)
      .size(12.dp),
  )
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
        ratingCount = 4.3f,
        honeyQualityCount = 3.4f,
        learningQualityCount = 3.4f,
        satisfactionCount = 4.4f,
        pressedBackgroundColor = Primary,
      )
      Spacer(modifier = Modifier.height(20.dp))
      SuwikiReviewGradeCard(
        modifier = Modifier,
        onClick = {},
        reviewCount = 0,
        ratingCount = 0.0f,
        honeyQualityCount = 0.0f,
        learningQualityCount = 0.0f,
        satisfactionCount = 0.0f,
        pressedBackgroundColor = Primary,
      )
    }
  }
}