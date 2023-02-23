package com.example.account_book.accountItem

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.account_book.R
import java.util.Calendar

class AccountViewModel():ViewModel() {
    private val _selectedYear = MutableLiveData<String>()
    private val _selectedMonth = MutableLiveData<String>()

    val selectedYear:LiveData<String>
        get() = _selectedYear
    val selectedMonth:LiveData<String>
        get() = _selectedMonth

    init {
        val date = Calendar.getInstance()
        _selectedYear.value = date.get(Calendar.YEAR).toString() + " 년 "
        _selectedMonth.value = date.get(Calendar.MONTH).toString() + " 월"
    }

    fun setYear(year:Int){ // 년도를 인풋값으로 변경
        _selectedYear.value = year.toString() + "년 "
    }
    fun setMonth(month:Number){ //월을 인풋값으로 변경
        _selectedMonth.value = month.toString() + "월"
    }

    fun changeMonth(view: View){ //좌우 버튼을 통해 년/월 변경
        val currentMonth = _selectedMonth.value!!.split(" ")[0].toInt()
        when(view.id){
            R.id.btnNextMonth -> {
                if(currentMonth == 12) {
                    _selectedYear.value = (_selectedYear.value!!.split(" ")[0].toInt() + 1).toString() + " 년 "
                    _selectedMonth.value = (currentMonth - 11).toString() + " 월"
                }
                else { _selectedMonth.value = (currentMonth + 1).toString() + " 월" }
            }
            R.id.btnPrevMonth -> {
                if(currentMonth == 1) {
                    _selectedYear.value = (_selectedYear.value!!.split(" ")[0].toInt() - 1).toString() + " 년 "
                    _selectedMonth.value = (currentMonth + 11).toString() + " 월"
                }
                else { _selectedMonth.value = (currentMonth - 1).toString() + " 월" }
            }
        }
    }
}