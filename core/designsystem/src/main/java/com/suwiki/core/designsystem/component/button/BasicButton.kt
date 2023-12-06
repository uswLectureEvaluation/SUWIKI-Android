package com.suwiki.core.designsystem.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.painter.Painter
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
    content = {
      Text(
        text = text,
        color = contentColor,
      )
    },
  )
}

@Stable
private val BasicContainedMiddleButtonHeight = 40.dp

@Composable
fun BasicContainedMiddleButton(
  text: String,
  modifier: Modifier = Modifier,
  shape: Shape = RectangleShape,
  enabled: Boolean = true,
  onClick: () -> Unit,
  enabledBackGroundColor: Color,
  textColor: Color,
) {
  val contentColor = textColor

  BasicButton(
    modifier = modifier
      .wrapContentWidth()
      .height(BasicContainedMiddleButtonHeight),
    shape = shape,
    enabled = enabled,
    onClick = onClick,
    enabledBackGroundColor = enabledBackGroundColor,
    pressedBackgroundColor = enabledBackGroundColor,
    disabledBackgroundColor = enabledBackGroundColor,
    content = {
      Text(
        text = text,
        color = contentColor,
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 9.dp),
      )
    },
  )
}

@Composable
fun BasicContainedMiddleIconButton(
  text: String,
  modifier: Modifier = Modifier,
  contentDescription: String?,
  shape: Shape = RectangleShape,
  enabled: Boolean = true,
  onClick: () -> Unit,
  enabledBackGroundColor: Color,
  textColor: Color,
  icon: Painter, // 이 부분에 이미지를 나타내는 Painter를 전달합니다.
) {
  val contentColor = textColor

  BasicButton(
    modifier = modifier
      .wrapContentWidth()
      .height(BasicContainedMiddleButtonHeight),
    shape = shape,
    enabled = enabled,
    onClick = onClick,
    enabledBackGroundColor = enabledBackGroundColor,
    pressedBackgroundColor = enabledBackGroundColor,
    disabledBackgroundColor = enabledBackGroundColor,
    content = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp , horizontal = 16.dp)
      ) {
        Image(
          painter = icon,
          contentDescription = contentDescription,
        )
        Text(
          text = text,
          color = contentColor,
        )
      }
    },
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
    content = {
      Text(
        text = text,
        color = textColor,
      )
    },
  )
}
