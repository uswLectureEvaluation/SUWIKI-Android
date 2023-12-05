package com.suwiki.core.designsystem.component.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R

@Composable
fun SuwikiDropDownMenu(
  label: String = "",
  onClickLabel: () -> Unit = {},
  onDismissRequest: () -> Unit = {},
  expanded: Boolean = false,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(
    horizontalAlignment = Alignment.Start,
  ) {
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .clickable(onClick = onClickLabel)
        .background(Color.LightGray)
        .padding(
          horizontal = 9.dp,
          vertical = 6.dp,
        ),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = label,
      )
      Image(
        painter = painterResource(id = R.drawable.ic_dropdown_arrow_down),
        contentDescription = "",
      )
    }

    MaterialTheme(
      shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(10.dp)),
    ) {
      DropdownMenu(
        modifier = Modifier
          .width(106.dp)
          .background(Color.White)
          .padding(vertical = 8.dp)
          .clip(RoundedCornerShape(20.dp)),
        offset = DpOffset(x = 0.dp, y = 6.dp),
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        content = content,
      )
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun SuwikiDropDownMenuPreview() {
  var isSemesterDropDownExpanded by remember {
    mutableStateOf(false)
  }

  var isExamDropdownExpanded by remember {
    mutableStateOf(false)
  }

  Column {
    SuwikiDropDownMenu(
      label = "수강학기 선택",
      expanded = isSemesterDropDownExpanded,
      onClickLabel = { isSemesterDropDownExpanded = true },
      onDismissRequest = { isSemesterDropDownExpanded = false },
    ) {
      repeat(4) {
        SuwikiDropdownMenuItem(
          text = "menu",
        )
      }
    }

    SuwikiDropDownMenu(
      label = "시험유형 선택",
      expanded = isExamDropdownExpanded,
      onClickLabel = { isExamDropdownExpanded = true },
      onDismissRequest = { isExamDropdownExpanded = false },
    ) {
      repeat(4) {
        SuwikiDropdownMenuItem(
          text = "menu",
        )
      }
    }
  }
}

@Composable
fun SuwikiDropdownMenuItem(
  onClick: () -> Unit = {},
  text: String = "",
) {
  DropdownMenuItem(
    modifier = Modifier.fillMaxWidth(),
    onClick = onClick,
    text = {
      Text(
        text = text,
        textAlign = TextAlign.Start,
      )
    },
  )
}
