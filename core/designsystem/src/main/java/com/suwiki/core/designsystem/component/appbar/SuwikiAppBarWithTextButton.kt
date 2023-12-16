package com.suwiki.core.designsystem.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.button.SuwikiContainedButtonSmall
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiAppBarWithTextButton(
  modifier: Modifier = Modifier,
  buttonText: String = "",
  onClickBack: () -> Unit = {},
  onClickTextButton: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(White)
      .padding(vertical = 15.dp, horizontal = 18.dp),
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_appbar_arrow_left),
      contentDescription = "",
      tint = Gray95,
      modifier = Modifier
        .size(24.dp)
        .suwikiClickable(onClick = onClickBack)
        .align(Alignment.CenterStart)
        .padding(vertical = 2.dp, horizontal = 6.5.dp),
    )
    SuwikiContainedButtonSmall(
      text = buttonText,
      textColor = Primary,
      onClick = onClickTextButton,
      modifier = Modifier
        .align(Alignment.CenterEnd),
    )
  }
}

@Preview
@Composable
fun SuwikiAddClassAppBarPreview() {
  SuwikiTheme {
    SuwikiAppBarWithTextButton(
      buttonText = "text",
      onClickBack = { /*TODO*/ },
      onClickTextButton = { /*TODO*/ },
    )
  }
}
