package com.suwiki.core.designsystem.component.slider

import androidx.annotation.IntRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.slider.material.Slider
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuwikiSlider(
  modifier: Modifier = Modifier,
  value: Float = 2.5f,
  onValueChange: (Float) -> Unit = { _ -> },
  @IntRange(from = 0)
  steps: Int = 9,
  valueRange: ClosedFloatingPointRange<Float> = 0f..5f,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  var isHovering by rememberSaveable {
    mutableStateOf(false)
  }

  val label = (round(value * 100) / 100).toString()

  Row(
    modifier = modifier
      .fillMaxWidth(),
    verticalAlignment = Alignment.Bottom,
  ) {
    Slider(
      modifier = Modifier.weight(1f),
      value = value,
      onValueChange = {
        isHovering = true
        onValueChange(it)
      },
      onValueChangeFinished = { isHovering = false },
      valueRange = valueRange,
      steps = steps,
      interactionSource = interactionSource,
      trackOffset = with(LocalDensity.current) { 16.dp.toPx() },
      thumb = {
        Box(
          modifier = Modifier.height(57.dp),
          contentAlignment = Alignment.BottomCenter,
        ) {
          if (isHovering) {
            SuwikiSliderThumbWithLabel(
              label = label,
            )
          } else {
            SuwikiSliderThumb()
          }
        }
      },
      track = {
        SuwikiSliderTrack(
          value = value,
          valueRange = valueRange,
          height = 6.dp,
          shape = RoundedCornerShape(4.dp),
        )
      },
    )

    Column {
      Text(
        text = label,
        style = SuwikiTheme.typography.header6,
        color = Primary,
      )
      Spacer(modifier = Modifier.size(3.dp))
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiSliderPreview() {
  SuwikiTheme {
    var sliderPosition by rememberSaveable {
      mutableFloatStateOf(5f)
    }

    SuwikiSlider(
      value = sliderPosition,
      onValueChange = { sliderPosition = if (it < 0.5) 0.5F else it },
    )
  }
}
