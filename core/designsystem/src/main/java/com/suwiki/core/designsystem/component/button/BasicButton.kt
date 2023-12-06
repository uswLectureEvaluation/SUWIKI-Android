package com.suwiki.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun BasicButton(
  modifier: Modifier = Modifier,
  shape: Shape = RectangleShape,
  enabled: Boolean = true,
  onClick: () -> Unit,
  enabledBackGroundColor: Color,
  pressedBackgroundColor: Color,
  disabledBackgroundColor: Color,
  interactionSource: MutableInteractionSource =
    remember { MutableInteractionSource() },
  content: @Composable (isPressed: Boolean) -> Unit,
) {
  val isPressed by interactionSource.collectIsPressedAsState()

  val btnColor =
    if (!enabled) disabledBackgroundColor else if (isPressed) pressedBackgroundColor else enabledBackGroundColor

  Box(
    modifier = modifier
      .background(
        color = btnColor,
        shape = shape,
      )
      .suwikiClickable(
        rippleEnabled = true,
        rippleColor = pressedBackgroundColor,
        onClick = onClick,
      ),
    contentAlignment = Alignment.Center,
  ) {
    content(isPressed)
  }
}


@Stable
private val BasicBigButtonHeight = 50.dp

@Composable
fun BasicContainedBigButton(
  text: String,
  modifier: Modifier = Modifier,
  shape: Shape = RectangleShape,
  enabled: Boolean = true,
  onClick: () -> Unit,
  enabledBackGroundColor: Color,
  pressedBackgroundColor: Color,
  disabledBackgroundColor: Color,
  textColor: Color,
  disabledTextColor: Color,
) {
  val contentColor = if (enabled) textColor else disabledTextColor

  BasicButton(
    modifier = modifier
      .fillMaxWidth()
      .height(BasicBigButtonHeight),
    shape = shape,
    enabled = enabled,
    onClick = onClick,
    enabledBackGroundColor = enabledBackGroundColor,
    pressedBackgroundColor = pressedBackgroundColor,
    disabledBackgroundColor = disabledBackgroundColor,
    content = {isPressed ->
      Text(
        text = text,
        color = contentColor,
      )
    }
  )
}

@Composable
fun BasicOutlineBigButton(
  text: String,
  modifier: Modifier = Modifier,
  shape: Shape = RectangleShape,
  enabled: Boolean = true,
  onClick: () -> Unit,
  enabledBackGroundColor: Color,
  pressedBackgroundColor: Color,
  textColor: Color,
) {
  BasicButton(
    modifier = modifier
      .fillMaxWidth()
      .height(BasicBigButtonHeight),
    shape = shape,
    enabled = enabled,
    onClick = onClick,
    enabledBackGroundColor = enabledBackGroundColor,
    pressedBackgroundColor = pressedBackgroundColor,
    disabledBackgroundColor = enabledBackGroundColor,
    content = {isPressed ->
      Text(
        text = text,
        color = textColor,
      )
    }
  )
}
