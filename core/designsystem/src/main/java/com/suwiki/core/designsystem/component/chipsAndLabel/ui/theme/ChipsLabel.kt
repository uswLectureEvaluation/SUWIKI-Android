package com.suwiki.core.designsystem.component.chipsAndLabel.ui.theme

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

enum class LabelType(val type: String) {
  ORANGE("orange"),
  BLUE("blue"),
  GREEN("green"),
}

class ChipsLabel {

  @Composable
  fun LabelButton(type: LabelType, text: String) {
    var labelState by remember { mutableStateOf(false) }

    val backgroundColor: Color
    val contentColor: Color

    when (labelState) {
      false -> {
        backgroundColor = F6
        contentColor = c95
      }
      true -> {
        when (type) {
          LabelType.ORANGE -> {
            backgroundColor = orange10
            contentColor = orange100
          }
          LabelType.BLUE -> {
            backgroundColor = blue10
            contentColor = blue100
          }
          LabelType.GREEN -> {
            backgroundColor = green10
            contentColor = green100
          }
        }
      }
    }

    InactiveContainedLabel(
      text = text,
      backgroundColor = backgroundColor,
      contentColor = contentColor,
      onBtnClicked = { labelState = !labelState },
    )
  }

  @Composable
  fun InactiveContainedLabel(
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

  @Preview
  @Composable
  fun PreviewLabel() {
    TestTheme {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        LabelButton(LabelType.ORANGE, "label")
      }
    }
  }
}
