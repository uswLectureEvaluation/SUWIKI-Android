package com.suwiki.core.designsystem.component.align

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiAlignContainer(
  text: String,
  isChecked: Boolean = false,
  onClick: () -> Unit = {},
) {
  val textColor = if (isChecked) Primary else Gray6A
  Box(
    modifier = Modifier
      .background(White)
      .fillMaxWidth()
      .height(50.dp)
      .suwikiClickable(
        onClick = onClick,
        rippleColor = Gray6A,
      ),
  ) {
    Text(
      text = text,
      color = textColor,
      style = SuwikiTheme.typography.body2,
      modifier = Modifier
        .align(Alignment.CenterStart)
        .padding(start = 24.dp, end = 52.dp)
        .fillMaxWidth()
        .wrapContentHeight(),
    )
    if (isChecked) {
      Icon(
        painter = painterResource(id = R.drawable.ic_align_checked),
        contentDescription = "",
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .padding(end = 16.dp)
          .size(24.dp),
        tint = Primary,
      )
    }
  }
}

@Preview(widthDp = 300, heightDp = 200)
@Composable
fun SuwikiAlignContainerPreview() {
  var isChecked by remember { mutableStateOf(false) }

  SuwikiTheme {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      SuwikiAlignContainer(
        text = "메뉴",
        isChecked = isChecked,
        onClick = { isChecked = !isChecked },
      )
    }
  }
}
