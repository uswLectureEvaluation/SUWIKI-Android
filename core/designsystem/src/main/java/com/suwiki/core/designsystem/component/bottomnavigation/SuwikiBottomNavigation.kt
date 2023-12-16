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
  timeTableComposable: @Composable () -> Unit,
  evaluationComposable: @Composable () -> Unit = @Composable {},
  myInfoComposable: @Composable () -> Unit = @Composable {},
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
      val itemModifier = Modifier.weight(1f)

      timeTableComposable()
      evaluationComposable()
      myInfoComposable()
    }
  }
}

@Composable
fun SetBottomNavigationItemActive(
  modifier: Modifier = Modifier,
  isTimeTableActive: Boolean,
  isEvaluationActive: Boolean,
  isMyInfoActive: Boolean,
  timeTableItemIcon: Int,
  evaluationItemIcon: Int,
  myInfoItemIcon: Int,
  changeTimeTableActive: () -> Unit,
  changeEvaluationActive: () -> Unit,
  changeMyInfoActive: () -> Unit,
  itemIndicator: BottomNavigationIndicate,
) {
  val (isActive, iconId, changeActive) = when (itemIndicator) {
    BottomNavigationIndicate.TIMETABLE ->
      Triple(
        isTimeTableActive,
        timeTableItemIcon,
        changeTimeTableActive,
      )
    BottomNavigationIndicate.EVALUATION ->
      Triple(
        isEvaluationActive,
        evaluationItemIcon,
        changeEvaluationActive,
      )
    BottomNavigationIndicate.MYINFO ->
      Triple(
        isMyInfoActive,
        myInfoItemIcon,
        changeMyInfoActive,
      )
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
        timeTableComposable = {
          SetBottomNavigationItemActive(
            isTimeTableActive = isTimeTableActive,
            isEvaluationActive = isEvaluationActive,
            isMyInfoActive = isMyInfoActive,
            timeTableItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            evaluationItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            myInfoItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            changeTimeTableActive = changeTimeTableActive,
            changeEvaluationActive = changeEvaluationActive,
            changeMyInfoActive = changeMyInfoActive,
            itemIndicator = BottomNavigationIndicate.TIMETABLE
          )
        },
        evaluationComposable = {
          SetBottomNavigationItemActive(
            isTimeTableActive = isTimeTableActive,
            isEvaluationActive = isEvaluationActive,
            isMyInfoActive = isMyInfoActive,
            timeTableItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            evaluationItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            myInfoItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            changeTimeTableActive = changeTimeTableActive,
            changeEvaluationActive = changeEvaluationActive,
            changeMyInfoActive = changeMyInfoActive,
            itemIndicator = BottomNavigationIndicate.EVALUATION
          )
        },
        myInfoComposable = {
          SetBottomNavigationItemActive(
            isTimeTableActive = isTimeTableActive,
            isEvaluationActive = isEvaluationActive,
            isMyInfoActive = isMyInfoActive,
            timeTableItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            evaluationItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            myInfoItemIcon = R.drawable.ic_bottom_navigation_evaluation,
            changeTimeTableActive = changeTimeTableActive,
            changeEvaluationActive = changeEvaluationActive,
            changeMyInfoActive = changeMyInfoActive,
            itemIndicator = BottomNavigationIndicate.MYINFO
          )
        }
      )
    }
  }
}
