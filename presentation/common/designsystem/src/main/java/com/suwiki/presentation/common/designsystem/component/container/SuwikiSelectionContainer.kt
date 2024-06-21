package com.suwiki.presentation.common.designsystem.component.container

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.presentation.common.designsystem.R
import com.suwiki.presentation.common.designsystem.theme.Gray95
import com.suwiki.presentation.common.designsystem.theme.GrayF6
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiSelectionContainer(
  modifier: Modifier = Modifier,
  title: String = "",
  onClick: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .clickable(onClick = onClick)
      .background(GrayF6)
      .padding(
        horizontal = 9.dp,
        vertical = 6.dp,
      ),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = title,
      color = Gray95,
      style = SuwikiTheme.typography.body6,
    )
    Image(
      painter = painterResource(id = R.drawable.ic_dropdown_arrow_down),
      contentDescription = "",
    )
  }
}

@Preview
@Composable
fun SuwikiSelectionContainerPreview() {
  SuwikiTheme {
    SuwikiSelectionContainer(title = "title")
  }
}
