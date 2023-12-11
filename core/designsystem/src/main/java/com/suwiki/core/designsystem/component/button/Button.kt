package com.suwiki.core.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.suwiki.core.designsystem.R

internal object ButtonDefaultRound {
  val Small = 5.dp
  val Regular = 10.dp
  val Large = 15.dp
}

@Stable
private val SuwikiButtonLargeDefaultRound = ButtonDefaultRound.Large

@Composable
fun SuwikiContainedButtonLarge(
  modifier: Modifier = Modifier,
  round: Dp = SuwikiButtonLargeDefaultRound,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicContainedButtonLarge(
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

@Stable
private val SuwikiRoundButtonRegularDefaultRound = ButtonDefaultRound.Regular

@Composable
fun SuwikiContainedButtonRegular(
  modifier: Modifier = Modifier,
  round: Dp = SuwikiRoundButtonRegularDefaultRound,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicContainedButton(
    modifier = modifier,
    text = text,
    shape = RoundedCornerShape(round),
    onClick = onClick,
    enabledBackGroundColor = Color(0xFF346CFD),
    textColor = Color(0xFFFFFFFF),
    enabled = enabled,
    padding = PaddingValues(22.dp, 9.dp),
  )
}

@Composable
fun SuwikiContainedIconButtonRegular(
  modifier: Modifier = Modifier,
  painter: Painter,
  round: Dp = SuwikiRoundButtonRegularDefaultRound,
  text: String,
  contentDescription: String?,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicContainedIconButtonRegular(
    modifier = modifier,
    text = text,
    shape = RoundedCornerShape(round),
    onClick = onClick,
    enabledBackGroundColor = Color(0xFF346CFD),
    textColor = Color(0xFFFFFFFF),
    enabled = enabled,
    icon = painter,
    contentDescription = contentDescription,
  )
}

@Composable
fun SuwikiContainedButtonSmall(
  modifier: Modifier = Modifier,
  round: Dp = SuwikiRoundButtonRegularDefaultRound,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicContainedButton(
    modifier = modifier,
    text = text,
    shape = RoundedCornerShape(round),
    onClick = onClick,
    enabledBackGroundColor = Color(0xFFFFFFFF),
    textColor = Color(0xFF346CFD),
    enabled = enabled,
    padding = PaddingValues(8.dp, 4.dp),
  )
}

@Stable
private val SuwikiSmallRoundButtonDefaultRound = ButtonDefaultRound.Small

@Composable
fun SuwikiContainedGreyButtonSmall(
  modifier: Modifier = Modifier,
  round: Dp = SuwikiSmallRoundButtonDefaultRound,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicContainedButton(
    modifier = modifier,
    text = text,
    shape = RoundedCornerShape(round),
    onClick = onClick,
    enabledBackGroundColor = Color(0xFFF6F6F6),
    textColor = Color(0xFF959595),
    enabled = enabled,
    padding = PaddingValues(6.dp, 2.dp),
  )
}

@Composable
fun SuwikiOutlinedButtonLarge(
  modifier: Modifier = Modifier,
  round: Dp = SuwikiButtonLargeDefaultRound,
  text: String,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  BasicOutlineButtonLarge(
    modifier = modifier,
    text = text,
    shape = RoundedCornerShape(round),
    onClick = onClick,
    enabledBackGroundColor = Color(0xFFFFFFFF),
    pressedBackgroundColor = Color(0xFFFBFBFB),
    textColor = Color(0xFF346CFD),
    enabled = enabled,
    borderColor = Color(0xFF346CFD),
  )
}

@Preview
@Composable
fun ButtonPreview() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    SuwikiContainedButtonLarge(
      text = "버튼",
    ) {
    }

    SuwikiContainedButtonLarge(
      text = "버튼",
      enabled = false,
    ) {
    }

    SuwikiOutlinedButtonLarge(
      text = "버튼",
    ) {
    }

    SuwikiContainedButtonRegular(
      text = "강의평가 작성하기",
    ) {
    }

    SuwikiContainedIconButtonRegular(
      painter = painterResource(id = R.drawable.ic_color_checked_chip),
      contentDescription = "suwiki",
      text = "작성하기",
    ) {
    }
    SuwikiContainedButtonSmall(
      text = "text",
    ) {
    }

    SuwikiContainedGreyButtonSmall(
      text = "신고",
    ) {
    }
  }
}
