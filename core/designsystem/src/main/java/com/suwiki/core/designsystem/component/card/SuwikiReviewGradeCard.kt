package com.suwiki.core.designsystem.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
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
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .suwikiClickable(
        rippleEnabled = true,
        rippleColor = pressedBackgroundColor,
        onClick = onClick,
      ),
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
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(
          modifier = Modifier
            .padding(end = 40.dp),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(
            text = "$ratingCount",
            style = SuwikiTheme.typography.header1,
            color = Primary,
          )
          RatingBar(ratingCount.toInt())
          Text(
            text = "$reviewCount 개의 리뷰",
            style = SuwikiTheme.typography.caption7,
            color = Gray95,
          )
        }
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          SuwikiProgressLine("꿀강지수", "$honeyQualityCount", 1f)
          SuwikiProgressLine("배움지수", "$learningQualityCount", 1f)
          SuwikiProgressLine("만족도수", "$satisfactionCount", 1f)
        }
      }
    }
  }
}

@Composable
fun SuwikiProgressLine(
  text: String,
  text2: String,
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
      color = Black,
      modifier = Modifier.widthIn(max = 122.dp),
    )
    LinearProgressIndicator(
      modifier = Modifier
        .weight(1f)
        .height(6.dp)
        .padding(start = 8.dp)
        .clip(RoundedCornerShape(4.dp)),
      progress = progress,
      color = Primary,
    )
    Text(
      text = text2,
      style = SuwikiTheme.typography.caption5,
      color = Black,
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
  val iconColor = if (isFilled) Primary else Gray95
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
    Column {
      SuwikiReviewGradeCard(
        modifier = Modifier,
        onClick = {},
        reviewCount = 4,
        ratingCount = 4.3f,
        honeyQualityCount = 3.3f,
        learningQualityCount = 3.4f,
        satisfactionCount = 4.4f,
        pressedBackgroundColor = Primary,
      )
    }
  }
}
