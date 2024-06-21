package com.suwiki.presentation.lectureevaluation.editor.lectureevaluation.component.slider.material

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.gestures.DragScope
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.GestureCancellationException
import androidx.compose.foundation.gestures.PressGestureScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.coroutineScope
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * material3에서 제공하는 SliderState와 동일합니다.
 */
@Stable
@ExperimentalMaterial3Api
class SliderState(
  initialValue: Float = 0f,
  initialOnValueChange: ((Float) -> Unit)? = null,
  val steps: Int = 0,
  val valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
  var onValueChangeFinished: (() -> Unit)? = null,
) : DraggableState {

  private var valueState by mutableFloatStateOf(initialValue)

  /**
   * [Float] that indicates the current value that the thumb
   * currently is in respect to the track.
   */
  var value: Float
    set(newVal) {
      val coercedValue = newVal.coerceIn(valueRange.start, valueRange.endInclusive)
      val snappedValue = snapValueToTick(
        coercedValue,
        tickFractions,
        valueRange.start,
        valueRange.endInclusive,
      )
      valueState = snappedValue
    }
    get() = valueState

  override suspend fun drag(
    dragPriority: MutatePriority,
    block: suspend DragScope.() -> Unit,
  ): Unit = coroutineScope {
    isDragging = true
    scrollMutex.mutateWith(dragScope, dragPriority, block)
    isDragging = false
  }

  override fun dispatchRawDelta(delta: Float) {
    val maxPx = max(totalWidth - thumbWidth / 2, 0f)
    val minPx = min(thumbWidth / 2, maxPx)
    rawOffset = (rawOffset + delta + pressOffset)
    pressOffset = 0f
    val offsetInTrack = snapValueToTick(rawOffset, tickFractions, minPx, maxPx)
    onValueChange(scaleToUserValue(minPx, maxPx, offsetInTrack))
  }

  /**
   * callback in which value should be updated
   */
  internal var onValueChange: (Float) -> Unit = {
    if (it != value) {
      initialOnValueChange?.invoke(it) ?: defaultOnValueChange(it)
    }
  }

  internal val tickFractions = stepsToTickFractions(steps)
  internal var totalWidth by mutableIntStateOf(0)
  internal var isRtl = false
  internal var thumbWidth by mutableFloatStateOf(0f)

  internal val coercedValueAsFraction
    get() = calcFraction(
      valueRange.start,
      valueRange.endInclusive,
      value.coerceIn(valueRange.start, valueRange.endInclusive),
    )

  internal var isDragging by mutableStateOf(false)
    private set

  internal fun updateDimensions(
    newThumbWidth: Float,
    newTotalWidth: Int,
  ) {
    thumbWidth = newThumbWidth
    totalWidth = newTotalWidth
  }

  internal val gestureEndAction = {
    if (!isDragging) {
      // check isDragging in case the change is still in progress (touch -> drag case)
      onValueChangeFinished?.invoke()
    }
  }

  internal suspend fun PressGestureScope.onPress(pos: Offset) {
    val to = if (isRtl) totalWidth - pos.x else pos.x
    pressOffset = to - rawOffset
    try {
      awaitRelease()
    } catch (_: GestureCancellationException) {
      pressOffset = 0f
    }
  }

  private var rawOffset by mutableFloatStateOf(scaleToOffset(0f, 0f, value))
  private var pressOffset by mutableFloatStateOf(0f)
  private val dragScope: DragScope = object : DragScope {
    override fun dragBy(pixels: Float): Unit = dispatchRawDelta(pixels)
  }

  private val scrollMutex = MutatorMutex()

  private fun defaultOnValueChange(newVal: Float) {
    value = newVal
  }

  private fun scaleToUserValue(minPx: Float, maxPx: Float, offset: Float) =
    scale(minPx, maxPx, offset, valueRange.start, valueRange.endInclusive)

  private fun scaleToOffset(minPx: Float, maxPx: Float, userValue: Float) =
    scale(valueRange.start, valueRange.endInclusive, userValue, minPx, maxPx)
}

private fun scale(a1: Float, b1: Float, x1: Float, a2: Float, b2: Float) =
  lerp(a2, b2, calcFraction(a1, b1, x1))

private fun snapValueToTick(
  current: Float,
  tickFractions: FloatArray,
  minPx: Float,
  maxPx: Float,
): Float {
  // target is a closest anchor to the `current`, if exists
  return tickFractions
    .minByOrNull { abs(lerp(minPx, maxPx, it) - current) }
    ?.run { lerp(minPx, maxPx, this) }
    ?: current
}

fun lerp(start: Float, stop: Float, amount: Float): Float {
  return start + (stop - start) * amount
}

private fun stepsToTickFractions(steps: Int): FloatArray {
  return if (steps == 0) floatArrayOf() else FloatArray(steps + 2) { it.toFloat() / (steps + 1) }
}

private fun calcFraction(a: Float, b: Float, pos: Float) =
  (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)
