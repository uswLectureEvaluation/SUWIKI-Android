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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

enum class BottomNavigationIndicate {
  TIMETABLE,
  EVALUATION,
  MYINFO,
}

@Composable
fun SuwikiBottomNavigation(
  modifier: Modifier = Modifier,
  content: List<@Composable () -> Unit>,
) {
  Box(
    modifier = modifier
      .shadow(elevation = 4.dp)
      .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
      .background(White)
      .fillMaxWidth()
      .wrapContentHeight()
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      content.forEach { item ->
        item()
      }
    }
  }
}

@Composable
fun SetBottomNavigationItemActive(
  modifier: Modifier = Modifier,
  isTimeTableActive: Boolean,
  isEvaluationActive: Boolean,
  isMyInfoActive: Boolean,
  iconId: Int,
  changeTimeTableActive: () -> Unit,
  changeEvaluationActive: () -> Unit,
  changeMyInfoActive: () -> Unit,
  itemIndicator: BottomNavigationIndicate,
) {
  val (isActive, changeActive) = when (itemIndicator) {
    BottomNavigationIndicate.TIMETABLE -> isTimeTableActive to changeTimeTableActive
    BottomNavigationIndicate.EVALUATION -> isEvaluationActive to changeEvaluationActive
    BottomNavigationIndicate.MYINFO -> isMyInfoActive to changeMyInfoActive
  }

  if (isActive) {
    SuwikiBottomNavigationActiveItem(
      modifier = modifier,
      iconId = iconId,
      onClickItem = changeActive,
    )
  } else {
    SuwikiBottomNavigationInActiveItem(
      modifier = modifier,
      iconId = iconId,
      onClickItem = changeActive,
    )
  }
}

@Preview(widthDp = 400, heightDp = 200, showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SuwikiBottomNavigationPreview() {
  SuwikiTheme {
    Column {
      var isTimeTableActive by rememberSaveable { mutableStateOf(true) }
      var isEvaluationActive by rememberSaveable { mutableStateOf(false) }
      var isMyInfoActive by rememberSaveable { mutableStateOf(false) }

      val changeTimeTableActive = {
        isTimeTableActive = true
        isEvaluationActive = false
        isMyInfoActive = false
      }

      val changeEvaluationActive = {
        isTimeTableActive = false
        isEvaluationActive = true
        isMyInfoActive = false
      }

      val changeMyInfoActive = {
        isTimeTableActive = false
        isEvaluationActive = false
        isMyInfoActive = true
      }

      SuwikiBottomNavigation(
        content = listOf(
          {
            SetBottomNavigationItemActive(
              modifier = Modifier.weight(1f),
              isTimeTableActive = isTimeTableActive,
              isEvaluationActive = isEvaluationActive,
              isMyInfoActive = isMyInfoActive,
              iconId = R.drawable.ic_bottom_navigation_evaluation,
              changeTimeTableActive = changeTimeTableActive,
              changeEvaluationActive = changeEvaluationActive,
              changeMyInfoActive = changeMyInfoActive,
              itemIndicator = BottomNavigationIndicate.TIMETABLE,
            )
          },
          {
            SetBottomNavigationItemActive(
              modifier = Modifier.weight(1f),
              isTimeTableActive = isTimeTableActive,
              isEvaluationActive = isEvaluationActive,
              isMyInfoActive = isMyInfoActive,
              iconId = R.drawable.ic_bottom_navigation_evaluation,
              changeTimeTableActive = changeTimeTableActive,
              changeEvaluationActive = changeEvaluationActive,
              changeMyInfoActive = changeMyInfoActive,
              itemIndicator = BottomNavigationIndicate.EVALUATION,
            )
          },
          {
            SetBottomNavigationItemActive(
              modifier = Modifier.weight(1f),
              isTimeTableActive = isTimeTableActive,
              isEvaluationActive = isEvaluationActive,
              isMyInfoActive = isMyInfoActive,
              iconId = R.drawable.ic_bottom_navigation_evaluation,
              changeTimeTableActive = changeTimeTableActive,
              changeEvaluationActive = changeEvaluationActive,
              changeMyInfoActive = changeMyInfoActive,
              itemIndicator = BottomNavigationIndicate.MYINFO,
            )
          },
        ),
      )
    }
  }
}
