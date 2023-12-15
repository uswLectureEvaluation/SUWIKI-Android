package com.suwiki.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.component.align.SuwikiAlignContainer
import com.suwiki.core.designsystem.theme.Gray95
import com.suwiki.core.designsystem.theme.SuwikiTheme
import com.suwiki.core.designsystem.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuwikiBottomSheet(
  isSheetOpen: Boolean,
  changeSheetOpen: () -> Unit = {},
  bottomSheetItem: List<@Composable () -> Unit>,
) {
  if (isSheetOpen) {
    ModalBottomSheet(
      onDismissRequest = changeSheetOpen,
      containerColor = White,
      dragHandle = null,
    ) {
      Spacer(modifier = Modifier.height(36.dp))
      bottomSheetItem.forEach { item ->
        item()
      }
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

@Preview
@Composable
fun SuwikiBottomSheetPreview() {
  var isChecked by remember { mutableStateOf(false) }
  var isSheetOpen by rememberSaveable { mutableStateOf(false) }

  // 테스트용 버튼
  Button(onClick = { isSheetOpen = true }) {
    Text("Bottom Sheet 열기")
  }

  SuwikiTheme {
    SuwikiBottomSheet(
      isSheetOpen = isSheetOpen,
      changeSheetOpen = { isSheetOpen = !isSheetOpen },
      bottomSheetItem = listOf(
        { SuwikiBottomSheetItem(title = "타이틀") },
        {
          SuwikiAlignContainer(
            text = "메뉴",
            isChecked = isChecked,
            onClick = { isChecked = !isChecked },
          )
        },
      ),
    )
  }
}
