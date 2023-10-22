package com.suwiki.data.network.dto.converter

import com.suwiki.data.network.dto.OpenMajorVersionDto
import com.suwiki.domain.model.OpenMajorVersion

fun OpenMajorVersionDto.toDomain(): OpenMajorVersion {
  return OpenMajorVersion(version)
}
