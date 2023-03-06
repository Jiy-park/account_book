package com.example.account_book.room
import androidx.room.*

@Entity(tableName = "dateDB", primaryKeys = ["yearMonthID", "dayID"])
data class DateEntity(
    val yearMonthID:Int,
    val dayID:Int,

    @ColumnInfo(name = "week")
    val week:String //날짜 - 요일
)

@Dao
interface DateDao {
    @Query("SELECT * FROM dateDB ORDER BY dayID DESC")
    fun getAllDate() : MutableList<DateEntity>

    @Query("SELECT * FROM dateDB WHERE yearMonthID = :yearMonth ORDER BY dayID DESC")
    fun getAllDateByYearMonthID(yearMonth: Int) : MutableList<DateEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDate(record: DateEntity)

    @Update
    fun updateDate(record: DateEntity)

    @Delete
    fun deleteDate(record: DateEntity)
}

