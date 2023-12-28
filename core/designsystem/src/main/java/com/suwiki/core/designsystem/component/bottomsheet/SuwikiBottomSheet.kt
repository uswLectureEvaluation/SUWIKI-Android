package com.suwiki.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuwikiBottomSheet(
  sheetState: SheetState = rememberModalBottomSheetState(),
  isSheetOpen: Boolean,
  onDismissRequest: () -> Unit = {},
  content: @Composable ColumnScope.() -> Unit,
) {
  if (isSheetOpen) {
    ModalBottomSheet(
      sheetState = sheetState,
      shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
      onDismissRequest = onDismissRequest,
      containerColor = White,
      dragHandle = null,
    ) {
      content()
    }
  }
}

@Composable
fun SuwikiBottomSheetItem(
  modifier: Modifier = Modifier,
  title: String,
) {
  Text(
    text = title,
    style = SuwikiTheme.typography.body5,
    color = Gray95,
    modifier = modifier
      .fillMaxWidth()
      .background(White)
      .padding(vertical = 14.dp, horizontal = 24.dp),
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SuwikiBottomSheetPreview() {
  var isSheetOpen by rememberSaveable { mutableStateOf(false) }

  // 테스트용 버튼
  Button(onClick = { isSheetOpen = true }) {
    Text("Bottom Sheet 열기")
  }

  SuwikiTheme {
    SuwikiBottomSheet(
      isSheetOpen = isSheetOpen,
      onDismissRequest = { isSheetOpen = !isSheetOpen },
      content = {},
    )
  }
}
