package com.example.account_book.accountItem

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.R
import com.example.account_book.databinding.DayRecordBinding
import java.text.SimpleDateFormat

class DayRecordAdapter(val recordList:MutableList<DayRecord>):RecyclerView.Adapter<DayRecordAdapter.Holder>() {
//    var recordList = mutableListOf<DayRecord>()
    companion object {
        val sdf = SimpleDateFormat("hh:mm")
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DayRecordBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setting(recordList[position])
    }

    override fun getItemCount() = recordList.size

    inner class Holder(val binding: DayRecordBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor")
        fun setting(record: DayRecord){
            binding.run{
                classification.text = record.classification
                detail.text = record.detail
                detailDate.text = changeTime(record.detailDate)
                detailBank.text = record.detailBank
                if(record.amount > 0){
                    amount.text = record.amount.toString()
                    amount.setTextColor(R.color.income)
                }
                else {
                    amount.text = (-record.amount).toString()
                    amount.setTextColor(R.color.expenditure)
                }
            }
        }
        fun changeTime(date:Long):String{
            val time = sdf.format(date)
            val hour = time.split(":")[0].toInt()
            val minute = time.split(":")[1]
            var res = ""
            res = when (hour) {
                in 0..11 -> "오전 $time"
                12 -> "오후 $time"
                in 13..23 -> "오후 ${hour-12} : $minute"
                else -> {
                    Log.e("ERROR", "DayRecordAdapter :: changeTime() called Error (return else)")
                    "error"
                }
            }
            return res
        }

    }
}
