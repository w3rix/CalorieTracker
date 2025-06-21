package com.mericcengiz_hw2.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entry::class], version = 1, exportSchema = false)
abstract class EntryRoomDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDAO

    companion object {
        @Volatile
        private var INSTANCE: EntryRoomDatabase? = null

        fun getDatabase(context: Context): EntryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntryRoomDatabase::class.java,
                    "entry_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
