package com.example.account_book.account_Item

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.account_book.R
import java.util.Calendar

class AccountMainViewModel():ViewModel() {
    private val _selectedYear = MutableLiveData<Int>()
    private val _selectedMonth = MutableLiveData<Int>()

    val selectedYear:MutableLiveData<Int>
        get() = _selectedYear
    val selectedMonth:MutableLiveData<Int>
        get() = _selectedMonth

    init {
        val date = Calendar.getInstance()
        _selectedYear.value = date.get(Calendar.YEAR)
        _selectedMonth.value = date.get(Calendar.MONTH)+1
    }

    fun changeMonth(view: View){ //좌우 버튼을 통해 년/월 변경
        when(view.id){
            R.id.btnNextMonth -> {
                if(_selectedMonth.value!! == 12) {
                    _selectedYear.value = _selectedYear.value!!.plus(1)
                    _selectedMonth.value = _selectedMonth.value!!.minus(11)
                }
                else { _selectedMonth.value = _selectedMonth.value!!.plus(1) }
            }
            R.id.btnPrevMonth -> {
                if(_selectedMonth.value!! == 1) {
                    _selectedYear.value = _selectedYear.value!!.minus(1)
                    _selectedMonth.value = _selectedMonth.value!!.plus(11)
                }
                else { _selectedMonth.value = _selectedMonth.value!!.minus(1) }
            }
        }
    }
}