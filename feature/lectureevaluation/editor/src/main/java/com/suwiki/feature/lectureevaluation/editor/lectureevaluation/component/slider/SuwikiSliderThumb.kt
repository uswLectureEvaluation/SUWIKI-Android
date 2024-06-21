package com.suwiki.feature.lectureevaluation.editor.lectureevaluation.component.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.feature.common.designsystem.theme.Primary
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiSliderThumb() {
  Box(
    modifier = Modifier
      .height(26.dp)
      .width(28.dp),
    contentAlignment = Alignment.Center,
  ) {
    Spacer(
      modifier = Modifier
        .size(16.dp)
        .clip(CircleShape)
        .background(Primary),
    )
  }
}

@Preview
@Composable
fun SuwikiSliderThumbPreview() {
  SuwikiTheme {
    SuwikiSliderThumb()
  }
}
