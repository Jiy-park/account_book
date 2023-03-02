package com.example.account_book.account_Item

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.databinding.AccountDayRecordBinding
import com.example.account_book.main_menu.AccountFragment
import com.example.account_book.room.DB
import com.example.account_book.room.DetailEntity

class AccountDayAdapter(val fragment: AccountFragment):RecyclerView.Adapter<AccountDayAdapter.Holder>() {
    var dayList = mutableListOf<DetailEntity>()
    private lateinit var db: DB
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) { this.itemClickListener = itemClickListener }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.d("LOG_CHECK", "AccountDayAdapter :: onCreateViewHolder() called")
        val binding = AccountDayRecordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        db = DB.getDatabase(binding.root.context)!!
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
        holder.setting(dayList[position])
    }

    override fun getItemCount() = dayList.size


    inner class Holder(val binding:AccountDayRecordBinding):RecyclerView.ViewHolder(binding.root){
        fun setting(day: DetailEntity){
            Log.d("LOG_CHECK", "Holder :: setting() called")
            binding.run {
                classification.text = day.classification
                detail.text = day.detail
                detailDate.text = day.time
                detailBank.text = day.bank
                amount.text = if(day.amount > 0) { day.amount.toString() }
                            else { (-day.amount).toString() }
            }
        }
    }
}