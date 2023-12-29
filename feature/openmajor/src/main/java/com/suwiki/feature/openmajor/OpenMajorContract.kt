package com.suwiki.feature.openmajor

import com.suwiki.feature.openmajor.model.OpenMajor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class OpenMajorState(
  val filteredAllOpenMajorList: PersistentList<OpenMajor> = persistentListOf(),
  val filteredBookmarkedOpenMajorList: PersistentList<OpenMajor> = persistentListOf(),
  val showBottomShadow: Boolean = true,
  val currentPage: Int = 0,
  val isLoading: Boolean = false,
)

sealed interface OpenMajorSideEffect {
  data class HandleException(val throwable: Throwable) : OpenMajorSideEffect
  data object PopBackStack : OpenMajorSideEffect
  data class PopBackStackWithArgument(val argument: String) : OpenMajorSideEffect

  data object ShowNeedLoginToast : OpenMajorSideEffect
}
