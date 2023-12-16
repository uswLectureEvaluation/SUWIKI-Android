package com.suwiki.core.designsystem.component.bottomnavigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiBottomNavigationActiveItem(
  modifier: Modifier = Modifier,
  onClickItem: () -> Unit = {},
  iconId: Int,
) {
  Icon(
    painter = painterResource(id = iconId),
    contentDescription = "",
    tint = Primary,
    modifier = modifier
      .suwikiClickable(onClick = onClickItem)
      .padding(vertical = 16.dp, horizontal = 48.dp)
  )
}

@Composable
fun SuwikiBottomNavigationInActiveItem(
  modifier: Modifier = Modifier,
  onClickItem: () -> Unit = {},
  iconId: Int,
) {
  Icon(
    painter = painterResource(id = iconId),
    contentDescription = "",
    tint = GrayDA,
    modifier = modifier
      .suwikiClickable(onClick = onClickItem)
      .padding(vertical = 16.dp, horizontal = 48.dp)
  )
}

@Preview
@Composable
fun SuwikiBottomNavigationItemPreview() {
  SuwikiTheme {
    Row {
      SuwikiBottomNavigationActiveItem(
        iconId = R.drawable.ic_bottom_navigation_evaluation
      )
      SuwikiBottomNavigationInActiveItem(
        iconId = R.drawable.ic_bottom_navigation_evaluation
      )
    }
  }
}
