package com.suwiki.presentation.common.designsystem.shadow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suwiki.presentation.common.designsystem.theme.GrayCB
import com.suwiki.presentation.common.designsystem.theme.White

fun Modifier.cardShadow(
  modifier: Modifier = Modifier,
  borderRadius: Dp = 0.dp,
) = this.then(
  modifier.suwikiShadow(
    color = GrayCB,
    spread = 0.1.dp,
    borderRadius = borderRadius,
    blurRadius = 12.dp,
    offsetY = 8.dp,
    offsetX = 8.dp,
  ),
)

@Preview(showBackground = true)
@Composable
fun CardShadowPreview() {
  Box(
    modifier = Modifier.padding(40.dp),
    contentAlignment = Alignment.Center,
  ) {
    Box(
      modifier = Modifier
        .size(160.dp)
        .cardShadow(borderRadius = 5.dp)
        .clip(RoundedCornerShape(5.dp))
        .background(White),
    ) {
    }
  }
}
