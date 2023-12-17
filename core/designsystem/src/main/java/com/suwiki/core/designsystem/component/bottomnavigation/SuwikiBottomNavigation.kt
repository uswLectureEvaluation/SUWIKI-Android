package com.suwiki.core.designsystem.component.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiBottomNavigation(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .shadow(elevation = 4.dp)
      .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
      .background(White)
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      SuwikiBottomNavigationActiveItem(
        modifier = Modifier.weight(1f),
        iconId = R.drawable.ic_bottom_navigation_evaluation
      )
      SuwikiBottomNavigationInActiveItem(
        modifier = Modifier.weight(1f),
        iconId = R.drawable.ic_bottom_navigation_evaluation
      )
      SuwikiBottomNavigationInActiveItem(
        modifier = Modifier.weight(1f),
        iconId = R.drawable.ic_bottom_navigation_evaluation
      )
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun SuwikiBottomNavigationPreview() {
  SuwikiTheme {
    Column {
      SuwikiBottomNavigation()
    }
  }
}
