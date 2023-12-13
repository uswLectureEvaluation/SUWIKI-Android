package com.suwiki.core.designsystem.component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.filter.SuwikiFilterButton
import com.suwiki.core.designsystem.theme.GrayFB
import com.suwiki.core.designsystem.theme.SuwikiTheme

@Composable
fun SuwikiEvaluationAppBar(
  modifier: Modifier = Modifier,
  title: String,
  major: String,
  onClickMajor: () -> Unit = {}
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(GrayFB)
  ) {
    Row(
      modifier = Modifier
        .align(Alignment.CenterStart)
        .padding(top = 28.dp, bottom = 3.dp, start = 24.dp, end = 24.dp)
    ) {
      Text(
        text = title,
        style = SuwikiTheme.typography.header1
      )
      SuwikiFilterButton(
        text = major,
        onClick = onClickMajor,
        modifier = Modifier
          .align(CenterVertically)
      )
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun SuwikiEvaluationAppBarPreview() {
  SuwikiTheme {
    SuwikiEvaluationAppBar(
      title = "강의평가",
      major = "학과명",
      onClickMajor = { /*TODO*/ }
    )
  }
}
