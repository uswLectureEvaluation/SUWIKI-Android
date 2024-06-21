package com.suwiki.presentation.common.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.presentation.common.designsystem.theme.Gray95
import com.suwiki.presentation.common.designsystem.theme.GrayDA
import com.suwiki.presentation.common.designsystem.theme.Primary
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.common.designsystem.theme.White
import com.suwiki.presentation.common.ui.extension.suwikiClickable

@Composable
fun SuwikiOutlinedChip(
  modifier: Modifier = Modifier,
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
    modifier = modifier
      .clip(RoundedCornerShape(5.dp))
      .suwikiClickable(onClick = onClick)
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
