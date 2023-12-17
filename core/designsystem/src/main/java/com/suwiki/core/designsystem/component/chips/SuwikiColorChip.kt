package com.suwiki.core.designsystem.component.chips

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiColorChip(
  modifier: Modifier = Modifier,
  isChecked: Boolean,
  onClick: () -> Unit = {},
) {
  Box(
    modifier = modifier.size(40.dp),
  ) {
    Image(
      painter = painterResource(id = if (isChecked) R.drawable.ic_color_checked_chip else R.drawable.ic_color_chip),
      contentDescription = "",
      modifier = Modifier.suwikiClickable(onClick = onClick),
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiColorChipPreview() {
  var isChecked by rememberSaveable { mutableStateOf(false) }

  SuwikiColorChip(
    isChecked = isChecked,
    onClick = { isChecked = !isChecked },
  )
}
