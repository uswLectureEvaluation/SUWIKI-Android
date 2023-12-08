package com.suwiki.core.designsystem.component.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suwiki.core.designsystem.R
import com.suwiki.core.ui.extension.suwikiClickable

@Composable
fun SuwikiFilterContainer(
  text: String,
  isChecked: Boolean,
  isStared: Boolean,
  onClick: () -> Unit = {},
  onStarClick: () -> Unit = {},
) {
  val (textColor, backgroundColor) = if (isChecked) {
    Color(0xFF346CFD) to Color(0xFFF5F8FF)
  } else {
    Color(0xFF222222) to Color(0xFFFFFFFF)
  }

  Box(
    modifier = Modifier
      .background(backgroundColor)
      .size(width = 360.dp, height = 48.dp)
      .suwikiClickable(onClick = onClick),
  ) {
    Row(
      verticalAlignment = Alignment.Top,
      horizontalArrangement = Arrangement.Start,
      modifier = Modifier
        .padding(vertical = 12.dp, horizontal = 24.dp),
    ) {
      Icon(
        painter = painterResource(id = R.drawable.ic_filter_stared),
        contentDescription = "",
        modifier = Modifier
          .size(24.dp)
          .suwikiClickable(
            onClick = onStarClick,
            rippleEnabled = false,
          ),
        tint = if (isStared) Color(0xFF346CFD) else Color(0xFFDADADA),
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = text,
        color = textColor,
        fontSize = 15.sp,
        modifier = Modifier.height(23.dp),
      )
    }
  }
}

@Preview
@Composable
fun SuwikiFilterContainerPreview() {
  var isChecked by remember { mutableStateOf(false) }
  var isStared by remember { mutableStateOf(false) }

  SuwikiFilterContainer(
    text = "개설학과명",
    isChecked = isChecked,
    isStared = isStared,
    onClick = { isChecked = !isChecked },
    onStarClick = {isStared = !isStared},
  )
}
