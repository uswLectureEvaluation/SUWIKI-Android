package com.suwiki.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SuwikiTextFieldReview(
  modifier: Modifier = Modifier,
  hint: String = "",
  value: String = "",
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  isError: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier.fillMaxWidth(),
    interactionSource = interactionSource,
    cursorBrush = SolidColor(Color.Blue),
    decorationBox = { innerText ->
      Column(
        modifier = Modifier
          .heightIn(min = 198.dp)
          .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
          .border(
            width = 1.dp,
            color = if (isError) Color.Red else Color.Unspecified,
            shape = RoundedCornerShape(10.dp),
          )
          .padding(horizontal = 16.dp, vertical = 24.dp),
      ) {
        Spacer(modifier = Modifier.size(2.dp))

        Box(
          modifier = Modifier.wrapContentSize(),
          contentAlignment = Alignment.CenterStart,
        ) {
          innerText()
          if (value.isEmpty()) Text(text = hint, color = Color.Gray)
        }
      }
    },
  )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiTextFieldReviewPreview() {
  var normalValue by remember {
    mutableStateOf("")
  }

  Column(
    modifier = Modifier.padding(20.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    SuwikiTextFieldReview(
      hint = "강의평가를 작성해주세요",
      value = normalValue,
      onValueChange = { normalValue = it },
      onClickClearButton = { normalValue = "" },
    )

    SuwikiTextFieldReview(
      hint = "강의평가를 작성해주세요",
      value = normalValue,
      isError = true,
      onValueChange = { normalValue = it },
      onClickClearButton = { normalValue = "" },
    )
  }
}
