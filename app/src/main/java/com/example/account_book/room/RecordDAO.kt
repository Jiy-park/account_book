package com.example.account_book.room

import androidx.room.*

@Dao
interface RecordDAO {
    @Query("SELECT * FROM record")
    fun getAll():List<Record>

    @Query("SELECT * FROM record WHERE detailDateMonth = :month")
    fun getAllByMonth(month:String):List<Record>

    @Query("SELECT * FROM record WHERE detailDateDay = :day")
    fun getAllByDay(day:String):List<Record>

    @Insert
    fun insertRecord(record: Record)

    @Update
    fun updateRecord(record: Record)

    @Delete
    fun deleteRecord(record: Record)
}