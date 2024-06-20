package com.suwiki.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.common.ui.extension.suwikiClickable

@Composable
fun SuwikiDialog(
  modifier: Modifier = Modifier,
  headerText: String,
  bodyText: String,
  confirmButtonText: String,
  dismissButtonText: String? = null,
  onDismissRequest: () -> Unit,
  onClickConfirm: () -> Unit,
  onClickDismiss: () -> Unit = {},
) {
  Dialog(
    onDismissRequest = onDismissRequest,
    content = {
      Column(
        modifier = modifier
          .clip(RoundedCornerShape(10.dp))
          .background(White)
          .padding(top = 20.dp, bottom = 15.dp, start = 15.dp, end = 22.dp),
      ) {
        Text(
          text = headerText,
          style = SuwikiTheme.typography.header5,
          color = Black,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = bodyText,
          style = SuwikiTheme.typography.body5,
          color = Black,
        )
        Spacer(modifier = Modifier.height(37.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End,
        ) {
          if (dismissButtonText != null) {
            Text(
              modifier = Modifier.suwikiClickable(rippleEnabled = false, onClick = onClickDismiss),
              text = dismissButtonText,
              style = SuwikiTheme.typography.body4,
              color = Gray95,
            )
          }

          Spacer(modifier = Modifier.width(30.dp))

          Text(
            modifier = Modifier.suwikiClickable(rippleEnabled = false, onClick = onClickConfirm),
            text = confirmButtonText,
            style = SuwikiTheme.typography.body4,
            color = Primary,
          )
        }
      }
    },
  )
}

@Preview
@Composable
fun DialogPreview() {
  SuwikiTheme {
    SuwikiDialog(
      headerText = "Header text",
      bodyText = "Body text",
      confirmButtonText = "Action 2",
      dismissButtonText = "Action 1",
      onDismissRequest = { /*TODO*/ },
      onClickConfirm = { /*TODO*/ },
    ) {
    }
  }
}
