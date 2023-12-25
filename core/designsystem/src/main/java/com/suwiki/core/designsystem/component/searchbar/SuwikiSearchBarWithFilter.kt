package com.suwiki.core.designsystem.component.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.align.SuwikiAlignButton
import com.suwiki.core.designsystem.shadow.cardShadow
import com.suwiki.core.designsystem.theme.GrayCB
import com.suwiki.core.designsystem.theme.GrayFB
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiSearchBarWithFilter(
  modifier: Modifier = Modifier,
  placeHolder: String = "",
  value: String = "",
  maxLines: Int = 1,
  minLines: Int = 1,
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  onClickFilterButton: () -> Unit = {},
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  Row(
    modifier
      .background(GrayFB)
      .padding(vertical = 10.dp, horizontal = 24.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    BasicSearchBar(
      modifier = Modifier
        .weight(1f)
        .cardShadow()
        .background(White, shape = RoundedCornerShape(10.dp))
        .height(40.dp)
        .padding(8.dp),
      value = value,
      onValueChange = onValueChange,
      maxLines = maxLines,
      minLines = minLines,
      interactionSource = interactionSource,
      placeholder = placeHolder,
      keyboardOptions = keyboardOptions,
      keyboardActions = keyboardActions,
      placeholderColor = GrayCB,
      onClickClearButton = onClickClearButton,
    )

    Spacer(modifier = Modifier.size(4.dp))

    SuwikiAlignButton(
      onClick = onClickFilterButton,
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiSearchBarWithFilterPreview() {
  SuwikiTheme {
    var normalValue by remember {
      mutableStateOf("")
    }

    Column(
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      SuwikiSearchBarWithFilter(
        placeHolder = "Hinted search text",
        value = normalValue,
        onValueChange = { normalValue = it },
        onClickClearButton = { normalValue = "" },
      )
    }
  }
}
