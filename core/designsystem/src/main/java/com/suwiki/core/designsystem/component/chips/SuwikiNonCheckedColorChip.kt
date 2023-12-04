package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.chips.ui.theme.TestTheme

@Composable
fun SuwikiNonCheckedColorChip(
  onClicked: () -> Unit,
) {
  Box(
    modifier = Modifier.size(40.dp),
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_color_chip),
      contentDescription = "",
      modifier = Modifier.clickable { onClicked() },
    )
  }
}

@Preview
@Composable
fun SuwikiNonCheckedColorChipPreview() {
  TestTheme {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      SuwikiNonCheckedColorChip { }
    }
  }
}
