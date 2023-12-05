package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
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
enum class SuwikiChipType {
  ORANGE,
  BLUE,
  GREEN,
}

@Composable
fun SuwikiContainedChip(
  isChecked: Boolean,
  onClick: () -> Unit = {},
  type: SuwikiChipType,
  text: String,
) {
  val (backgroundColor, contentColor) = if (isChecked) {
    when (type) {
      SuwikiChipType.ORANGE -> Color(0xFFFFF3EB) to Color(0xFFFD873B)
      SuwikiChipType.BLUE -> Color(0xFFECEDFF) to Color(0xFF3D4EFB)
      SuwikiChipType.GREEN -> Color(0xFFEAF8EC) to Color(0xFF2DB942)
    }
  } else {
    Color(0xFFF6F6F6) to Color(0xFF959595)
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(5.dp))
      .background(color = backgroundColor)
      .clickable(onClick = onClick)
      .height(26.dp),
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
fun SuwikiContainedChipPreview() {
  var isChecked by remember { mutableStateOf(false) }

  SuwikiContainedChip(
    isChecked = isChecked,
    onClick = { isChecked = !isChecked },
    type = SuwikiChipType.GREEN,
    text = "label",
  )
}
