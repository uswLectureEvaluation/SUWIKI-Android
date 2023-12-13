package com.suwiki.core.designsystem.component.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Blue5
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiFilterContainer(
  text: String,
  isChecked: Boolean,
  isStared: Boolean,
  onClick: () -> Unit = {},
  onClickStar: () -> Unit = {},
) {
  val (textColor, backgroundColor) = if (isChecked) {
    Primary to Blue5
  } else {
    Black to White
  }

  Box(
    modifier = Modifier
      .background(backgroundColor)
      .fillMaxWidth()
      .wrapContentHeight()
      .suwikiClickable(onClick = onClick),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Start,
      modifier = Modifier
        .padding(vertical = 12.dp, horizontal = 24.dp),
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_filter_stared),
        contentDescription = "",
        modifier = Modifier
          .size(24.dp)
          .suwikiClickable(
            onClick = onClickStar,
            rippleEnabled = false,
          ),
        tint = if (isStared) Primary else GrayDA,
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = text,
        style = SuwikiTheme.typography.body2,
        color = textColor,
      )
    }
  }
}

@Preview(widthDp = 300, heightDp = 50)
@Composable
fun SuwikiFilterContainerPreview() {
  var isChecked by remember { mutableStateOf(false) }
  var isStared by remember { mutableStateOf(false) }

  SuwikiTheme {
    SuwikiFilterContainer(
      text = "개설학과명",
      isChecked = isChecked,
      isStared = isStared,
      onClick = { isChecked = !isChecked },
      onClickStar = { isStared = !isStared },
    )
  }
}
