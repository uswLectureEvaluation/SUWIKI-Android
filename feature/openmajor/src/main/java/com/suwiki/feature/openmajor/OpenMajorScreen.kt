package com.suwiki.feature.openmajor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suwiki.core.designsystem.component.appbar.SuwikiAppBarWithTitle
import com.suwiki.core.designsystem.component.button.SuwikiContainedLargeButton
import com.suwiki.core.designsystem.component.loading.LoadingScreen
import com.suwiki.core.designsystem.component.searchbar.SuwikiSearchBar
import com.suwiki.core.designsystem.component.tabbar.SuwikiTabBar
import com.suwiki.core.designsystem.component.tabbar.TabTitle
import com.suwiki.core.designsystem.shadow.suwikiShadow
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White
import com.suwiki.core.ui.extension.isScrolledToEnd
import com.suwiki.feature.openmajor.component.OpenMajorContainer
import com.suwiki.feature.openmajor.model.OpenMajor
import kotlinx.collections.immutable.PersistentList
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private const val OPEN_MAJOR_PAGE_COUNT = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OpenMajorRoute(
  viewModel: OpenMajorViewModel = hiltViewModel(),
  popBackStack: () -> Unit,
  popBackStackWithArgument: (String) -> Unit,
  handleException: (Throwable) -> Unit,
) {
  val uiState = viewModel.collectAsState().value
  viewModel.collectSideEffect { sideEffect ->
    when (sideEffect) {
      is OpenMajorSideEffect.HandleException -> handleException(sideEffect.throwable)
      OpenMajorSideEffect.PopBackStack -> popBackStack()
      is OpenMajorSideEffect.PopBackStackWithArgument -> popBackStackWithArgument(sideEffect.argument)
    }
  }

  val pagerState = rememberPagerState(pageCount = { OPEN_MAJOR_PAGE_COUNT })

  val allOpenMajorListState = rememberLazyListState()
  val bookmarkedOpenMajorListState = rememberLazyListState()

  val onReachedBottomAllOpenMajor by remember {
    derivedStateOf {
      allOpenMajorListState.isScrolledToEnd()
    }
  }

  val onReachedBottomBookmarkedOpenMajor by remember {
    derivedStateOf {
      bookmarkedOpenMajorListState.isScrolledToEnd()
    }
  }

  LaunchedEffect(onReachedBottomAllOpenMajor) {
    viewModel.changeBottomShadowVisible(!onReachedBottomAllOpenMajor)
  }

  LaunchedEffect(onReachedBottomBookmarkedOpenMajor) {
    viewModel.changeBottomShadowVisible(!onReachedBottomBookmarkedOpenMajor)
  }

  LaunchedEffect(key1 = Unit) {
    viewModel.initData()
  }

  OpenMajorScreen(
    uiState = uiState,
    pagerState = pagerState,
    allOpenMajorListState = allOpenMajorListState,
    bookmarkedOpenMajorListState = bookmarkedOpenMajorListState,
    onClickClose = viewModel::popBackStack,
    onClickConfirmButton = viewModel::popBackStackWithArgument,
    onClickOpenMajorContainer = viewModel::updateSelectedOpenMajor,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OpenMajorScreen(
  uiState: OpenMajorState = OpenMajorState(),
  pagerState: PagerState = rememberPagerState(pageCount = { OPEN_MAJOR_PAGE_COUNT }),
  allOpenMajorListState: LazyListState = rememberLazyListState(),
  bookmarkedOpenMajorListState: LazyListState = rememberLazyListState(),
  onClickClose: () -> Unit = {},
  onClickConfirmButton: () -> Unit = {},
  onClickOpenMajorContainer: (String) -> Unit = {},
) {
  Box(
    modifier = Modifier.background(White),
  ) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      SuwikiAppBarWithTitle(
        showBackIcon = false,
        title = "개설학과",
        onClickClose = onClickClose,
      )

      SuwikiSearchBar()

      SuwikiTabBar {
        TabTitle(title = "전체", position = 0, selected = true, onClick = {})
        TabTitle(title = "즐겨찾기", position = 1, selected = false, onClick = {})
      }

      HorizontalPager(
        modifier = Modifier.weight(1f),
        state = pagerState,
      ) { page ->
        when (page) {
          0 -> {
            OpenMajorLazyColumn(
              listState = allOpenMajorListState,
              openMajorList = uiState.filteredAllOpenMajorList,
              onClickOpenMajorContainer = onClickOpenMajorContainer,
            )
          }

          1 -> {
            OpenMajorLazyColumn(
              listState = bookmarkedOpenMajorListState,
              openMajorList = uiState.filteredBookmarkedOpenMajorList,
              onClickOpenMajorContainer = onClickOpenMajorContainer,
            )
          }
        }
      }

      Box(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
      ) {
        SuwikiContainedLargeButton(
          modifier = Modifier
            .suwikiShadow(
              color = if (uiState.showBottomShadow) White else Color.Transparent,
              blurRadius = 80.dp,
              spread = 50.dp,
            ),
          text = stringResource(R.string.word_confirm),
          onClick = onClickConfirmButton,
        )
      }
    }

    if (uiState.isLoading) LoadingScreen()
  }
}

@Composable
private fun OpenMajorLazyColumn(
  listState: LazyListState,
  openMajorList: PersistentList<OpenMajor>,
  onClickOpenMajorContainer: (String) -> Unit = {},
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    state = listState,
    contentPadding = PaddingValues(top = 12.dp),
  ) {
    items(
      count = openMajorList.size,
      key = { index -> openMajorList[index].name },
    ) { index ->
      with(openMajorList[index]) {
        OpenMajorContainer(
          text = name,
          isChecked = isSelected,
          isStared = isBookmarked,
          onClick = { onClickOpenMajorContainer(name) },
        )
      }
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
