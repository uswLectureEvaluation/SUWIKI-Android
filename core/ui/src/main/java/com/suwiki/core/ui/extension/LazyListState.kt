package com.suwiki.core.ui.extension

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

// https://manavtamboli.medium.com/infinite-list-paged-list-in-jetpack-compose-b10fc7e74768
@Composable
fun LazyListState.OnBottomReached(
  // tells how many items before we reach the bottom of the list
  // to call onLoadMore function
  buffer: Int = 0,
  onLoadMore: () -> Unit,
) {
  // Buffer must be positive.
  // Or our list will never reach the bottom.
  require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

  val shouldLoadMore = remember {
    derivedStateOf {
      val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
        ?: return@derivedStateOf true

      // subtract buffer from the total items
      lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
    }
  }

  LaunchedEffect(shouldLoadMore) {
    snapshotFlow { shouldLoadMore.value }
      .collect { if (it) onLoadMore() }
  }
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
