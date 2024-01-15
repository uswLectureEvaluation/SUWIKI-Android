package com.suwiki.core.model.exception

class TimetableCellOverlapException(
  val name: String,
  val startPeriod: Int,
  val endPeriod: Int,
  override val message: String = "겹치는 시간이 있어요\n$name ($startPeriod - $endPeriod)",
) : RuntimeException()

class TimetableCellPeriodInvalidException(
  val startPeriod: Int,
  val endPeriod: Int,
  override val message: String = "교시를 확인해주세요 (${startPeriod}교시 - ${endPeriod}교시)",
) : RuntimeException()
