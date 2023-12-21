package com.suwiki.feature.navigator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.suwiki.core.designsystem.component.toast.SuwikiToast
import com.suwiki.core.designsystem.shadow.bottomNavigationShadow
import com.suwiki.core.designsystem.theme.GrayDA
import com.suwiki.core.designsystem.theme.Primary
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.feature.lectureevaluation.viewerreporter.navigation.lectureEvaluationNavGraph
import com.suwiki.feature.myinfo.navigation.myInfoNavGraph
import com.suwiki.feature.timetable.navigation.timetableNavGraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

private const val SHOW_TOAST_LENGTH = 2000L
val mutex = Mutex()

@Composable
internal fun MainScreen(
  modifier: Modifier = Modifier,
  navigator: MainNavigator = rememberMainNavigator(),
) {
  val coroutineScope = rememberCoroutineScope()

  var toastMessage: String? by remember { mutableStateOf(null) }
  var toastVisible by remember { mutableStateOf(false) }

  val onShowToast: (message: String) -> Unit = { message ->
    coroutineScope.launch {
      mutex.lock()
      toastMessage = message
    }
  }

  LaunchedEffect(key1 = toastMessage) {
    if (toastMessage == null) return@LaunchedEffect

    toastVisible = true
    delay(SHOW_TOAST_LENGTH)
    toastVisible = false
    if (mutex.isLocked) mutex.unlock()
  }

  Scaffold(
    modifier = modifier,
    content = { innerPadding ->
      NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
      ) {
        timetableNavGraph(
          padding = innerPadding,
        )

        lectureEvaluationNavGraph(
          padding = innerPadding,
        )

        myInfoNavGraph(
          padding = innerPadding,
        )
      }

      SuwikiToast(
        visible = toastVisible,
        message = toastMessage ?: "",
      )
    },
    bottomBar = {
      MainBottomBar(
        visible = navigator.shouldShowBottomBar(),
        tabs = MainTab.entries, currentTab = navigator.currentTab,
        onTabSelected = navigator::navigate,
      )
    },
  )
}

@Preview
@Composable
fun MainScreenPreview() {
  SuwikiTheme {
    MainScreen()
  }
}

@Composable
private fun MainBottomBar(
  visible: Boolean,
  tabs: List<MainTab>,
  currentTab: MainTab?,
  onTabSelected: (MainTab) -> Unit,
) {
  AnimatedVisibility(
    visible = visible,
    enter = fadeIn() + slideIn { IntOffset(0, it.height) },
    exit = fadeOut() + slideOut { IntOffset(0, it.height) },
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .bottomNavigationShadow()
        .background(
          color = White,
          shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        ),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      tabs.forEach { tab ->
        MainBottomBarItem(
          tab = tab,
          selected = tab == currentTab,
          onClick = { onTabSelected(tab) },
        )
      }
    }
  }
}

@Composable
private fun RowScope.MainBottomBarItem(
  tab: MainTab,
  selected: Boolean,
  onClick: () -> Unit,
) {
  Box(
    modifier = Modifier
      .weight(1f)
      .fillMaxHeight()
      .selectable(
        selected = selected,
        indication = null,
        role = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick,
      ),
    contentAlignment = Alignment.Center,
  ) {
    Icon(
      painter = painterResource(tab.iconResId),
      contentDescription = tab.contentDescription,
      tint = if (selected) {
        Primary
      } else {
        GrayDA
      },
      modifier = Modifier.size(24.dp),
    )
  }
}
