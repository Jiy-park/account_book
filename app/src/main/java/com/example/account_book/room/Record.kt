package com.example.account_book.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//DayRecord("분류1","테스트입니다.",System.currentTimeMillis(),"지용뱅크",10_000),

@Entity(tableName = "record")
data class Record(
    @PrimaryKey(autoGenerate = true)
    val recodeID:Int?,

    @ColumnInfo(name = "classification")
    val classification:String, // 분류

    @ColumnInfo(name = "detail")
    val detail:String?, // 설명

    @ColumnInfo(name = "detailDateYear")
    val detailDateYear:String, //날짜 - 년

    @ColumnInfo(name = "detailDateMonth")
    val detailDateMonth:String, //날짜 - 월

    @ColumnInfo(name = "detailDateDay")
    val detailDateDay:String, //날짜 - 일

    @ColumnInfo(name = "detailDateWeek")
    val detailDateWeek:String, //날짜 - 요일

    @ColumnInfo(name = "detailDateTime")
    val detailDateTime:String, //시간

    @ColumnInfo(name = "detailBank")
    val detailBank:String?, //은행

    @ColumnInfo(name = "amount")
    val amount:Int //금액
)
