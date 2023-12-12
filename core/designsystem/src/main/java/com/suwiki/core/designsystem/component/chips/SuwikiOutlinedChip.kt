package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiOutlinedChip(
  text: String,
  isChecked: Boolean,
  onClick: () -> Unit = {},
) {
  val (borderLineColor, contentColor) = if (isChecked) {
    Primary to Primary
  } else {
    GrayDA to Gray95
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(5.dp))
      .clickable(onClick = onClick)
      .wrapContentHeight()
      .background(color = White)
      .border(width = 1.dp, color = borderLineColor, shape = RoundedCornerShape(5.dp))
      .padding(vertical = 4.dp, horizontal = 6.dp),
  ) {
    Text(
      text = text,
      style = SuwikiTheme.typography.caption1,
      color = contentColor,
      modifier = Modifier
        .align(Alignment.Center),
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiOutlinedChipPreview() {
  var isChecked by remember { mutableStateOf(false) }

  SuwikiTheme {
    SuwikiOutlinedChip(
      text = "label",
      isChecked = isChecked,
      onClick = { isChecked = !isChecked },
    )
  }
}
