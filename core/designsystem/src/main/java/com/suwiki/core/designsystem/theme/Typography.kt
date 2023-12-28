package com.suwiki.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.suwiki.core.designsystem.R

internal val notoSansFamily = FontFamily(
  Font(R.font.notosanskrbold, FontWeight.Bold),
  Font(R.font.notosanskrmedium, FontWeight.Medium),
  Font(R.font.notosanskrregular, FontWeight.Normal),
  Font(R.font.notosanskrlight, FontWeight.Light),
)

private val notoSansStyle = TextStyle(
  fontFamily = notoSansFamily,
  lineHeight = 1.5.em,
  platformStyle = PlatformTextStyle(
    includeFontPadding = false,
  ),
  lineHeightStyle = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None,
  ),
  color = Black,
)

internal val Typography = SuwikiTypography(
  header1 = notoSansStyle.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp,
  ),
  header2 = notoSansStyle.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
  ),
  header3 = notoSansStyle.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp,
  ),
  header4 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
  ),
  header5 = notoSansStyle.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
  ),
  header6 = notoSansStyle.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
  ),
  header7 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
  ),

  body1 = notoSansStyle.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 15.sp,
  ),
  body2 = notoSansStyle.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 15.sp,
  ),
  body3 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
  ),
  body4 = notoSansStyle.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
  ),
  body5 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
  ),
  body6 = notoSansStyle.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
  ),
  body7 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
  ),

  caption1 = notoSansStyle.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
  ),
  caption2 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
  ),
  caption3 = notoSansStyle.copy(
    fontWeight = FontWeight.Bold,
    fontSize = 11.sp,
  ),
  caption4 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
  ),
  caption5 = notoSansStyle.copy(
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp,
  ),
  caption6 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
  ),
  caption7 = notoSansStyle.copy(
    fontWeight = FontWeight.Normal,
    fontSize = 8.sp,
  ),
)

@Immutable
data class SuwikiTypography(
  val header1: TextStyle,
  val header2: TextStyle,
  val header3: TextStyle,
  val header4: TextStyle,
  val header5: TextStyle,
  val header6: TextStyle,
  val header7: TextStyle,

  val body1: TextStyle,
  val body2: TextStyle,
  val body3: TextStyle,
  val body4: TextStyle,
  val body5: TextStyle,
  val body6: TextStyle,
  val body7: TextStyle,

  val caption1: TextStyle,
  val caption2: TextStyle,
  val caption3: TextStyle,
  val caption4: TextStyle,
  val caption5: TextStyle,
  val caption6: TextStyle,
  val caption7: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
  SuwikiTypography(
    header1 = notoSansStyle,
    header2 = notoSansStyle,
    header3 = notoSansStyle,
    header4 = notoSansStyle,
    header5 = notoSansStyle,
    header6 = notoSansStyle,
    header7 = notoSansStyle,
    body1 = notoSansStyle,
    body2 = notoSansStyle,
    body3 = notoSansStyle,
    body4 = notoSansStyle,
    body5 = notoSansStyle,
    body6 = notoSansStyle,
    body7 = notoSansStyle,
    caption1 = notoSansStyle,
    caption2 = notoSansStyle,
    caption3 = notoSansStyle,
    caption4 = notoSansStyle,
    caption5 = notoSansStyle,
    caption6 = notoSansStyle,
    caption7 = notoSansStyle,
  )
}
