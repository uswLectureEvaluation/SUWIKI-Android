package com.suwiki.core.model.timetable

data class TimetableCell(
  val name: String = "",
  val professor: String = "",
  val location: String = "",
  val day: String = "",
  val startTime: String = "",
  val endTime: String = "",
  val color: Int,
  val credit: String = "",
)

/**
 * object TimetableCellColor {
 *     val colorMap = mapOf<String, Int>(
 *         "Pink" to Color.rgb(254, 136, 136), // 핑크
 *         "Orange" to Color.rgb(255, 193, 82), // 주황
 *         "Purple" to Color.rgb(204, 154, 243), // 보라
 *         "Sky" to Color.rgb(137, 200, 254), // 하늘
 *         "Green" to Color.rgb(165, 220, 129), // 연두
 *         "Brown" to Color.rgb(194, 143, 98), // 갈색
 *         "Gray" to Color.rgb(194, 193, 189), // 회색
 *         "Navy" to Color.rgb(67, 87, 150), // 남색
 *         "darkGreen" to Color.rgb(107, 143, 84), // 진녹색
 *         "lightBrown" to Color.rgb(255, 187, 128), // 연갈색
 *         "darkPurple" to Color.rgb(161, 121, 192), // 진보라색
 *         "darkGray" to Color.rgb(143, 142, 139) // 진회색
 *     )
 * }
 */
