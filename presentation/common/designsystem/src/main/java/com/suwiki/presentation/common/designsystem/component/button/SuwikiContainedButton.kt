package com.suwiki.presentation.common.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.presentation.common.designsystem.theme.Black
import com.suwiki.presentation.common.designsystem.theme.Gray95
import com.suwiki.presentation.common.designsystem.theme.GrayF6
import com.suwiki.presentation.common.designsystem.theme.Primary
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.common.designsystem.theme.White

@Composable
fun SuwikiContainedLargeButton(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(15.dp),
  text: String,
  enabled: Boolean = true,
  clickable: Boolean = true,
  onClick: () -> Unit = {},
) {
  BasicButton(
    modifier = modifier
      .fillMaxWidth(),
    text = text,
    shape = shape,
    onClick = onClick,
    backgroundColor = if (enabled) Primary else GrayF6,
    rippleColor = Black,
    textColor = if (enabled) White else Gray95,
    textStyle = SuwikiTheme.typography.header5,
    clickable = clickable,
    padding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
  )
}

@Composable
fun SuwikiContainedMediumButton(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(10.dp),
  text: String,
  enabled: Boolean = true,
  clickable: Boolean = true,
  onClick: () -> Unit = {},
) {
  BasicButton(
    modifier = modifier,
    text = text,
    shape = shape,
    onClick = onClick,
    backgroundColor = if (enabled) Primary else GrayF6,
    rippleColor = Black,
    textColor = if (enabled) White else Gray95,
    textStyle = SuwikiTheme.typography.body2,
    clickable = clickable,
    padding = PaddingValues(horizontal = 16.dp, vertical = 10.5.dp),
  )
}

@Composable
fun SuwikiContainedSmallButton(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(5.dp),
  text: String,
  onClick: () -> Unit = {},
) {
  BasicButton(
    modifier = modifier,
    text = text,
    shape = shape,
    onClick = onClick,
    backgroundColor = GrayF6,
    rippleColor = Black,
    textColor = Gray95,
    textStyle = SuwikiTheme.typography.caption2,
    padding = PaddingValues(horizontal = 6.dp, vertical = 2.5.dp),
  )
}

@Preview
@Composable
fun SuwikiContainedButtonPreview() {
  SuwikiTheme {
    Column(
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      SuwikiContainedLargeButton(enabled = true, text = "버튼")

      SuwikiContainedLargeButton(enabled = false, text = "버튼")

      SuwikiContainedMediumButton(enabled = true, text = "버튼")

      SuwikiContainedMediumButton(enabled = false, text = "버튼")

      SuwikiContainedSmallButton(text = "버튼")
    }
  }
}
