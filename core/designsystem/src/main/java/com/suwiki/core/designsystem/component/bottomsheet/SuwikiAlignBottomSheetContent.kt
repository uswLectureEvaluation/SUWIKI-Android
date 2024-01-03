package com.suwiki.core.designsystem.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.align.SuwikiAlignContainer
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuwikiAlignBottomSheet(
  isSheetOpen: Boolean,
  hideAlignBottomSheet: () -> Unit,
  onClickAlignBottomSheetItem: (String) -> Unit,
  itemList: List<String>,
  bottomSheetTitle: String,
  selectedItem: String,
) {
  SuwikiBottomSheet(
    sheetState = rememberModalBottomSheetState(
      skipPartiallyExpanded = true,
    ),
    isSheetOpen = isSheetOpen,
    onDismissRequest = hideAlignBottomSheet,
  ) {
    SuwikiAlignBottomSheetContent(
      onClickAlignBottomSheetItem = onClickAlignBottomSheetItem,
      bottomSheetTitle = bottomSheetTitle,
      itemList = itemList,
      selectedItem = selectedItem,
    )
  }
}

@Composable
fun SuwikiAlignBottomSheetContent(
  selectedItem: String,
  onClickAlignBottomSheetItem: (String) -> Unit = {},
  bottomSheetTitle: String,
  itemList: List<String>,
) {
  Column(
    modifier = Modifier
      .padding(top = 36.dp, bottom = 45.dp),
  ) {
    Text(
      text = bottomSheetTitle,
      style = SuwikiTheme.typography.body5,
      color = Gray95,
      modifier = Modifier.padding(horizontal = 24.dp, vertical = 15.dp),
    )

    itemList.forEachIndexed { index, item ->
      val isChecked = selectedItem == itemList[index]
      SuwikiAlignContainer(
        text = item,
        isChecked = isChecked,
        onClick = {
          if (!isChecked) {
            onClickAlignBottomSheetItem(item)
          }
        },
      )
    }
  }
}
