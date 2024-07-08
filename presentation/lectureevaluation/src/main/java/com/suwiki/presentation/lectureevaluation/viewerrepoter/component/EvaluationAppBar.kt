package com.suwiki.presentation.lectureevaluation.viewerrepoter.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.presentation.common.designsystem.theme.GrayFB
import com.suwiki.presentation.common.designsystem.theme.Primary
import com.suwiki.presentation.common.designsystem.theme.SuwikiTheme
import com.suwiki.presentation.common.ui.extension.suwikiClickable
import com.suwiki.presentation.lectureevaluation.R

@Composable
fun EvaluationAppBar(
  modifier: Modifier = Modifier,
  title: String,
  major: String,
  onClickMajor: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .background(GrayFB)
      .padding(top = 28.dp, bottom = 3.dp, start = 24.dp, end = 24.dp),
  ) {
    Text(
      text = title,
      style = SuwikiTheme.typography.header1,
    )
    FilterButton(
      text = major,
      onClick = onClickMajor,
      modifier = Modifier
        .align(CenterVertically),
    )
  }
}

@Composable
fun FilterButton(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .suwikiClickable(onClick = onClick)
      .wrapContentHeight()
      .padding(vertical = 4.dp, horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = text,
      style = SuwikiTheme.typography.header6,
      color = Primary,
    )
    Icon(
      painter = painterResource(id = R.drawable.ic_filter_arrow_down),
      contentDescription = "",
      tint = Primary,
      modifier = Modifier
        .size(24.dp)
        .padding(vertical = 9.dp, horizontal = 7.dp),
    )
  }
}

@Preview(showSystemUi = true)
@Composable
fun EvaluationAppBarPreview() {
  SuwikiTheme {
    EvaluationAppBar(
      title = "강의평가",
      major = "학과명",
      onClickMajor = { /*TODO*/ },
    )
  }
}
