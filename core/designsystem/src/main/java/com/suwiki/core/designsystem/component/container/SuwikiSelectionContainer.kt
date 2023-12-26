package com.suwiki.core.designsystem.component.container

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiSelectionContainer(
  modifier: Modifier = Modifier,
  isChecked: Boolean,
  text: String,
  onClickCheckIcon: () -> Unit = {},
  onClickArrowIcon: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(24.dp, 18.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      modifier = Modifier.suwikiClickable(rippleEnabled = false, onClick = onClickCheckIcon),
      painter = painterResource(id = R.drawable.ic_check_filled),
      tint = if (isChecked) Primary else GrayDA,
      contentDescription = "",
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = text,
      style = SuwikiTheme.typography.body2,
      color = Black,
    )
    Image(
      modifier = Modifier
        .clip(CircleShape)
        .suwikiClickable(onClick = onClickArrowIcon),
      painter = painterResource(id = R.drawable.ic_arrow_gray_right),
      contentDescription = "",
    )
  }
}

@Preview
@Composable
fun SuwikiSelectionContainerPreview() {
  SuwikiTheme {
    Column {
      SuwikiSelectionContainer(
        isChecked = true,
        text = "약관",
      )
      SuwikiSelectionContainer(
        isChecked = false,
        text = "약관",
      )
    }
  }
}
