package com.example.account_book.account_Item

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.account_book.databinding.AccountMonthRecordBinding
import com.example.account_book.main_menu.AccountFragment
import com.example.account_book.room.DB
import com.example.account_book.room.DateEntity
import com.example.account_book.room.DetailEntity

class AccountMonthAdapter(val fragment: AccountFragment):RecyclerView.Adapter<AccountMonthAdapter.Holder>() {
    var dateList = listOf<DateEntity>()
    lateinit var db:DB
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AccountMonthRecordBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        db = DB.getDatabase(binding.root.context)!!
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dayList = db.detailDao().getAllDetailByDate(dateList[position].ID)
        val test = db.detailDao().getAllDetail()
        Log.d("LOG_CHECK", "${dateList[position].ID}  $test")
        holder.setting(dateList[position], dayList)
    }

    override fun getItemCount() = dateList.size


    inner class Holder(private val binding:AccountMonthRecordBinding):RecyclerView.ViewHolder(binding.root){
        private var dayAdapter = AccountDayAdapter(fragment)

        @SuppressLint("SetTextI18n")
        fun setting(record: DateEntity, dayList:List<DetailEntity>){
            val id = record.ID.toString() //230212
            val day = id.substring(4) //12
            val year = "20${id.substring(0, 2)}"
            val month = id.substring(2,4)
            binding.run {
                this.day.text = day
                this.week.text = record.week
                this.yearMonth.text = "$year.$month"
                dayAdapter.dayList = dayList
                this.recyclerViewDayRecord.adapter = dayAdapter
                this.recyclerViewDayRecord.layoutManager = LinearLayoutManager(fragment.context, LinearLayoutManager.VERTICAL,false)
            }
        }
    }
}