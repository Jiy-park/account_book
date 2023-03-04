package com.example.account_book

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.account_book.databinding.ActivityAddNewRecordBinding
import com.example.account_book.room.DB
import com.example.account_book.room.DateEntity
import com.example.account_book.room.DetailEntity


class AddNewRecord : AppCompatActivity() {
    private val binding by lazy { ActivityAddNewRecordBinding.inflate(layoutInflater) }
    private val dialogDate by lazy { DatePickerDialog(this) }
    private lateinit var dialogTime: TimePickerDialog
    private lateinit var db: DB
    private var incomeMode = false // true -> 수입 false -> 지출
    private var year:Int = 0
    private var month:Int = 0
    private var day:Int = 0
    private var week:String = ""
    private var hour:Int = 0
    private var minute:Int = 0

    init {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)+1
        day = calendar.get(Calendar.DAY_OF_MONTH)
        week = changeWeek(calendar.get(Calendar.DAY_OF_WEEK))
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun settingDateTime(){
        binding.tvDate.text = "$year/$month/$day - $week"
        binding.tvTime.text = "$hour:$minute"
    }

    private fun initListener(){
        db = DB.getDatabase(binding.root.context)!!
        dialogDate.setOnDateSetListener { _, year, month, day ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            this.year = year
            this.month = month+1
            this.day = day
            this.week = changeWeek(calendar.get(Calendar.DAY_OF_WEEK))
            binding.tvDate.text = "${this.year}/${this.month}/${this.day} - ${this.week}"
        }
        dialogTime = TimePickerDialog(this@AddNewRecord,TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            this.hour = hour
            this.minute = minute
            binding.tvTime.text = "${this.hour}:${this.minute}"
        }, this.hour, this.minute, true)
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.radioIncome -> {
                    binding.tvInOut.text = getString(R.string.INCOME)
                    incomeMode = true
                }
                R.id.radioExpenditure -> {
                    binding.tvInOut.text = getString(R.string.EXPENDITURE)
                    incomeMode = false
                }
            }
        }
        binding.btnBack.setOnClickListener { finish() }
        binding.tvDate.setOnClickListener { dialogDate.show() }
        binding.tvTime.setOnClickListener { dialogTime.show() }
        binding.btnSave.setOnClickListener {
            val yearMonthID = ((year*100 + month) - 200000) //2023 / 3 / 5 ->  2303 / 5
            val dateID = yearMonthID*100+day
            val orderID = db.detailDao().getOrderCountByDate(dateID) //2303 / 5 -> 230305
            val time = "$hour:$minute"
            val amount = if(incomeMode) binding.editAmount.text.toString().toInt()
                        else -(binding.editAmount.text.toString().toInt())
            db.dateDao().insertDate(DateEntity(yearMonthID, day, week))
            db.detailDao().insertDetail(DetailEntity(
                dateID,
                orderID,
                binding.tvClass.text.toString(),
                binding.editDetail.text.toString(),
                time,
                binding.tvBank.text.toString(),
                amount
            ))
            Toast.makeText(binding.root.context, "기록이 저장되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun changeWeek(week:Int):String{
        return when(week) {
            1 -> "일요일"
            2 -> "월요일"
            3 -> "화요일"
            4 -> "수요일"
            5 -> "목요일"
            6 -> "금요일"
            7 -> "토요일"
            else -> "Error week is $week"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        settingDateTime()
        initListener()
    }

}


