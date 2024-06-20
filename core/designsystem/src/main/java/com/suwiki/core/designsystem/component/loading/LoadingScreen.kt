package com.suwiki.core.designsystem.component.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.feature.common.ui.extension.suwikiClickable

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier.fillMaxSize().suwikiClickable(rippleEnabled = false, onClick = {}),
    contentAlignment = Alignment.Center,
  ) {
    CircularProgressIndicator(
      modifier = Modifier.size(36.dp),
      strokeWidth = 4.dp,
      color = Primary,
      trackColor = Color.Transparent,
    )
  }
}
