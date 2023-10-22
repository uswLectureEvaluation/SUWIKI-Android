package com.suwiki.data.db.entity.converter

import com.suwiki.data.db.entity.OpenMajorEntity
import com.suwiki.domain.model.OpenMajor

fun OpenMajorEntity.toDomain(): OpenMajor {
  return OpenMajor(
    id = id,
    name = name,
  )
}

fun OpenMajor.toEntity(): OpenMajorEntity {
  return OpenMajorEntity(name).also {
    it.id = id
  }
}
