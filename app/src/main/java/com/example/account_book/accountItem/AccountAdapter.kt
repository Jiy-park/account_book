package com.example.account_book.accountItem

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.databinding.ItemDayBinding

class AccountAdapter(val activity: FragmentActivity?, val dayList:MutableList<Byte>, val recordList:MutableList<DayRecord>): RecyclerView.Adapter<AccountAdapter.Holder>() {
//    var dayList = mutableListOf<Byte>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemDayBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(activity, binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setting(dayList[position])
    }

    override fun getItemCount() = dayList.size

    inner class Holder(val activity:FragmentActivity?, val binding: ItemDayBinding): RecyclerView.ViewHolder(binding.root){
        fun setting(day:Byte){
            binding.day.text = day.toString()
            binding.recyclerViewDayRecord.adapter = DayRecordAdapter(recordList)
            binding.recyclerViewDayRecord.layoutManager = LinearLayoutManager(activity)
        }
    }
}

