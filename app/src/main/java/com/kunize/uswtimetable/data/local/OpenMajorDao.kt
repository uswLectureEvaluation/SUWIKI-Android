package com.kunize.uswtimetable.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OpenMajorDao {
    @Insert
    fun insertAll(lists: List<OpenMajorData>)

    @Query("DELETE FROM OpenMajorData")
    fun deleteAll()

    @Query("SELECT * FROM OpenMajorData")
    fun getAll(): List<OpenMajorData>
}