package com.example.account_book.room
import androidx.room.*

@Entity(tableName = "detailDB", primaryKeys  = ["dateID", "orderID"])
data class DetailEntity(
    val dateID:Int,
    val orderID:Int,

    @ColumnInfo(name = "classification")
    val classification:String, // 분류

    @ColumnInfo(name = "detail")
    val detail:String, // 설명

    @ColumnInfo(name = "time")
    val time:String, //시간

    @ColumnInfo(name = "bank")
    val bank:String, //은행

    @ColumnInfo(name = "amount")
    val amount:Int //금액
)

@Dao
interface DetailDao{
    @Query("SELECT * FROM detailDB")
    fun getAllDetail():List<DetailEntity>
    @Query("SELECT * FROM detailDB WHERE dateID = :date")
    fun getAllDetailByDate(date:Int):List<DetailEntity>

    @Insert
    fun insertDetail(record: DetailEntity)

    @Update
    fun updateDetail(record: DetailEntity)

    @Delete
    fun deleteDetail(record: DetailEntity)
}

