package com.example.account_book.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Record::class],  version = 1)
abstract class RecordDatabase:RoomDatabase() {
    abstract fun Dao():RecordDAO
    companion object{
        private var INSTANCE:RecordDatabase? = null
        fun getDatabase(context: Context):RecordDatabase?{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDatabase::class.java,
                    "record_database")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}