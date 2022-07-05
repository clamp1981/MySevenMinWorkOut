package com.example.mysevenminworkout

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {

    @Insert
    suspend fun insert( historyEntity: HistoryEntity )

    @Update
    suspend fun update( historyEntity: HistoryEntity )


    @Query( "SELECT * FROM 'history-table'")
    fun fetchAllHistories(): Flow<List<HistoryEntity>>

    @Query( "DELETE FROM 'history-table'")
    suspend fun deleteAllHistories()
}