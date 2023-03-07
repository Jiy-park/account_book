package com.example.account_book.main_menu

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.account_book.account_Item.AccountMainViewModel
import com.example.account_book.account_Item.AccountDateAdapter
import com.example.account_book.AddNewRecordActivity
import com.example.account_book.databinding.FragmentAccountBinding
import com.example.account_book.room.DateEntity
import com.example.account_book.room.DB
import com.example.account_book.room.DetailEntity

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var accountViewModel: AccountMainViewModel
    private lateinit var dateList: MutableList<DateEntity>
    private lateinit var db:DB
    private lateinit var dateAdapter:AccountDateAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        db = DB.getDatabase(binding.root.context)!!
        dateAdapter = AccountDateAdapter(this@AccountFragment)
        accountViewModel = ViewModelProvider(this@AccountFragment)[AccountMainViewModel::class.java]
        accountViewModel.selectedMonth.observe(viewLifecycleOwner, Observer {
            val yearMonth = (if(accountViewModel.selectedMonth.value!! < 10) "${accountViewModel.selectedYear.value!!}0${accountViewModel.selectedMonth.value!!}"
                                else "${accountViewModel.selectedYear.value!!}0${accountViewModel.selectedMonth.value!!}").substring(2).toInt()
            dateList = db.dateDao().getAllDateByYearMonthID(yearMonth)
            dateAdapter.dateList = dateList
            dateAdapter.notifyDataSetChanged()
        })

        binding.viewModel = accountViewModel
        binding.lifecycleOwner = this@AccountFragment
        binding.recyclerView.adapter = dateAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.VERTICAL,false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTest.setOnClickListener {
            val dateId = SimpleDateFormat("yy-MM-dd")
                .format(System.currentTimeMillis())
                .replace("-","")
                .toInt()
            val orderId = db.detailDao().getLastOrderCountByDateID(dateId)+1
            val yearMonthId = dateId/100
            val dayId = dateId%100
            val week = SimpleDateFormat("E요일").format(System.currentTimeMillis())
            db.dateDao().insertDate(DateEntity(
                yearMonthId,
                dayId,
                week
            ))
            db.detailDao().insertDetail(DetailEntity(
                dateId,
                orderId,
                "testClass",
                "testDetail $dateId $orderId",
                "test:time",
                "testBank",
                12345
            ))

            val yearMonth = (if(accountViewModel.selectedMonth.value!! < 10) "${accountViewModel.selectedYear.value!!}0${accountViewModel.selectedMonth.value!!}"
            else "${accountViewModel.selectedYear.value!!}0${accountViewModel.selectedMonth.value!!}").substring(2).toInt()
            dateList = db.dateDao().getAllDateByYearMonthID(yearMonth)
            dateAdapter.dateList = dateList
            dateAdapter.notifyDataSetChanged()
        }
        binding.btnAddRecord.setOnClickListener {
            val intent = Intent(activity, AddNewRecordActivity::class.java)
            Log.d("LOG_CHECK", "1")
            startActivity(intent)
            Log.d("LOG_CHECK", "2")
        }
    }

}
