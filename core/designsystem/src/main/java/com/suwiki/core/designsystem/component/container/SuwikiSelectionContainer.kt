package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.component.chips.SuwikiColorChip
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiSelectionContainer(
  modifier: Modifier = Modifier,
  isChecked: Boolean,
  text: String,
  onClick: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .suwikiClickable(
        rippleEnabled = true,
        rippleColor = Primary,
        onClick = onClick,
      ),
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp, 18.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        SuwikiColorChip(
          isChecked = isChecked,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = text,
          style = SuwikiTheme.typography.body2,
          color = Black,
        )
        Image(
          painter = painterResource(id = R.drawable.ic_arrow_gray_right),
          contentDescription = "",
        )
      }
    }
  }
}

@Composable
@Preview
fun SuwikiSelectionContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiSelectionContainer(
        isChecked = true,
        text = "약관",
        onClick = {},
      )
      SuwikiSelectionContainer(
        isChecked = false,
        text = "약관",
        onClick = {},
      )
    }
  }
}
