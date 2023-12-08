package com.suwiki.core.designsystem.component.align

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiAlignContainer(
  text: String,
  isChecked: Boolean = false,
  onClick: () -> Unit = {},
) {
  val textColor = if (isChecked) {
    Color(0xFF346CFD)
  } else {
    Color(0xFF6A6A6A)
  }
  Box(
    modifier = Modifier
      .background(Color(0xFFFFFFFF))
      .size(width = 360.dp, height = 50.dp)
      .suwikiClickable(
        onClick = onClick,
        rippleColor = Color(0xFF6a6a6a),
      ),
  ) {
    Row(
      horizontalArrangement = Arrangement.Start,
      modifier = Modifier.padding(start = 24.dp, end = 16.dp, top = 13.dp, bottom = 14.dp),
    ) {
      Text(
        text = text,
        color = textColor,
        modifier = Modifier
          .size(width = 284.dp, height = 23.dp),
      )
      Spacer(modifier = Modifier.width(12.dp))
      if (isChecked) {
        Icon(
          painter = painterResource(id = R.drawable.ic_align_checked),
          contentDescription = "",
          modifier = Modifier.size(24.dp),
          tint = Color(0xFF346CFD),
        )
      }
    }
  }
}

@Preview(widthDp = 400, heightDp = 200)
@Composable
fun SuwikiAlignContainerPreview() {
  var isChecked by remember { mutableStateOf(false) }

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
