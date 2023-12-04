package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwiki.core.designsystem.component.chips.ui.theme.F6
import com.suwiki.core.designsystem.component.chips.ui.theme.TestTheme
import com.suwiki.core.designsystem.component.chips.ui.theme.blue10
import com.suwiki.core.designsystem.component.chips.ui.theme.blue100
import com.suwiki.core.designsystem.component.chips.ui.theme.c95
import com.suwiki.core.designsystem.component.chips.ui.theme.green10
import com.suwiki.core.designsystem.component.chips.ui.theme.green100
import com.suwiki.core.designsystem.component.chips.ui.theme.orange10
import com.suwiki.core.designsystem.component.chips.ui.theme.orange100
enum class SuwikiLabelType(val type: String) {
  ORANGE("orange"),
  BLUE("blue"),
  GREEN("green"),
}

@Composable
fun SuwikiContainedChips(type: SuwikiLabelType, text: String) {
  var chipState by remember { mutableStateOf(false) }

  val backgroundColor: Color
  val contentColor: Color

  when (chipState) {
    false -> {
      backgroundColor = F6
      contentColor = c95
    }
    true -> {
      when (type) {
        SuwikiLabelType.ORANGE -> {
          backgroundColor = orange10
          contentColor = orange100
        }
        SuwikiLabelType.BLUE -> {
          backgroundColor = blue10
          contentColor = blue100
        }
        SuwikiLabelType.GREEN -> {
          backgroundColor = green10
          contentColor = green100
        }
      }
    }
  }

  SuwikiContainedChipsItem(
    text = text,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    onBtnClicked = { chipState = !chipState },
  )
}

@Composable
fun SuwikiContainedChipsItem(
  text: String,
  backgroundColor: Color,
  contentColor: Color,
  onBtnClicked: () -> Unit,
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(5.dp))
      .background(color = backgroundColor)
      .clickable { onBtnClicked() }
      .size(41.dp, 26.dp),
  ) {
    Text(
      text = text,
      color = contentColor,
      modifier = Modifier
        .align(Alignment.Center),
      fontSize = 12.sp,
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiContainedChipsPreview() {
  TestTheme {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      SuwikiContainedChips(SuwikiLabelType.ORANGE, "label")
    }
  }
}
