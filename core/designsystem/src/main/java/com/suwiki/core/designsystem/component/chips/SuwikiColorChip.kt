package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.chips.ui.theme.TestTheme

@Composable
fun SuwikiColorChip() {
  var chipState by rememberSaveable { mutableStateOf(false) }

  when (chipState) {
    true -> {
      SuwikiCheckedColorChip { chipState = !chipState }
    }
    false -> {
      SuwikiNonCheckedColorChip { chipState = !chipState }
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiColorChipPreview() {
  TestTheme {
    Column(
      modifier = Modifier.size(300.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      SuwikiColorChip()
    }
  }
}
