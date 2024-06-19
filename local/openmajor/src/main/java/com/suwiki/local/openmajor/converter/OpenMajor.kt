package com.suwiki.local.openmajor.converter

import com.suwiki.core.model.openmajor.OpenMajor
import com.suwiki.local.common.database.entity.OpenMajorEntity

fun OpenMajorEntity.toModel() = OpenMajor(
  id = id,
  name = name,
)

fun OpenMajor.toEntity() = OpenMajorEntity(name)
