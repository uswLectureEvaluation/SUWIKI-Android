package com.suwiki.presentation.common.designsystem.component.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.presentation.common.designsystem.theme.Gray95
import com.suwiki.presentation.common.designsystem.theme.GrayF6
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.common.designsystem.theme.White

@Composable
fun SuwikiSearchBar(
  modifier: Modifier = Modifier,
  placeholder: String = "",
  value: String = "",
  maxLines: Int = 1,
  minLines: Int = 1,
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  onClickSearchButton: (String) -> Unit = {},
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  Box(
    modifier
      .background(White)
      .padding(horizontal = 24.dp)
      .height(40.dp),
    contentAlignment = Alignment.Center,
  ) {
    BasicSearchBar(
      modifier = Modifier
        .background(GrayF6, shape = RoundedCornerShape(10.dp))
        .padding(8.dp),
      value = value,
      onValueChange = onValueChange,
      maxLines = maxLines,
      minLines = minLines,
      interactionSource = interactionSource,
      placeholder = placeholder,
      placeholderColor = Gray95,
      keyboardOptions = keyboardOptions,
      keyboardActions = KeyboardActions(
        onDone = {
          onClickSearchButton(value)
          keyboardController?.hide()
        },
      ),
      onClickClearButton = onClickClearButton,
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiSearchBarPreview() {
  SuwikiTheme {
    var normalValue by remember {
      mutableStateOf("")
    }

    Column(
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      SuwikiSearchBar(
        placeholder = "Hinted search text",
        value = normalValue,
        onValueChange = { normalValue = it },
        onClickClearButton = { normalValue = "" },
      )
    }
  }
}
