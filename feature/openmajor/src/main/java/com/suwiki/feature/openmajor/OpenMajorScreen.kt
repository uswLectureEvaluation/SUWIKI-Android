package com.suwiki.feature.openmajor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.searchbar.SuwikiSearchBar
import com.suwiki.core.designsystem.component.tabbar.SuwikiTabBar
import com.suwiki.core.designsystem.component.tabbar.TabTitle
import com.suwiki.core.designsystem.shadow.suwikiShadow
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private const val OPEN_MAJOR_PAGE_COUNT = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OpenMajorRoute(
  viewModel: OpenMajorViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is OpenMajorSideEffect.HandleException -> handleException(sideEffect.throwable)
      OpenMajorSideEffect.PopBackStack -> popBackStack()
    }
  }

  val pagerState = rememberPagerState(pageCount = { OPEN_MAJOR_PAGE_COUNT })

  OpenMajorScreen(
    uiState = uiState,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OpenMajorScreen(
  uiState: OpenMajorState = OpenMajorState(),
  pagerState: PagerState = rememberPagerState(pageCount = { OPEN_MAJOR_PAGE_COUNT }),
) {
  Box {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      SuwikiAppBarWithTitle(
        showBackIcon = false,
        title = "개설학과",
      )

      SuwikiSearchBar()

      SuwikiTabBar {
        TabTitle(title = "전체", position = 0, selected = true, onClick = {})
        TabTitle(title = "즐겨찾기", position = 1, selected = false, onClick = {})
      }

      Spacer(modifier = Modifier.size(12.dp))

      HorizontalPager(
        state = pagerState,
      ) { page ->
        when (page) {
          0 -> {
          }

          1 -> {
          }
        }
      }
    }

    Box(
      modifier = Modifier
        .padding(24.dp)
        .align(Alignment.BottomCenter)
        .suwikiShadow(
          color = White,
          blurRadius = 50.dp,
        )
        .height(60.dp),
    ) {
      SuwikiContainedLargeButton(
        modifier = Modifier
          .align(Alignment.BottomCenter),
        text = "확인",
      )
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun OpenMajorScreenPreview() {
  SuwikiTheme {
    OpenMajorScreen()
  }
}
