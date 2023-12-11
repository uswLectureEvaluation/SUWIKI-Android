package com.suwiki.core.designsystem.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.suwiki.core.designsystem.component.button.SuwikiContainedButtonSmall
import com.suwiki.core.designsystem.component.button.SuwikiContainedGreyButtonSmall

@Composable
fun basicAlertDialog(
  onDismissRequest: () -> Unit,
  properties: DialogProperties = DialogProperties(),
  content: @Composable () -> Unit,
) {
  Dialog(
    onDismissRequest = onDismissRequest,
    properties = properties,
  ) {
    content()
  }
}

@Composable
fun SuwikiDialog(
  modifier: Modifier = Modifier,
  headerText: String,
  bodyText: String,
  confirmButtonText: String,
  dismissButtonText: String,
  onDismissRequest: () -> Unit,
  onClickConfirm: () -> Unit,
  onClickDismiss: () -> Unit,
) {
  basicAlertDialog(
    onDismissRequest = onDismissRequest,
    content = {
      Surface(
        modifier = modifier
          .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
      ) {
        Column(
          modifier = Modifier
            .padding(top = 20.dp, bottom = 15.dp, start = 15.dp, end = 22.dp),
        ) {
          Text(
            text = headerText,
            color = Color(0xFF222222),
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = bodyText,
            color = Color(0xFF222222),
          )
          Spacer(modifier = Modifier.height(37.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
          ) {
            SuwikiContainedGreyButtonSmall(
              onClick = onClickDismiss,
              text = dismissButtonText,
              modifier = Modifier.padding(2.dp),
            )
            SuwikiContainedButtonSmall(
              onClick = onClickConfirm,
              text = confirmButtonText,
            )
          }
        }
      }
    },
  )
}

@Preview
@Composable
fun DialogPreview() {
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
