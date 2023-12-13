package com.suwiki.core.model.timetable

enum class TimetableCellColor {
  BROWN,
  LIGHT_BROWN,
  ORANGE,
  APRICOT, // 살구
  PURPLE,
  PURPLE_LIGHT,
  RED_LIGHT,
  PINK,
  BROWN_DARK,
  GREEN_DARK,
  GREEN,
  GREEN_LIGHT,
  NAVY_DARK,
  NAVY,
  NAVY_LIGHT,
  VIOLET,
  GRAY_DARK,
  GRAY,
  SKY,
  VIOLET_LIGHT,
}

enum class TimetableDay(val idx: Int) {
  MON(0), TUE(1), WED(2), THU(3), FRI(4), SAT(5), E_LEARNING(6)
}
