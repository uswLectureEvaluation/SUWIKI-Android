package com.suwiki.core.designsystem.component.slider

import androidx.annotation.IntRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuwikiSlider(
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
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Slider(
      modifier = Modifier.weight(1f).height(SUWIKI_THUMB_WIDTH_LABEL_HEIGHT.dp),
      value = value,
      onValueChange = {
        isHovering = true
        onValueChange(it)
      },
      onValueChangeFinished = { isHovering = false },
      valueRange = valueRange,
      steps = steps,
      interactionSource = interactionSource,
      thumb = {
        if (isHovering) {
          SuwikiSliderThumbWithLabel(
            label = label,
          )
        } else {
          SuwikiSliderThumb()
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

    Text(text = label)
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiSliderPreview() {
  var sliderPosition by rememberSaveable {
    mutableFloatStateOf(2.5f)
  }

  Box(modifier = Modifier.padding(vertical = 40.dp)) {
    SuwikiSlider(
      value = sliderPosition,
      onValueChange = { sliderPosition = if (it < 0.5) 0.5F else it },
    )
  }
}
