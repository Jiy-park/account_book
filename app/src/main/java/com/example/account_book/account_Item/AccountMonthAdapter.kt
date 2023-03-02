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

class AccountMonthAdapter(val fragment: AccountFragment):RecyclerView.Adapter<AccountMonthAdapter.Holder>() {
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
        val dayList = db.detailDao().getAllDetailByDate(date)
        Log.d("LOG_CHECK", "$date")
        holder.setting(dateList[position], dayList)
    }

    override fun getItemCount() = dateList.size


    inner class Holder(private val binding:AccountMonthRecordBinding):RecyclerView.ViewHolder(binding.root){
        private val dayAdapter by lazy { AccountDayAdapter(fragment) }

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
                dayAdapter.dayList = dayList
                dayAdapter.setItemClickListener(object :AccountDayAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        Log.d("LOG_CHECK", "${dayList[position].dateID} ${dayList[position].orderID}")//이거 너무 하드코딩이다 라이브데이터로 변경하고 뷰모델로 관리하는게 나을듯
                        db.detailDao().deleteByDateOrder(dayList[position].dateID, dayList[position].orderID)
                        dayAdapter.notifyDataSetChanged()
                    }
                })
                this.recyclerViewDayRecord.adapter = dayAdapter
                this.recyclerViewDayRecord.layoutManager = LinearLayoutManager(fragment.context, LinearLayoutManager.VERTICAL,false)
            }
        }
    }
}