package com.suwiki.feature.openmajor

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class OpenMajorState(
  val filteredAllOpenMajorList: PersistentList<OpenMajor> = persistentListOf(),
  val filteredBookmarkedOpenMajorList: PersistentList<OpenMajor> = persistentListOf(),
  val showBottomShadow: Boolean = true,
  val isLoading: Boolean = false,
)

data class OpenMajor(
  val name: String,
  val isBookmarked: Boolean = false,
  val isSelected: Boolean = false,
)

fun List<String>.toBookmarkedOpenMajorList(
  selectedOpenMajor: String,
) = map { name ->
  OpenMajor(
    name = name,
    isBookmarked = true,
    isSelected = selectedOpenMajor == name,
  )
}.toPersistentList()

fun List<String>.toOpenMajorList(
  bookmarkedOpenMajorList: List<String>,
  selectedOpenMajor: String,
) = map { name ->
  OpenMajor(
    name = name,
    isBookmarked = name in bookmarkedOpenMajorList,
    isSelected = selectedOpenMajor == name,
  )
}.toPersistentList()

sealed interface OpenMajorSideEffect {
  data class HandleException(val throwable: Throwable) : OpenMajorSideEffect
  data object PopBackStack : OpenMajorSideEffect
}
