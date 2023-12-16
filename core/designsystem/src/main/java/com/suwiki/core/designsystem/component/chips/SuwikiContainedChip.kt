package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Blue10
import com.suwiki.core.designsystem.theme.Blue100
import com.suwiki.core.designsystem.theme.Green10
import com.suwiki.core.designsystem.theme.Green100
import com.suwiki.core.designsystem.theme.Orange10
import com.suwiki.core.designsystem.theme.Orange100
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

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
      SuwikiChipType.ORANGE -> Orange10 to Orange100
      SuwikiChipType.BLUE -> Blue10 to Blue100
      SuwikiChipType.GREEN -> Green10 to Green100
    }
  } else {
    Color(0xFFF6F6F6) to Color(0xFF959595)
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(5.dp))
      .background(color = backgroundColor)
      .suwikiClickable(onClick = onClick)
      .wrapContentHeight()
      .padding(vertical = 4.dp, horizontal = 6.dp),
  ) {
    Text(
      text = text,
      style = SuwikiTheme.typography.caption1,
      color = contentColor,
      modifier = Modifier.align(Alignment.Center),
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiContainedChipPreview() {
  var isChecked by remember { mutableStateOf(false) }

  SuwikiTheme {
    SuwikiContainedChip(
      isChecked = isChecked,
      onClick = { isChecked = !isChecked },
      type = SuwikiChipType.GREEN,
      text = "label",
    )
  }
}
