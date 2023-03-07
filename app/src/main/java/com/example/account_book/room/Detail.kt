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
    @Query("SELECT * FROM detailDB ORDER BY dateID, orderID DESC")
    fun getAllDetail():MutableList<DetailEntity>

    @Query("SELECT * FROM detailDB WHERE dateID = :date ORDER BY dateID, orderID DESC")
    fun getAllDetailByDateID(date:Int):MutableList<DetailEntity>

    @Query("SELECT MAX(orderID) FROM  detailDB GROUP BY :date")
    fun getLastOrderCountByDateID(date:Int):Int
//    select gender, max (point), min (point) from user group by gender;

    @Insert()
    fun insertDetail(record: DetailEntity)


    @Update
    fun updateDetail(record: DetailEntity)

    @Delete
    fun deleteDetail(record: DetailEntity)

    @Query("DELETE FROM detailDB WHERE (dateID = :date AND orderID = :order)")
    fun deleteByDateOrderIDs(date:Int, order: Int)
}

