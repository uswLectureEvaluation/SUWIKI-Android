package com.suwiki.feature.common.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.feature.common.designsystem.theme.Primary
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White

@Composable
fun SuwikiOutlinedButton(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(15.dp),
  text: String,
  onClick: () -> Unit = {},
) {
  BasicButton(
    modifier = modifier
      .fillMaxWidth(),
    text = text,
    shape = shape,
    onClick = onClick,
    backgroundColor = White,
    textColor = Primary,
    borderColor = Primary,
    borderWidth = 1.dp,
    textStyle = SuwikiTheme.typography.header5,
    padding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
  )
}

@Preview
@Composable
fun SuwikiOutlinedButtonPreview() {
  SuwikiTheme {
    SuwikiOutlinedButton(text = "버튼")
  }
}
