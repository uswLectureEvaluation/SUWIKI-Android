package com.suwiki.core.designsystem.component.bottomnavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun SuwikiBottomNavigationItem(
  modifier: Modifier = Modifier,
  isChecked: Boolean,
  iconId: Int,
  onClickItem: () -> Unit = {},
) {
  val iconColor = if (isChecked) Primary else GrayDA
  Icon(
    painter = painterResource(id = iconId),
    contentDescription = "",
    tint = iconColor,
    modifier = modifier
      .suwikiClickable(onClick = onClickItem)
      .padding(vertical = 16.dp, horizontal = 48.dp),
  )
}

@Preview
@Composable
fun SuwikiBottomNavigationItemPreview() {
  var isChecked by remember { mutableStateOf(false) }

  SuwikiTheme {
    SuwikiBottomNavigationItem(
      isChecked = isChecked,
      iconId = R.drawable.ic_bottom_navigation_evaluation,
      onClickItem = { isChecked = !isChecked }
    )
  }
}
