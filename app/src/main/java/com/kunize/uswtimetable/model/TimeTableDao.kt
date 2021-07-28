package com.kunize.uswtimetable.model

import androidx.room.*

@Dao
interface TimeTableDao {
    @Query("SELECT * FROM TimeTableData")
    fun getAll(): List<TimeTableData>

    @Insert
    fun insert(data: TimeTableData)

    @Query("DELETE FROM TimeTableData")
    fun deleteAll()

    @Delete
    fun delete(data: TimeTableData)

    @Update
    fun update(data: TimeTableData)
}

