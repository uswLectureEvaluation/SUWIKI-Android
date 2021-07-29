package com.kunize.uswtimetable.model

import androidx.room.*

@Dao
interface TimeTableListDao {
    @Query("SELECT * FROM TimeTableList")
    fun getAll(): List<TimeTableList>

    @Query("SELECT * FROM TimeTableList WHERE createTime = :createTime")
    fun getOne(createTime: Long) : TimeTableList

    @Insert
    fun insert(data: TimeTableList)

    @Query("DELETE FROM TimeTableList")
    fun deleteAll()

    @Delete
    fun delete(data: TimeTableList)

    @Update
    fun update(data: TimeTableList)
}