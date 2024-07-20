package com.suwiki.presentation.lectureevaluation.lectureevaluation.component.slider.material

import androidx.annotation.IntRange
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * material3에서 제공하는 Slider와 거의 동일합니다.
 * 주요 차이점은 trackOffset이 존재합니다.
 */
@Composable
@ExperimentalMaterial3Api
fun Slider(
  value: Float,
  onValueChange: (Float) -> Unit,
  modifier: Modifier = Modifier,
  trackOffset: Float,
  enabled: Boolean = true,
  onValueChangeFinished: (() -> Unit)? = null,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  @IntRange(from = 0)
  steps: Int = 0,
  thumb: @Composable (SliderState) -> Unit = { _ -> },
  track: @Composable (SliderState) -> Unit = { _ -> },
  valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
) {
  val state = remember(
    steps,
    valueRange,
  ) {
    SliderState(
      value,
      onValueChange,
      steps,
      valueRange,
      onValueChangeFinished,
    )
  }

  state.value = value
  state.onValueChange = onValueChange
  state.onValueChangeFinished = onValueChangeFinished

  Slider(
    state = state,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    thumb = thumb,
    track = track,
    trackOffset = trackOffset,
  )
}

@Composable
@ExperimentalMaterial3Api
fun Slider(
  state: SliderState,
  modifier: Modifier = Modifier,
  trackOffset: Float,
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  thumb: @Composable (SliderState) -> Unit = {
  },
  track: @Composable (SliderState) -> Unit = { sliderState ->
  },
) {
  require(state.steps >= 0) { "steps should be >= 0" }

  SliderImpl(
    state = state,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    thumb = thumb,
    track = track,
    trackOffset = trackOffset,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SliderImpl(
  modifier: Modifier,
  state: SliderState,
  enabled: Boolean,
  interactionSource: MutableInteractionSource,
  trackOffset: Float,
  thumb: @Composable (SliderState) -> Unit,
  track: @Composable (SliderState) -> Unit,
) {
  val press = Modifier.sliderTapModifier(
    state,
    interactionSource,
    enabled,
  )
  val drag = Modifier.draggable(
    orientation = Orientation.Horizontal,
    enabled = enabled,
    interactionSource = interactionSource,
    onDragStopped = { state.gestureEndAction() },
    startDragImmediately = state.isDragging,
    state = state,
  )

  Layout(
    {
      Box(modifier = Modifier.layoutId(SliderComponents.THUMB)) {
        thumb(state)
      }
      Box(modifier = Modifier.layoutId(SliderComponents.TRACK)) {
        track(state)
      }
    },
    modifier = modifier
      .minimumInteractiveComponentSize()
      .requiredSizeIn(
        minWidth = 20.dp,
        minHeight = 20.dp,
      )
      .sliderSemantics(
        state,
        enabled,
      )
      .focusable(enabled, interactionSource)
      .then(press)
      .then(drag),
  ) { measurables, constraints ->

    val thumbPlaceable = measurables.find {
      it.layoutId == SliderComponents.THUMB
    }!!.measure(constraints)

    val trackPlaceable = measurables.find {
      it.layoutId == SliderComponents.TRACK
    }!!.measure(
      constraints.offset(
        horizontal = -thumbPlaceable.width,
      ).copy(minHeight = 0),
    )

    val sliderWidth = thumbPlaceable.width + trackPlaceable.width
    val sliderHeight = max(trackPlaceable.height, thumbPlaceable.height)

    state.updateDimensions(
      thumbPlaceable.width.toFloat(),
      sliderWidth,
    )

    val trackOffsetX = thumbPlaceable.width / 2
    val thumbOffsetX = ((trackPlaceable.width) * state.coercedValueAsFraction).roundToInt()
    val trackOffsetY = (sliderHeight - trackPlaceable.height) / 2 + trackOffset.toInt()
    val thumbOffsetY = (sliderHeight - thumbPlaceable.height) / 2

    layout(sliderWidth, sliderHeight) {
      trackPlaceable.placeRelative(
        trackOffsetX,
        trackOffsetY,
      )
      thumbPlaceable.placeRelative(
        thumbOffsetX,
        thumbOffsetY,
      )
    }
  }
}

private enum class SliderComponents {
  THUMB,
  TRACK,
}

@OptIn(ExperimentalMaterial3Api::class)
private fun Modifier.sliderTapModifier(
  state: SliderState,
  interactionSource: MutableInteractionSource,
  enabled: Boolean,
) = if (enabled) {
  pointerInput(state, interactionSource) {
    detectTapGestures(
      onPress = { with(state) { onPress(it) } },
      onTap = {
        state.dispatchRawDelta(0f)
        state.gestureEndAction()
      },
    )
  }
} else {
  this
}

@OptIn(ExperimentalMaterial3Api::class)
private fun Modifier.sliderSemantics(
  state: SliderState,
  enabled: Boolean,
): Modifier {
  return semantics {
    if (!enabled) disabled()
    setProgress(
      action = { targetValue ->
        var newValue = targetValue.coerceIn(
          state.valueRange.start,
          state.valueRange.endInclusive,
        )
        val originalVal = newValue
        val resolvedValue = if (state.steps > 0) {
          var distance: Float = newValue
          for (i in 0..state.steps + 1) {
            val stepValue = lerp(
              state.valueRange.start,
              state.valueRange.endInclusive,
              i.toFloat() / (state.steps + 1),
            )
            if (abs(stepValue - originalVal) <= distance) {
              distance = abs(stepValue - originalVal)
              newValue = stepValue
            }
          }
          newValue
        } else {
          newValue
        }

        // This is to keep it consistent with AbsSeekbar.java: return false if no
        // change from current.
        if (resolvedValue == state.value) {
          false
        } else {
          state.onValueChange(resolvedValue)
          state.onValueChangeFinished?.invoke()
          true
        }
      },
    )
  }.progressSemantics(
    state.value,
    state.valueRange.start..state.valueRange.endInclusive,
    state.steps,
  )
}
