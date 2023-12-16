package com.suwiki.core.designsystem.component.searchbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.GrayF6
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiSearchBar(
  modifier: Modifier = Modifier,
  hint: String = "",
  value: String = "",
  maxLines: Int = 1,
  minLines: Int = 1,
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  Box(
    Modifier
      .background(White)
      .padding(vertical = 10.dp, horizontal = 24.dp),
    contentAlignment = Alignment.Center,
  ) {
    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = modifier
        .background(GrayF6, shape = RoundedCornerShape(10.dp))
        .padding(8.dp),
      textStyle = SuwikiTheme.typography.header7.copy(color = Black),
      singleLine = maxLines == 1,
      maxLines = if (minLines > maxLines) minLines else maxLines,
      minLines = minLines,
      interactionSource = interactionSource,
      cursorBrush = SolidColor(Primary),
      decorationBox = { innerText ->
        Row(
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "")

          Spacer(modifier = Modifier.size(5.dp))

          Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart,
          ) {
            innerText()
            if (value.isEmpty()) {
              Text(
                text = hint,
                color = Gray95,
                style = SuwikiTheme.typography.header7,
              )
            }
          }

          if (value.isNotEmpty()) {
            Image(
              modifier = Modifier
                .clickable(onClick = onClickClearButton),
              painter = painterResource(id = R.drawable.ic_textfield_clear),
              contentDescription = "",
            )
          }
        }
      },
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
        hint = "Hinted search text",
        value = normalValue,
        onValueChange = { normalValue = it },
        onClickClearButton = { normalValue = "" },
      )
    }
  }
}
