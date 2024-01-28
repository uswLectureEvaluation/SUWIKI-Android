package com.suwiki.feature.lectureevaluation.editor.lectureevaluation.component.slider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
@ExperimentalMaterial3Api
fun SuwikiSliderTrack(
  modifier: Modifier = Modifier,
  value: Float,
  valueRange: ClosedFloatingPointRange<Float>,
  height: Dp = 6.dp,
  shape: Shape = RoundedCornerShape(4.dp),
) {
  SuwikiTheme {
    Box(
      modifier = modifier
        .track(height = height, shape = shape)
        .background(GrayF6),
    ) {
      Box(
        modifier = modifier
          .progress(
            value = value,
            valueRange = valueRange,
            height = height,
            shape = shape,
          )
          .background(Primary),
      )
    }
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
