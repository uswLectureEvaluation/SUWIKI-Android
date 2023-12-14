package com.suwiki.core.designsystem.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiClassReviewCard(
  modifier: Modifier = Modifier,
  className: String,
  department: String,
  professor: String,
  rating: String,
  reviewCount: String,
  classType: String,
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
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(15.dp, 14.dp, 18.dp, 12.dp),
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Column {
            Text(
              text = className,
              style = SuwikiTheme.typography.header3,
              color = Black,
            )
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.wrapContentSize(),
            ) {
              Text(
                text = department,
                style = SuwikiTheme.typography.body7,
                color = Gray6A,
              )
              VerticalDivider(
                modifier = Modifier
                  .width(9.dp)
                  .padding(4.dp)
                  .fillMaxHeight(0.02f)
                  .background(Black),
              )
              Text(
                text = professor,
                style = SuwikiTheme.typography.body7,
                color = Gray6A,
              )
            }
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.wrapContentWidth(),
            ) {
              Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
              )
              Text(
                text = rating,
                style = SuwikiTheme.typography.body1,
                color = Primary,
              )
              Text(
                text = reviewCount,
                style = SuwikiTheme.typography.body3,
                color = Gray95,
              )
            }
          }
          Text(
            text = classType,
            style = SuwikiTheme.typography.caption4,
            color = Gray6A,
            modifier = Modifier
              .background(color = GrayF6, shape = RoundedCornerShape(10.dp))
              .padding(6.dp, 2.dp),
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun CardPreview() {
  SuwikiTheme {
    SuwikiClassReviewCard(
      modifier = Modifier,
      className = "강의명",
      department = "개설학과",
      professor = "교수명",
      rating = "평점",
      reviewCount = "(리뷰개수)",
      classType = "강의 유형",
      onClick = {},
      pressedBackgroundColor = Primary,
    )
  }
}
