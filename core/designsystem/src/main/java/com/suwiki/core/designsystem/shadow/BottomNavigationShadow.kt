package com.suwiki.core.designsystem.shadow

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
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.White

fun Modifier.bottomNavigationShadow(
  modifier: Modifier = Modifier,
  borderRadius: Dp = 0.dp,
) = this.then(
  modifier.suwikiShadow(
    color = GrayDA,
    spread = 0.1.dp,
    borderRadius = borderRadius,
    blurRadius = 8.dp,
    offsetY = (-4).dp,
  ),
)

@Preview(showBackground = true)
@Composable
fun BottomNavigationShadowPreview() {
  Box(
    modifier = Modifier.padding(40.dp),
    contentAlignment = Alignment.Center,
  ) {
    Box(
      modifier = Modifier
        .size(160.dp)
        .bottomNavigationShadow(borderRadius = 5.dp)
        .clip(RoundedCornerShape(5.dp))
        .background(White),
    ) {
    }
  }
}
