package com.suwiki.core.designsystem.component.textfield

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R

@Composable
fun SuwikiTextFieldRegular(
  modifier: Modifier = Modifier,
  label: String = "",
  hint: String = "",
  value: String = "",
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  helperText: String = "",
  isError: Boolean = false,
  maxLines: Int = 1,
  minLines: Int = 1,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val isFocused by interactionSource.collectIsFocusedAsState()

  val (labelColor, helperTextColor) = when {
    isError -> (Color.Red to Color.Red)
    isFocused -> (Color.Blue to Color.Blue)
    else -> (Color.Gray to Color.Gray)
  }

  val underlineColor = when {
    isError -> Color.Red
    isFocused -> Color.Blue
    value.isEmpty() -> Color.Gray
    else -> Color.LightGray
  }

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier.fillMaxWidth(),
    singleLine = maxLines == 1,
    maxLines = if (minLines > maxLines) minLines else maxLines,
    minLines = minLines,
    interactionSource = interactionSource,
    cursorBrush = SolidColor(Color.Blue),
    decorationBox = { innerText ->
      Column {
        Text(
          text = label,
          color = labelColor,
        )

        Spacer(modifier = Modifier.size(2.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(
              min = 27.dp,
            ),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.CenterStart,
          ) {
            innerText()
            if (value.isEmpty()) Text(text = hint, color = Color.LightGray)
          }

          if (value.isNotEmpty()) {
            Image(
              modifier = Modifier.clickable(onClick = onClickClearButton),
              painter = painterResource(id = R.drawable.ic_textfield_clear),
              contentDescription = "",
            )
          }
        }

        Spacer(modifier = Modifier.height(if (isFocused || isError) 4.dp else 5.dp))

        HorizontalDivider(
          thickness = if (isFocused || isError) 2.dp else 1.dp,
          color = underlineColor,
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
          text = helperText,
          color = helperTextColor,
        )
      }
    },
  )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiTextFieldRegularPreview() {
  var normalValue by remember {
    mutableStateOf("")
  }

  var errorValue by remember {
    mutableStateOf("")
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    SuwikiTextFieldRegular(
      label = "라벨",
      hint = "플레이스 홀더",
      value = normalValue,
      onValueChange = { normalValue = it },
      onClickClearButton = { normalValue = "" },
      helperText = "도움말 메세지",
    )

    SuwikiTextFieldRegular(
      label = "라벨",
      hint = "플레이스 홀더",
      value = errorValue,
      onValueChange = { errorValue = it },
      onClickClearButton = { errorValue = "" },
      helperText = "도움말 메세지",
      isError = true,
    )
  }
}
