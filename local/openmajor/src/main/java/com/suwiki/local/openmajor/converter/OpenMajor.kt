package com.suwiki.local.openmajor.converter

import com.suwiki.core.database.model.OpenMajorEntity
import com.suwiki.core.model.openmajor.OpenMajor

fun OpenMajorEntity.toModel() = OpenMajor(
    id = id,
    name = name,
)

fun OpenMajor.toEntity() = OpenMajorEntity(name)
