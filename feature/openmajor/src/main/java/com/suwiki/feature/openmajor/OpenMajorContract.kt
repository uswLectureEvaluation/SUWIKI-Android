package com.suwiki.feature.openmajor

data class OpenMajorState(
  val selectedOpenMajor: String = "",
)

sealed interface OpenMajorSideEffect {
  data class HandleException(val throwable: Throwable) : OpenMajorSideEffect
  data object PopBackStack : OpenMajorSideEffect
}
