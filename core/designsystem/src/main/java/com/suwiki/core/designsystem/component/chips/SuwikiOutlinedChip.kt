package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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

@Composable
fun SuwikiOutlinedChip(text: String) {
  var labelState by remember { mutableStateOf(false) }

  val backgroundColor: Color
  val contentColor: Color

  when (labelState) {
    false -> {
      backgroundColor = Color(0xFFDADADA)
      contentColor = Color(0xFF959595)
    }
    true -> {
      backgroundColor = Color(0xFF346CFD)
      contentColor = Color(0xFF346CFD)
    }
  }

  SuwikiOutlinedChipItem(
    text = text,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    onBtnClicked = { labelState = !labelState },
  )
}

@Composable
fun SuwikiOutlinedChipItem(
  text: String,
  backgroundColor: Color,
  contentColor: Color,
  onBtnClicked: () -> Unit,
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(5.dp))
      .clickable { onBtnClicked() }
      .size(41.dp, 26.dp)
      .background(color = Color(0xFFFFFFFF))
      .border(width = 1.dp, color = backgroundColor, shape = RoundedCornerShape(5.dp)),
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
fun SuwikiOutlinedChipPreview() {
  SuwikiOutlinedChip("label")
}
