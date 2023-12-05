package com.suwiki.core.designsystem.component.chips

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

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
  SuwikiColorChip()
}
