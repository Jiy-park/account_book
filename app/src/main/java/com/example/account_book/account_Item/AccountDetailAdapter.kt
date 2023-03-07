package com.example.account_book.account_Item

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.R
import com.example.account_book.databinding.AccountDayRecordBinding
import com.example.account_book.main_menu.AccountFragment
import com.example.account_book.room.DB
import com.example.account_book.room.DetailEntity

class AccountDetailAdapter(val fragment: AccountFragment):RecyclerView.Adapter<AccountDetailAdapter.Holder>() {
    var detailList = mutableListOf<DetailEntity>()
    private lateinit var db: DB
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) { this.itemClickListener = itemClickListener }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AccountDayRecordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        db = DB.getDatabase(binding.root.context)!!
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
        holder.setting(detailList[position])
    }

    override fun getItemCount() = detailList.size


    inner class Holder(private val binding:AccountDayRecordBinding):RecyclerView.ViewHolder(binding.root){
        fun setting(day: DetailEntity){
            binding.run {
                classification.text = day.classification
                detail.text = day.detail
                detailDate.text = day.time
                detailBank.text = day.bank
                if(day.amount > 0) {
                    amount.text = day.amount.toString()
                    amount.setTextColor(ContextCompat.getColor(fragment.requireContext(), R.color.income))
                }
                else {
                    amount.text = (-day.amount).toString()
                    amount.setTextColor(ContextCompat.getColor(fragment.requireContext(), R.color.expenditure))
                }
            }
        }
    }
}