package com.example.account_book.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DateEntity::class, DetailEntity::class],  version = 1)
abstract class DB:RoomDatabase() {
    abstract fun dateDao():DateDao
    abstract fun detailDao():DetailDao
    init{
        Log.d("LOG_CHECK", "DB :: () called")
    }
    companion object{
        private var INSTANCE:DB? = null
        fun getDatabase(context: Context):DB?{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "DB")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}