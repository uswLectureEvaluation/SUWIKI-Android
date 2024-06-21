package com.suwiki.presentation.lectureevaluation.editor.lectureevaluation.component.slider

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.presentation.common.designsystem.R
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiSliderThumbWithLabel(
  modifier: Modifier = Modifier,
  label: String,
) {
  Box(
    modifier = modifier.wrapContentSize(),
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
        style = SuwikiTheme.typography.caption1,
      )
    }
  }
}

@Preview
@Composable
fun SuwikiSliderThumbWithLabelPreview() {
  SuwikiTheme {
    SuwikiSliderThumbWithLabel(label = "5.0")
  }
}
