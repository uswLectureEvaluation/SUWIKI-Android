package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
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
enum class SuwikiChipType {
  ORANGE,
  BLUE,
  GREEN,
  DISABLE
}

@Composable
fun SuwikiContainedChip(type: SuwikiChipType, text: String) {
  var chipState by remember { mutableStateOf(false) }

  val backgroundColor: Color
  val contentColor: Color

  when (type) {
    SuwikiChipType.ORANGE -> {
      backgroundColor = Color(0xFFFFF3EB)
      contentColor = Color(0xFFFD873B)
    }
    SuwikiChipType.BLUE -> {
      backgroundColor = Color(0xFFECEDFF)
      contentColor = Color(0xFF3D4EFB)
    }
    SuwikiChipType.GREEN -> {
      backgroundColor = Color(0xFFEAF8EC)
      contentColor = Color(0xFF2DB942)
    }
    SuwikiChipType.DISABLE -> {
      backgroundColor = Color(0xFFF6F6F6)
      contentColor = Color(0xFF959595)
    }
  }

  SuwikiContainedChipItem(
    text = text,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    onClick = { chipState = !chipState },
  )
}

@Composable
fun SuwikiContainedChipItem(
  text: String,
  backgroundColor: Color,
  contentColor: Color,
  onClick: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(5.dp))
      .background(color = backgroundColor)
      .clickable(onClick = onClick)
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
fun SuwikiContainedChipPreview() {
  SuwikiContainedChip(SuwikiChipType.ORANGE, "label")
}
