package com.suwiki.feature.openmajor.model

import kotlinx.collections.immutable.toPersistentList

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
