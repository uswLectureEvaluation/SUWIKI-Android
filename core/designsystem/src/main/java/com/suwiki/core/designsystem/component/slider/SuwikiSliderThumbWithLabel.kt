package com.suwiki.core.designsystem.component.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R

const val SUWIKI_THUMB_WIDTH_LABEL_HEIGHT = 88

@Composable
fun SuwikiSliderThumbWithLabel(
  modifier: Modifier = Modifier,
  label: String,
) {
  Box(
    modifier = modifier.height(SUWIKI_THUMB_WIDTH_LABEL_HEIGHT.dp),
  ) {
    Image(
      painter = painterResource(id = R.drawable.ic_slider_thumb_hovered),
      contentDescription = "",
    )

    Box(
      modifier = Modifier.size(28.dp),
      contentAlignment = Alignment.Center,
    ) {
      Text(
        text = label,
        textAlign = TextAlign.Center,
        color = Color.White,
      )
    }
  }
}

@Preview
@Composable
fun SuwikiSliderThumbWithLabelPreview() {
  SuwikiSliderThumbWithLabel(label = "5.0")
}
