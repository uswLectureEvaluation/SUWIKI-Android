package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R

@Composable
fun SuwikiNonCheckedColorChip(
  onClick: () -> Unit = {},
) {
  Box(
    modifier = Modifier.size(40.dp),
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_color_chip),
      contentDescription = "",
      modifier = Modifier.clickable(onClick = onClick),
    )
  }
}

@Preview
@Composable
fun SuwikiNonCheckedColorChipPreview() {
  SuwikiNonCheckedColorChip { }
}
