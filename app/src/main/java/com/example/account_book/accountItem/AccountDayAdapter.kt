package com.example.account_book.accountItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.databinding.AccountDayRecordBinding
import com.example.account_book.room.Record

class AccountDayAdapter(recordList:List<Record>):RecyclerView.Adapter<AccountDayAdapter.Holder>() {
    private var _recordList = recordList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AccountDayRecordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setting(_recordList[position])
    }

    override fun getItemCount() = _recordList.size

    inner class Holder(val binding:AccountDayRecordBinding):RecyclerView.ViewHolder(binding.root){
        fun setting(record: Record){
            binding.run {
                classification.text = record.classification
                detail.text = record.detail
                detailDate.text = record.detailDateTime
                detailBank.text = record.detailBank
                amount.text = if(record.amount > 0) { record.amount.toString() }
                            else { (-record.amount).toString() }
            }
        }
    }
}