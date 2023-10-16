package com.suwiki.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.suwiki.database.model.OpenMajorEntity

@Dao
interface OpenMajorDao {
    @Insert
    fun insertAll(lists: List<OpenMajorEntity>)

    @Query("DELETE FROM OpenMajorEntity")
    fun deleteAll()

    @Query("SELECT * FROM OpenMajorEntity")
    fun getAll(): List<OpenMajorEntity>
}