package com.suwiki.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.suwiki.core.database.model.OpenMajorEntity

@Dao
interface OpenMajorDao {
    @Insert
    fun insertAll(lists: List<OpenMajorEntity>)

    @Query("DELETE FROM OpenMajorEntity")
    fun deleteAll()

    @Query("SELECT * FROM OpenMajorEntity")
    fun getAll(): List<OpenMajorEntity>
}