package com.suwiki.openmajor.converter

import com.suwiki.database.model.OpenMajorEntity
import com.suwiki.model.OpenMajor

fun OpenMajorEntity.toModel() = OpenMajor(
    id = id,
    name = name,
)

fun OpenMajor.toEntity() = OpenMajorEntity(name).also {
    it.id = id
}
