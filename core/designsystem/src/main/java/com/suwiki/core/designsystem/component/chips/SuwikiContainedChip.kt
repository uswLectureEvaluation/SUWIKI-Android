package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.suwiki.core.designsystem.theme.Blue10
import com.suwiki.core.designsystem.theme.Blue100
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Green10
import com.suwiki.core.designsystem.theme.Green100
import com.suwiki.core.designsystem.theme.Orange10
import com.suwiki.core.designsystem.theme.Orange100
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

enum class ChipColor(
  val backgroundColor: Color,
  val contentColor: Color,
) {
  GREEN(
    backgroundColor = Green10,
    contentColor = Green100,
  ),
  BLUE(
    backgroundColor = Blue10,
    contentColor = Blue100,
  ),
  ORANGE(
    backgroundColor = Orange10,
    contentColor = Orange100,
  ),
}

@Composable
fun SuwikiContainedChip(
  modifier: Modifier = Modifier,
  isChecked: Boolean,
  onClick: () -> Unit = {},
  color: ChipColor,
  text: String,
) {
  with(color) {
    Box(
      modifier = modifier
        .clip(RoundedCornerShape(5.dp))
        .background(color = if (isChecked) backgroundColor else GrayF6)
        .suwikiClickable(onClick = onClick)
        .padding(vertical = 4.dp, horizontal = 6.dp),
    ) {
      Text(
        text = text,
        style = SuwikiTheme.typography.caption1,
        color = if (isChecked) contentColor else Gray95,
        modifier = Modifier.align(Alignment.Center),
      )
    }
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
      color = ChipColor.GREEN,
      text = "label",
    )
  }
}
