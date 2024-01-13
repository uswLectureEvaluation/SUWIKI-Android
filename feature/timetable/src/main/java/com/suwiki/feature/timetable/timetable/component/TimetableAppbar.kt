package com.suwiki.feature.timetable.timetable.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Black
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.ui.extension.suwikiClickable
import com.suwiki.feature.timetable.R

@Composable
fun TimetableAppbar(
  modifier: Modifier = Modifier,
  name: String? = null,
  onClickAdd: () -> Unit = {},
  onClickHamburger: () -> Unit = {},
  onClickSetting: () -> Unit = {},
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 28.dp, start = 24.dp, end = 20.dp, bottom = 12.dp),
    verticalAlignment = Alignment.Bottom,
    horizontalArrangement = Arrangement.spacedBy(18.dp),
  ) {
    Text(
      modifier = Modifier.weight(1f),
      overflow = TextOverflow.Ellipsis,
      text = name ?: stringResource(R.string.word_timetable),
      style = SuwikiTheme.typography.header1,
      color = Black,
      maxLines = 1,
    )

    Icon(
      modifier = Modifier
        .clip(CircleShape)
        .suwikiClickable(onClick = onClickAdd),
      painter = painterResource(id = R.drawable.ic_timetable_add),
      contentDescription = "",
      tint = Gray95,
    )

    Icon(
      modifier = Modifier
        .clip(CircleShape)
        .suwikiClickable(onClick = onClickHamburger),
      painter = painterResource(id = R.drawable.ic_timetable_hamburger),
      contentDescription = "",
      tint = Gray95,
    )

    Icon(
      modifier = Modifier
        .clip(CircleShape)
        .suwikiClickable(onClick = onClickSetting),
      painter = painterResource(id = R.drawable.ic_timetable_setting),
      contentDescription = "",
      tint = Gray95,
    )
  }
}

@Preview
@Composable
fun TimetableAppbarPreview() {
  SuwikiTheme {
    TimetableAppbar()
  }
}
