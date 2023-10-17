package com.suwiki.local.openmajor.converter

import com.suwiki.core.database.model.OpenMajorEntity
import com.suwiki.core.model.OpenMajor

fun OpenMajorEntity.toModel() = com.suwiki.core.model.OpenMajor(
    id = id,
    name = name,
)

fun com.suwiki.core.model.OpenMajor.toEntity() = OpenMajorEntity(name).also {
    it.id = id
}
