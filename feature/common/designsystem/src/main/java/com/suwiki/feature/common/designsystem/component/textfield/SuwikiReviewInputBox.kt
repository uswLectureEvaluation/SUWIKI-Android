package com.suwiki.feature.common.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import com.suwiki.feature.common.designsystem.theme.Black
import com.suwiki.feature.common.designsystem.theme.Error
import com.suwiki.feature.common.designsystem.theme.Gray95
import com.suwiki.feature.common.designsystem.theme.GrayF6
import com.suwiki.feature.common.designsystem.theme.Primary
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiReviewInputBox(
  modifier: Modifier = Modifier,
  hint: String = "",
  value: String = "",
  onValueChange: (String) -> Unit = { _ -> },
  isError: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier.fillMaxWidth(),
    textStyle = SuwikiTheme.typography.body7.copy(Black),
    interactionSource = interactionSource,
    cursorBrush = SolidColor(Primary),
    decorationBox = { innerText ->
      Box(
        modifier = modifier
          .heightIn(min = 198.dp)
          .background(GrayF6, shape = RoundedCornerShape(10.dp))
          .border(
            width = 1.dp,
            color = if (isError) Error else Color.Unspecified,
            shape = RoundedCornerShape(10.dp),
          )
          .padding(horizontal = 16.dp, vertical = 24.dp),
        contentAlignment = Alignment.TopStart,
      ) {
        innerText()
        if (value.isEmpty()) {
          Text(
            text = hint,
            color = Gray95,
            style = SuwikiTheme.typography.body7,
          )
        }
      }
    },
  )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiReviewInputBoxPreview() {
  SuwikiTheme {
    var normalValue by remember {
      mutableStateOf("")
    }

    Column(
      modifier = Modifier.padding(20.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      SuwikiReviewInputBox(
        hint = "강의평가를 작성해주세요",
        value = normalValue,
        onValueChange = { normalValue = it },
      )

      SuwikiReviewInputBox(
        hint = "강의평가를 작성해주세요",
        value = normalValue,
        isError = true,
        onValueChange = { normalValue = it },
      )
    }
  }
}
