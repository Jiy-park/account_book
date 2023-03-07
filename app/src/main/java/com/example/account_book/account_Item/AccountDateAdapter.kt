package com.example.account_book.account_Item

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.databinding.AccountMonthRecordBinding
import com.example.account_book.main_menu.AccountFragment
import com.example.account_book.room.DB
import com.example.account_book.room.DateEntity
import com.example.account_book.room.DetailEntity

class AccountDateAdapter(val fragment: AccountFragment):RecyclerView.Adapter<AccountDateAdapter.Holder>() {
    var dateList = mutableListOf<DateEntity>()
    private lateinit var db:DB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AccountMonthRecordBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        db = DB.getDatabase(binding.root.context)!!
        return Holder(binding)
    }
//230212
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val date = dateList[position].yearMonthID*100 + dateList[position].dayID
        val detailList = db.detailDao().getAllDetailByDateID(date)
        holder.setting(dateList[position], detailList)
    }

    override fun getItemCount() = dateList.size

    inner class Holder(private val binding:AccountMonthRecordBinding):RecyclerView.ViewHolder(binding.root){
        private val detailAdapter by lazy { AccountDetailAdapter(fragment) }
        @SuppressLint("SetTextI18n")
        fun setting(oneDate: DateEntity, detailList:MutableList<DetailEntity>){
            val day = if(oneDate.dayID < 10) "0${oneDate.dayID}"
                        else oneDate.dayID.toString()
            val year = oneDate.yearMonthID/100 + 2000
            val month = oneDate.yearMonthID%100
            val yearMonth = "${year}.$month"

            binding.day.text = day
            binding.week.text = oneDate.week
            binding.yearMonth.text = yearMonth
            detailAdapter.detailList = detailList
            detailAdapter.setItemClickListener(object :AccountDetailAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    Log.d("LOG_CHECK", "${detailList[position].dateID}")
                    db.detailDao().deleteByDateOrderIDs(detailList[position].dateID, detailList[position].orderID)
                    detailList.removeAt(position)
                    detailAdapter.notifyItemRemoved(position)
                    detailAdapter.notifyItemRangeChanged(position, detailList.size)
                    if(detailList.size == 0) { deleteDateOnList(oneDate) }
                }
            })
            binding.recyclerViewDayRecord.adapter = detailAdapter
            binding.recyclerViewDayRecord.layoutManager = LinearLayoutManager(fragment.context, LinearLayoutManager.VERTICAL,false)
        }

        fun deleteDateOnList(date:DateEntity){
            val position = dateList.indexOf(date)
            dateList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, dateList.size)
            db.dateDao().deleteDate(date)
        }
    }
}