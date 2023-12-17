package com.suwiki.core.designsystem.component.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@Composable
fun SuwikiBottomNavigation(
  modifier: Modifier = Modifier,
  isTimeTableChecked: Boolean,
  isEvaluationChecked: Boolean,
  isMyInfoChecked: Boolean,
  onClickTimeTableItem: () -> Unit = {},
  onClickEvaluationItem: () -> Unit = {},
  onClickMyInfoItem: () -> Unit = {},
) {
  Box(
    modifier = modifier
      .shadow(elevation = 4.dp)
      .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
      .background(White)
      .fillMaxWidth()
      .wrapContentHeight(),
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      SuwikiBottomNavigationItem(
        modifier = Modifier.weight(1f),
        isChecked = isTimeTableChecked,
        iconId = R.drawable.ic_bottom_navigation_evaluation,
        onClickItem = onClickTimeTableItem,
      )
      SuwikiBottomNavigationItem(
        modifier = Modifier.weight(1f),
        isChecked = isEvaluationChecked,
        iconId = R.drawable.ic_bottom_navigation_evaluation,
        onClickItem = onClickEvaluationItem,
      )
      SuwikiBottomNavigationItem(
        modifier = Modifier.weight(1f),
        isChecked = isMyInfoChecked,
        iconId = R.drawable.ic_bottom_navigation_evaluation,
        onClickItem = onClickMyInfoItem,
      )
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun SuwikiBottomNavigationPreview() {
  var isTimeTableChecked by remember { mutableStateOf(true) }
  var isEvaluationChecked by remember { mutableStateOf(false) }
  var isMyInfoChecked by remember { mutableStateOf(false) }

  SuwikiTheme {
    Column {
      SuwikiBottomNavigation(
        isTimeTableChecked = isTimeTableChecked,
        isEvaluationChecked = isEvaluationChecked,
        isMyInfoChecked = isMyInfoChecked,
        onClickTimeTableItem = { isTimeTableChecked = !isTimeTableChecked },
        onClickEvaluationItem = { isEvaluationChecked = !isEvaluationChecked },
        onClickMyInfoItem = { isMyInfoChecked = !isMyInfoChecked },
      )
    }
  }
}
