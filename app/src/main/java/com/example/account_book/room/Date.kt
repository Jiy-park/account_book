package com.example.account_book.room
import androidx.room.*

@Entity(tableName = "dateDB")
data class DateEntity(
    @PrimaryKey
    val ID:Int,

    @ColumnInfo(name = "week")
    val week:String //날짜 - 요일
)

@Dao
interface DateDao {
    @Query("SELECT * FROM dateDB")
    fun getAllDate() : List<DateEntity>



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDate(record: DateEntity)

    @Update
    fun updateDate(record: DateEntity)

    @Delete
    fun deleteDate(record: DateEntity)
}

