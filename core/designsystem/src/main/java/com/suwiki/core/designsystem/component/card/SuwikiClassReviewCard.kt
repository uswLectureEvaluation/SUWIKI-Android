package com.suwiki.core.designsystem.component.card

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.chips.SuwikiChipType
import com.suwiki.core.designsystem.component.chips.SuwikiContainedChip
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiClassReviewCard(
  modifier: Modifier = Modifier,
  className: String,
  department: String,
  professor: String,
  rating: Float,
  reviewCount: Int,
  classType: String,
  isChecked: Boolean,
  onClick: () -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .suwikiClickable(
        rippleEnabled = true,
        rippleColor = Primary,
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
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(15.dp, 14.dp, 18.dp, 12.dp),
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth(),
        ) {
          Text(
            text = className,
            style = SuwikiTheme.typography.header3,
            color = Black,
          )
          Spacer(modifier = Modifier.weight(1f))
          SuwikiContainedChip(
            text = classType,
            isChecked = isChecked,
            type = SuwikiChipType.GREEN,
          )
        }
        Row(
          modifier = Modifier
            .wrapContentSize()
            .height(IntrinsicSize.Min),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
          Text(
            text = department,
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
        Spacer(modifier = Modifier.height(3.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.wrapContentWidth(),
        ) {
          Image(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
          )
          Text(
            text = "$rating",
            style = SuwikiTheme.typography.body1,
            color = Primary,
          )
          Text(
            text = "($reviewCount)",
            style = SuwikiTheme.typography.body3,
            color = Gray95,
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
      rating = 4.0f,
      reviewCount = 3,
      classType = "강의 유형",
      isChecked = false,
      onClick = {},
    )
  }
}
