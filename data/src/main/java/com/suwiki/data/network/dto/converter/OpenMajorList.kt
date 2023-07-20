package com.suwiki.data.network.dto.converter

import com.suwiki.data.db.entity.OpenMajorEntity
import com.suwiki.data.network.dto.OpenMajorListDto

fun OpenMajorListDto.toEntityList(): List<OpenMajorEntity> {
    val tmp = mutableListOf<OpenMajorEntity>()
    data.forEach {
        tmp.add(OpenMajorEntity(it))
    }
    return tmp
}

fun OpenMajorListDto.toDomain(): List<String> {
    return this.data
}
