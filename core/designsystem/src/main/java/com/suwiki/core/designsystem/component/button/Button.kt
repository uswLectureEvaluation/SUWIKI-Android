package com.suwiki.core.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal object ButtonDefaultRound {
  val Small = 5.dp
  val Medium = 10.dp
  val Large = 15.dp
}

@Stable
private val SuwikiBigRoundButtonDefaultRound = ButtonDefaultRound.Medium

@Composable
fun SuwikiContainedBigButton(
  modifier: Modifier = Modifier,
  round: Dp = SuwikiBigRoundButtonDefaultRound,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicContainedBigButton(
    modifier = modifier,
    text = text,
    shape = RoundedCornerShape(round),
    onClick = onClick,
    enabledBackGroundColor = Color(0xFF346CFD),
    pressedBackgroundColor = Color(0xFF0244F0),
    disabledBackgroundColor = Color(0xFFF6F6F6),
    textColor = Color(0xFFFFFFFF),
    disabledTextColor = Color(0xFF959595),
    enabled = enabled,
  )
}

@Composable
fun SuwikiOutlinedBigButton(
  modifier: Modifier = Modifier,
  round: Dp = SuwikiBigRoundButtonDefaultRound,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicOutlineBigButton(
    modifier = modifier,
    text = text,
    shape = RoundedCornerShape(round),
    onClick = onClick,
    enabledBackGroundColor = Color(0xFFFFFFFF),
    pressedBackgroundColor = Color(0xFFFBFBFB),
    textColor = Color(0xFF346CFD),
    enabled = enabled,
  )
}

@Preview
@Composable
fun ButtonPreview() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {

    SuwikiContainedBigButton(
      text = "버튼",
    ) {
    }

    SuwikiContainedBigButton(
      text = "버튼",
      enabled = false,
    ) {
    }

    SuwikiOutlinedBigButton(
      text = "버튼",
    ) {
    }
  }
}
