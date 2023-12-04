package com.suwiki.core.designsystem.component.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalMaterial3Api
fun SuwikiSliderTrack(
  modifier: Modifier = Modifier,
  value: Float,
  valueRange: ClosedFloatingPointRange<Float>,
  height: Dp = 6.dp,
  shape: Shape = RoundedCornerShape(4.dp),
) {
  Box(
    modifier = modifier
      .track(height = height, shape = shape)
      .background(Color.LightGray),
  ) {
    Box(
      modifier = modifier
        .progress(
          value = value,
          valueRange = valueRange,
          height = height,
          shape = shape,
        )
        .background(Color.Blue),
    )
  }
}

private fun Modifier.track(
  height: Dp = 6.dp,
  shape: Shape = CircleShape,
) = fillMaxWidth()
  .heightIn(min = height)
  .clip(shape)

private fun Modifier.progress(
  value: Float,
  valueRange: ClosedFloatingPointRange<Float>,
  height: Dp = 6.dp,
  shape: Shape = CircleShape,
) =
  fillMaxWidth(fraction = value / valueRange.endInclusive - valueRange.start)
    .heightIn(min = height)
    .clip(shape)
