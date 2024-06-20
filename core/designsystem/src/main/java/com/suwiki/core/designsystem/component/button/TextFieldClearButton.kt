package com.suwiki.core.designsystem.component.button

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.ui.extension.suwikiClickable

@Composable
fun TextFieldClearButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  Icon(
    modifier = modifier
      .clip(CircleShape)
      .suwikiClickable(onClick = onClick),
    painter = painterResource(id = R.drawable.ic_textfield_clear),
    tint = Gray95,
    contentDescription = "",
  )
}

@Preview
@Composable
fun TextFieldClearButtonPreview() {
  SuwikiTheme {
    TextFieldClearButton(onClick = {})
  }
}
