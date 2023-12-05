package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun SuwikiOutlinedChip(
  text: String,
  isChecked: Boolean,
  onClick: () -> Unit = {},
) {
  val (borderLineColor, contentColor) = if (isChecked) {
    Color(0xFFDADADA) to Color(0xFF959595)
  } else {
    Color(0xFF346CFD) to Color(0xFF346CFD)
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(5.dp))
      .clickable(onClick = onClick)
      .height(26.dp)
      .background(color = Color(0xFFFFFFFF))
      .border(width = 1.dp, color = borderLineColor, shape = RoundedCornerShape(5.dp)),
  ) {
    Box(
      modifier = Modifier
        .padding(vertical = 4.dp, horizontal = 6.dp)
        .height(18.dp),
    ) {
      Text(
        text = text,
        color = contentColor,
        fontSize = 12.sp,
        modifier = Modifier.align(Alignment.Center),
      )
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiOutlinedChipPreview() {
  var isChecked by remember { mutableStateOf(false) }

  SuwikiOutlinedChip(
    text = "label",
    isChecked = isChecked,
    onClick = { isChecked = !isChecked },
  )
}
