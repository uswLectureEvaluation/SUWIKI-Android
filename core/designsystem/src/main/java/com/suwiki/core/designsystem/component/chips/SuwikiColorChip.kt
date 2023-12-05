package com.suwiki.core.designsystem.component.chips

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SuwikiColorChip(
  isChecked: Boolean,
  onClick: () -> Unit = {},
) {
  when (isChecked) {
    true -> {
      SuwikiCheckedColorChip(onClick = onClick)
    }
    false -> {
      SuwikiNonCheckedColorChip(onClick = onClick)
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiColorChipPreview() {
  var isChecked by rememberSaveable { mutableStateOf(false) }

  SuwikiColorChip(
    isChecked = isChecked,
    onClick = { isChecked = !isChecked }
  )
}
