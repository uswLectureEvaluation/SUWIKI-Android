package com.suwiki.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.suwiki.core.database.model.TimetableEntity

@Dao
interface TimeTableDao {
  @Query("SELECT * FROM TimetableEntity")
  fun getAll(): List<TimetableEntity>

  @Query("SELECT * FROM TimetableEntity WHERE createTime = :createTime")
  fun get(createTime: Long): TimetableEntity?

  @Insert
  fun insert(data: TimetableEntity)

  @Query("DELETE FROM TimetableEntity")
  fun deleteAll()

  @Delete
  fun delete(data: TimetableEntity)

  @Update
  fun update(data: TimetableEntity)
}
