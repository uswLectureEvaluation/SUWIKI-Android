package com.suwiki.core.designsystem.component.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SuwikiSliderThumb() {
  Box(
    modifier = Modifier.size(28.dp),
    contentAlignment = Alignment.Center,
  ) {
    Spacer(
      modifier = Modifier
        .size(16.dp)
        .clip(CircleShape)
        .background(Color.Blue),
    )
  }
}

@Preview
@Composable
fun SuwikiSliderThumbPreview() {
  SuwikiSliderThumb()
}
