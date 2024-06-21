package com.suwiki.feature.lectureevaluation.viewerreporter.component

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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.feature.common.designsystem.component.searchbar.BasicSearchBar
import com.suwiki.feature.common.designsystem.shadow.cardShadow
import com.suwiki.feature.common.designsystem.theme.Gray6A
import com.suwiki.feature.common.designsystem.theme.GrayCB
import com.suwiki.feature.common.designsystem.theme.GrayFB
import com.suwiki.feature.common.designsystem.theme.SuwikiTheme
import com.suwiki.feature.common.designsystem.theme.White
import com.suwiki.feature.common.ui.extension.suwikiClickable
import com.suwiki.feature.lectureevaluation.viewerreporter.R

@Composable
fun EvaluationSearchBar(
  modifier: Modifier = Modifier,
  placeHolder: String = "",
  value: String = "",
  maxLines: Int = 1,
  minLines: Int = 1,
  onValueChange: (String) -> Unit = { _ -> },
  onClickClearButton: () -> Unit = {},
  onClickFilterButton: () -> Unit = {},
  onClickSearchButton: (String) -> Unit = {},
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
  focusRequester: FocusRequester = remember { FocusRequester() },
  focusManager: FocusManager = LocalFocusManager.current,
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
        .focusRequester(focusRequester)
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
      keyboardActions = KeyboardActions(
        onDone = {
          onClickSearchButton(value)
          keyboardController?.hide()
          focusManager.clearFocus()
        },
      ),
      placeholderColor = GrayCB,
      onClickClearButton = onClickClearButton,
    )

    Spacer(modifier = Modifier.size(4.dp))

    SuwikiAlignButton(
      onClick = onClickFilterButton,
    )
  }
}

@Composable
fun SuwikiAlignButton(
  modifier: Modifier = Modifier,
  onClick: () -> Unit = {},
) {
  Icon(
    painter = painterResource(id = R.drawable.ic_filter),
    contentDescription = "",
    modifier = modifier
      .cardShadow()
      .clip(RoundedCornerShape(10.dp))
      .suwikiClickable(onClick = onClick)
      .background(White)
      .padding(8.dp),
    tint = Gray6A,
  )
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
      EvaluationSearchBar(
        placeHolder = "Hinted search text",
        value = normalValue,
        onValueChange = { normalValue = it },
        onClickClearButton = { normalValue = "" },
      )
    }
  }
}
