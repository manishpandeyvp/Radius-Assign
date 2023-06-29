package com.lemonsqueeze.radiusassign.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lemonsqueeze.radiusassign.data.dto.RemoteDataDto

@Dao
interface AssignDao {
    @Insert
    fun insertData(data: RemoteDataDto)

    @Delete
    fun deleteData(data: RemoteDataDto)

    @Query("SELECT * FROM ${RemoteDataDto.TABLE_NAME}")
    suspend fun getAllData(): RemoteDataDto?
}