package com.example.account_book.account_Item

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val dayList = db.detailDao().getAllDetailByDateID(date)
        Log.d("LOG_CHECK", "$date $dayList")
        holder.setting(dateList[position], dayList)
    }

    override fun getItemCount() = dateList.size


    inner class Holder(private val binding:AccountMonthRecordBinding):RecyclerView.ViewHolder(binding.root){
        private val dayAdapter by lazy { AccountDetailAdapter(fragment) }

        @SuppressLint("SetTextI18n")
        fun setting(oneDate: DateEntity, dayList:MutableList<DetailEntity>){
            val day = if(oneDate.dayID < 10) "0${oneDate.dayID}"
                        else oneDate.dayID.toString()
            val year = oneDate.yearMonthID/100 + 2000
            val month = oneDate.yearMonthID%100
            val yearMonth = "${year}.$month"
            binding.run {
                this.day.text = day
                this.week.text = oneDate.week
                this.yearMonth.text = yearMonth
                dayAdapter.detailList = dayList
                dayAdapter.setItemClickListener(object :AccountDetailAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        Log.d("LOG_CHECK", "${dayList[position].dateID} ${dayList[position].orderID}")//?????? ?????? ?????????????????? ????????????????????? ???????????? ???????????? ??????????????? ?????????
                        db.detailDao().deleteByDateOrderIDs(dayList[position].dateID, dayList[position].orderID)
                        dayAdapter.notifyDataSetChanged()
                    }
                })
                this.recyclerViewDayRecord.adapter = dayAdapter
                this.recyclerViewDayRecord.layoutManager = LinearLayoutManager(fragment.context, LinearLayoutManager.VERTICAL,false)
            }
        }
    }
}