package com.suwiki.core.designsystem.component.card

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.badge.BadgeColor
import com.suwiki.core.designsystem.component.badge.SuwikiBadge
import com.suwiki.core.designsystem.shadow.cardShadow
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable
import java.util.Locale

@Composable
fun SuwikiClassReviewCard(
  modifier: Modifier = Modifier,
  className: String,
  openMajor: String,
  professor: String,
  rating: Float,
  classType: String,
  onClick: () -> Unit,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .cardShadow()
      .clip(RoundedCornerShape(10.dp))
      .suwikiClickable(onClick = onClick)
      .background(White)
      .padding(horizontal = 16.dp, vertical = 13.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(
      modifier = Modifier.weight(1f),
    ) {
      Text(
        modifier = Modifier
          .wrapContentHeight(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = className,
        style = SuwikiTheme.typography.header3,
        color = Black,
      )
      Row(
        modifier = Modifier
          .wrapContentSize()
          .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
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
          text = if (professor == "null") stringResource(id = com.suwiki.core.ui.R.string.word_none) else professor,
          style = SuwikiTheme.typography.body7,
          color = Gray6A,
        )
      }
      Spacer(modifier = Modifier.height(3.dp))
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.wrapContentWidth(),
      ) {
        Image(
          painter = painterResource(id = R.drawable.ic_star),
          contentDescription = null,
        )
        Text(
          text = "%.1f".format(Locale.US, rating),
          style = SuwikiTheme.typography.body1,
          color = Primary,
        )
      }
    }

    SuwikiBadge(
      text = classType,
      color = BadgeColor.Gray,
    )
  }
}

@Preview
@Composable
fun CardPreview() {
  SuwikiTheme {
    Box(modifier = Modifier.padding(10.dp)) {
      SuwikiClassReviewCard(
        modifier = Modifier,
        className = "강의명강의명강의명강의명강의명강의명",
        openMajor = "개설학과",
        professor = "교수명",
        rating = 4.0f,
        classType = "강의 유형",
        onClick = {},
      )
    }
  }
}
