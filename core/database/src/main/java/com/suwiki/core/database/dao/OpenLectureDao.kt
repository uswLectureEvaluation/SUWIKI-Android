package com.suwiki.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.suwiki.core.database.model.OpenLectureEntity

@Dao
interface OpenLectureDao {
  @Query("SELECT * FROM OpenLectureEntity")
  fun getAll(): List<OpenLectureEntity>

  @Insert
  fun insert(data: OpenLectureEntity)

  @Query("DELETE FROM OpenLectureEntity")
  fun deleteAll()

  @Delete
  fun delete(data: OpenLectureEntity)

  @Update
  fun update(data: OpenLectureEntity)
}
