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
  content: @Composable (isPressed: Boolean) -> Unit,
) {

  val interactionSource = remember { MutableInteractionSource() }

  val isPressed = interactionSource.collectIsPressedAsState()

  val btnColor =
    if (!enabled) disabledBackgroundColor else if (isPressed.value) pressedBackgroundColor else enabledBackGroundColor

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
    content(isPressed.value)
  }
}


@Stable
private val BasicBigButtonHeight = 50.dp

@Composable
fun BasicBigButton(
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
  ) {
    Text(
      text = text,
      color = contentColor,
    )
  }
}
