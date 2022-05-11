package com.kunize.uswtimetable.data.local

import androidx.room.*
import com.kunize.uswtimetable.data.local.TimeTableData

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

