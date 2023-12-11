package com.suwiki.core.designsystem.component.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SuwikiToastView(
  messageTxt: String,
) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Surface(
      modifier = Modifier
        .wrapContentSize()
        .background(
          color = Color(0xFF959595),
          shape = RoundedCornerShape(25.dp),
        ),
      color = Color.Transparent,
    ) {
     Text(
        text = messageTxt,
        textAlign = TextAlign.Center,
        color = Color(0XFFFFFFFF),
        modifier = Modifier.padding(16.dp, 10.dp),
      )
    }
  }
}

@Preview
@Composable
fun SetViewPreview() {
  SuwikiToastView(
    messageTxt = "text",
  )
}
