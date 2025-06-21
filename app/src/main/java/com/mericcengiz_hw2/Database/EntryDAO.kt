package com.mericcengiz_hw2.Database

import androidx.room.*
import com.mericcengiz_hw2.Database.Entry
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: Entry)

    @Update
    fun update(entry: Entry)

    @Delete
    fun delete(entry: Entry)

    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAllEntries(): List<Entry>

    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAllOnce(): List<Entry>

    @Query("SELECT * FROM entries WHERE id = :id")
    fun getById(id: Int): Entry

}
