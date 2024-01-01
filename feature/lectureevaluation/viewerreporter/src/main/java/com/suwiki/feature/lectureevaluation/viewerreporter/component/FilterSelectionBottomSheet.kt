package com.suwiki.feature.lectureevaluation.viewerreporter.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.align.SuwikiAlignContainer
import com.suwiki.core.designsystem.component.bottomsheet.SuwikiBottomSheet
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.feature.lectureevaluation.viewerreporter.LectureEvaluationState
import kotlinx.collections.immutable.toPersistentList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSelectionBottomSheet(
  uiState: LectureEvaluationState,
  hideFilterSelectionBottomSheet: () -> Unit,
  onClickSelectedItem: () -> Unit,
) {
  SuwikiBottomSheet(
    sheetState = rememberModalBottomSheetState(
      skipPartiallyExpanded = true,
    ),
    isSheetOpen = uiState.showFilterSelectionBottomSheet,
    onDismissRequest = hideFilterSelectionBottomSheet,
  ) {
    FilterSelectionBottomSheetContent(
      onClickSelectedItem = onClickSelectedItem
    )
  }
}

@Composable
private fun FilterSelectionBottomSheetContent(
  onClickSelectedItem: () -> Unit = {},
) {
  Column {
    Text(
      text = "정렬",
      style = SuwikiTheme.typography.body5,
      color = Gray95,
      modifier = Modifier.padding(horizontal = 24.dp, vertical = 15.dp),
    )
    val yourList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    LazyColumn {
      items(items = yourList.toPersistentList()) { item ->
        var isChecked by remember { mutableStateOf(false) }
        SuwikiAlignContainer(
          text = item,
          isChecked = isChecked,
          onClick = {
            isChecked = !isChecked
            onClickSelectedItem.invoke()
          },
        )
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun FilterSelectionBottomSheetContentPreview() {
  SuwikiTheme {
    FilterSelectionBottomSheetContent()
  }
}
