package com.suwiki.core.designsystem.component.align

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Gray6A
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiAlignButton(
  onClick: () -> Unit = {},
) {
  Icon(
    painter = painterResource(id = R.drawable.ic_filter),
    contentDescription = "",
    modifier = Modifier
      .size(40.dp)
      .clip(RoundedCornerShape(10.dp))
      .suwikiClickable(onClick = onClick)
      .background(White)
      .padding(8.dp),
    tint = Gray6A,
  )
}

@Preview(widthDp = 400, heightDp = 400)
@Composable
fun SuwikiAlignButtonPreview() {
  Column(
    verticalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    SuwikiAlignButton()
  }
}
