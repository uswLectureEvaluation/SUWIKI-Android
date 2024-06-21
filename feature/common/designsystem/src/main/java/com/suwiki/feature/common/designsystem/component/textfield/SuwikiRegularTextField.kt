package com.suwiki.feature.common.designsystem.component.textfield

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.feature.common.designsystem.R
import com.suwiki.feature.common.designsystem.component.button.TextFieldClearButton
import com.suwiki.feature.common.designsystem.theme.Black
import com.suwiki.feature.common.designsystem.theme.Error
import com.suwiki.feature.common.designsystem.theme.Gray95
import com.suwiki.feature.common.designsystem.theme.GrayCB
import com.suwiki.feature.common.designsystem.theme.GrayF6
import com.suwiki.feature.common.designsystem.theme.Primary
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White
import com.suwiki.feature.common.ui.extension.suwikiClickable

@Composable
fun SuwikiRegularTextField(
  modifier: Modifier = Modifier,
  label: String? = null,
  placeholder: String = "",
  value: String = "",
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  helperText: String? = null,
  isError: Boolean = false,
  maxLines: Int = 1,
  minLines: Int = 1,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  showEyeIcon: Boolean = false,
  onClickEyeIcon: () -> Unit = {},
  showValue: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val isFocused by interactionSource.collectIsFocusedAsState()

  val (labelColor, helperTextColor) = when {
    isError -> (Error to Error)
    isFocused -> (Primary to Primary)
    else -> (Gray95 to Gray95)
  }

  val underlineColor = when {
    isError -> Error
    isFocused -> Primary
    value.isEmpty() -> Gray95
    else -> GrayF6
  }

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier.fillMaxWidth(),
    singleLine = maxLines == 1,
    maxLines = if (minLines > maxLines) minLines else maxLines,
    minLines = minLines,
    textStyle = SuwikiTheme.typography.header4.copy(color = Black),
    interactionSource = interactionSource,
    cursorBrush = SolidColor(Primary),
    visualTransformation = if (showValue) VisualTransformation.None else PasswordVisualTransformation(),
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    decorationBox = { innerText ->
      Column {
        if (label != null) {
          Text(
            text = label,
            color = labelColor,
            style = SuwikiTheme.typography.caption2,
          )
        }

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
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
          ) {
            innerText()
            if (value.isEmpty()) {
              Text(
                text = placeholder,
                color = GrayCB,
                style = SuwikiTheme.typography.header4,
              )
            }
          }

          if (value.isNotEmpty()) {
            Row(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
              if (showEyeIcon) {
                Icon(
                  modifier = modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .suwikiClickable(onClick = onClickEyeIcon),
                  painter = painterResource(id = if (showValue) R.drawable.ic_eye_off else R.drawable.ic_eye_on),
                  tint = Gray95,
                  contentDescription = "",
                )
              }

              TextFieldClearButton(
                onClick = onClickClearButton,
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(if (isFocused || isError) 4.dp else 5.dp))

        HorizontalDivider(
          thickness = if (isFocused || isError) 2.dp else 1.dp,
          color = underlineColor,
        )

        Spacer(modifier = Modifier.height(3.dp))

        if (helperText != null) {
          Text(
            text = helperText,
            color = helperTextColor,
            style = SuwikiTheme.typography.caption2,
          )
        }
      }
    },
  )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiRegularTextFieldPreview() {
  SuwikiTheme {
    var normalValue by remember {
      mutableStateOf("")
    }

    var errorValue by remember {
      mutableStateOf("")
    }

    Column(
      modifier = Modifier.background(White),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      SuwikiRegularTextField(
        label = "라벨",
        placeholder = "플레이스 홀더",
        value = normalValue,
        onValueChange = { normalValue = it },
        onClickClearButton = { normalValue = "" },
        helperText = "도움말 메세지",
      )

      SuwikiRegularTextField(
        label = "라벨",
        placeholder = "플레이스 홀더",
        value = errorValue,
        onValueChange = { errorValue = it },
        onClickClearButton = { errorValue = "" },
        helperText = "도움말 메세지",
        isError = true,
      )
    }
  }
}
