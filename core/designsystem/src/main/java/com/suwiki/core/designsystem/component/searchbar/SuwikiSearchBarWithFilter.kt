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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R

@Composable
fun SuwikiSearchBarWithFilter(
  modifier: Modifier = Modifier,
  hint: String = "",
  value: String = "",
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  onClickFilterButton: () -> Unit = {},
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  Row(
    Modifier
      .background(Color.LightGray)
      .padding(vertical = 10.dp, horizontal = 24.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      modifier = modifier
        .weight(1f)
        .shadow(elevation = 10.dp) // TODO Custom Shadow로 변경해야함
        .background(Color.White, shape = RoundedCornerShape(10.dp))
        .padding(8.dp),
      interactionSource = interactionSource,
      cursorBrush = SolidColor(Color.Blue),
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
            if (value.isEmpty()) Text(text = hint, color = Color.Gray)
          }

          if (value.isNotEmpty()) {
            Image(
              modifier = Modifier
                .size(21.dp)
                .clickable(onClick = onClickClearButton),
              painter = painterResource(id = R.drawable.ic_textfield_clear),
              contentDescription = "",
            )
          }
        }
      },
    )

    Spacer(modifier = Modifier.size(4.dp))

    Image(
      modifier = Modifier
        .shadow(elevation = 10.dp) // TODO Custom Shadow로 변경해야함
        .background(Color.White, shape = RoundedCornerShape(10.dp))
        .clickable(onClick = onClickFilterButton)
        .padding(8.dp),
      painter = painterResource(id = R.drawable.ic_filter),
      contentDescription = "",
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SuwikiSearchBarWithFilterPreview() {
  var normalValue by remember {
    mutableStateOf("")
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    SuwikiSearchBarWithFilter(
      hint = "Hinted search text",
      value = normalValue,
      onValueChange = { normalValue = it },
      onClickClearButton = { normalValue = "" },
    )
  }
}
