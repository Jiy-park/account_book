package com.example.account_book.accountItem

data class DayRecord(
    var classification:String,
    var detail:String,
    var detailDate:Long,
    var detailBank:String,
    var amount:Int
)
