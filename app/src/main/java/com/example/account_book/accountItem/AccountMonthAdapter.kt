package com.example.account_book.accountItem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.databinding.AccountMonthRecordBinding
import com.example.account_book.room.Record

class AccountMonthAdapter(recordList:List<Record>):RecyclerView.Adapter<AccountMonthAdapter.Holder>() {
    private var _recordList = recordList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AccountMonthRecordBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setting(_recordList[position])
    }

    override fun getItemCount() = _recordList.size


    inner class Holder(private val binding:AccountMonthRecordBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun setting(record: Record){
            binding.run {
                day.text = record.detailDateDay
                week.text = record.detailDateWeek
                yearMonth.text = record.detailDateYear + "." + record.detailDateMonth
            }
        }
    }
}