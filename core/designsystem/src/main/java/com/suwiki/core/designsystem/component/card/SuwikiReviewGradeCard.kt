package com.suwiki.core.designsystem.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiReviewGradeCard(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight(),
//      .suwikiClickable(
//        rippleEnabled = true,
//        rippleColor = pressedBackgroundColor,
//        onClick = onClick,
//      ),
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
      ) {
        Column(
          modifier = Modifier.wrapContentHeight(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(
            text = "3.0",
            style = SuwikiTheme.typography.header1,
            color = Primary,
          )
          RatingBar(3)
          Text(
            text = "3개의 리뷰",
            style = SuwikiTheme.typography.caption7,
            color = Gray95,
          )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
          Text(text = "3.0")
          Image(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
          )

          LinearProgressIndicator(
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(4.dp)),
            progress = 0.7f,
            color = Primary,
          )
        }
      }
    }
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
      )
      RatingBar(3)
    }
  }
}
