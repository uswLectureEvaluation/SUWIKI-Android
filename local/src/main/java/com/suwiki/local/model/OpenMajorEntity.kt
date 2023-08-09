package com.suwiki.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.suwiki.model.OpenMajor

@Entity
data class OpenMajorEntity(
    val name: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun OpenMajorEntity.toModel(): OpenMajor {
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
