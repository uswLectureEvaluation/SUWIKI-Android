package com.suwiki.core.designsystem.component.card

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
  buttonText: String,
  onClick: () -> Unit,
  onClickAdd: () -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .suwikiClickable(
        rippleEnabled = true,
        rippleColor = Primary,
        onClick = onClick,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(),
      shape = RoundedCornerShape(10.dp),
    ) {
      Row {

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
                .width(1.dp)
                .padding(vertical = 3.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = professor,
              style = SuwikiTheme.typography.body6,
              color = Black,
            )
          }
          Spacer(modifier = Modifier.height(1.dp))
          Row(
            modifier = Modifier
              .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
          ) {
            Text(
              text = day,
              style = SuwikiTheme.typography.caption4,
              color = Gray6A,
            )
            Text(
              text = classPeriod,
              style = SuwikiTheme.typography.caption4,
              color = Gray6A,
            )
            Text(
              text = lectureRoom,
              style = SuwikiTheme.typography.caption4,
              color = Gray6A,
            )
            Spacer(modifier = Modifier.weight(1f))
            SuwikiContainedGreyButtonSmall(
              text = buttonText,
              onClick = onClickAdd,
            )
          }
          Row(
            modifier = Modifier
              .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
          ) {
            Text(
              text = grade,
              style = SuwikiTheme.typography.caption4,
              color = Gray6A,
            )
            Text(
              text = classType,
              style = SuwikiTheme.typography.caption4,
              color = Gray6A,
            )
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
        buttonText = "추가",
        onClick = {},
        onClickAdd = {},
      )
    }
  }
}
