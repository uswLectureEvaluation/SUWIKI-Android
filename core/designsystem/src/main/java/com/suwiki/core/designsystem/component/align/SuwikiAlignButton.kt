package com.suwiki.core.designsystem.component.align

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiAlignButton(
  onClick: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .size(40.dp)
      .clip(RoundedCornerShape(10.dp))
      .suwikiClickable(onClick = onClick)
      .background(Color(0xFFFFFFFF)),
  ) {
    Icon(
      painter = painterResource(id = R.drawable.ic_filter),
      contentDescription = "",
      modifier = Modifier.align(Alignment.Center),
      tint = Color(0xFF6A6A6A),
    )
  }
}

@Preview(showSystemUi = true)
@Composable
fun SuwikiAlignButtonPreview() {
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    SuwikiAlignButton()
  }
}
