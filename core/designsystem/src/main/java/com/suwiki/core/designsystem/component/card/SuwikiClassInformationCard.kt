package com.suwiki.core.designsystem.component.card

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.button.SuwikiContainedGreyButtonSmall
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiClassInformationCard(
  modifier: Modifier = Modifier,
  className: String,
  professor: String,
  day: String,
  classPeriod: String,
  lectureRoom: String,
  grade: String,
  classType: String,
  offeredDepartment: String,
  onClick: () -> Unit,
  onClickAdd: () -> Unit,
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
          .padding(24.dp, 14.dp, 24.dp, 19.dp),
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        ) {
          Text(
            text = className,
            style = SuwikiTheme.typography.body6,
            color = Black,
          )
          Spacer(modifier = Modifier.width(6.dp))
          VerticalDivider(
            color = GrayDA,
            modifier = Modifier
              .fillMaxHeight()
              .width(1.dp),
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = professor,
            style = SuwikiTheme.typography.body6,
            color = Black,
          )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth(),
        ) {
          Text(
            text = day,
            style = SuwikiTheme.typography.caption4,
            color = Gray6A,
          )
          Spacer(modifier = Modifier.width(2.dp))
          Text(
            text = classPeriod,
            style = SuwikiTheme.typography.caption4,
            color = Gray6A,
          )
          Spacer(modifier = Modifier.width(2.dp))
          Text(
            text = lectureRoom,
            style = SuwikiTheme.typography.caption4,
            color = Gray6A,
          )
          Spacer(modifier = Modifier.weight(1f))
          SuwikiContainedGreyButtonSmall(
            text = "추가",
            onClick = onClickAdd,
          )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth(),
        ) {
          Text(
            text = grade,
            style = SuwikiTheme.typography.caption4,
            color = Gray6A,
          )
          Spacer(modifier = Modifier.width(2.dp))
          Text(
            text = classType,
            style = SuwikiTheme.typography.caption4,
            color = Gray6A,
          )
          Spacer(modifier = Modifier.width(2.dp))
          Text(
            text = offeredDepartment,
            style = SuwikiTheme.typography.caption4,
            color = Gray6A,
          )
        }
      }
    }
  }
}

@Preview
@Composable
fun ClassInformationPreview() {
  SuwikiTheme {
    Column {
      SuwikiClassInformationCard(
        modifier = Modifier,
        className = "강의명",
        professor = "교수명",
        day = "요일",
        classPeriod = "교시",
        lectureRoom = "강의실",
        grade = "학년",
        classType = "강의유형",
        offeredDepartment = "개설학과",
        onClick = {},
        onClickAdd = {},
        pressedBackgroundColor = Primary,
      )
    }
  }
}
