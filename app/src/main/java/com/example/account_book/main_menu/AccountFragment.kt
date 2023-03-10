package com.example.account_book.main_menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.account_book.account_Item.AccountMainViewModel
import com.example.account_book.account_Item.AccountDateAdapter
import com.example.account_book.AddNewRecord
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

//              ID  WEEK
    val DB_TEST_INPUT_DATA_DATE = listOf<DateEntity>(
        DateEntity(2302, 12, "일요일"),
        DateEntity(2302, 13, "월요일"),
        DateEntity(2302, 14, "화요일"),
        DateEntity(2302, 16, "목요일"),
        DateEntity(2302, 20, "월요일"),
        DateEntity(2302, 21, "화요일"),
        DateEntity(2302, 22, "수요일"),
        DateEntity(2302, 24, "금요일"),
        DateEntity(2303, 1, "수요일"),
        DateEntity(2303, 4, "토요일"),
        DateEntity(2303, 5, "일요일"),
        DateEntity(2303, 6, "월요일"),
        DateEntity(2303, 9, "목요일"),
    )

//          DATE_ID ORDER_ID CLASSIFICATION DETAIL TIME BANK AMOUNT
    val DB_TEST_INPUT_DATA_DETAIL = listOf<DetailEntity>(
        DetailEntity(230212, 1, "테스트1", "ㅈㅈ", "12:33", "지용", 10000),
        DetailEntity(230212, 2, "테스트1", "ㅈㅈ", "15:12", "지용", 10000),
        DetailEntity(230213, 1, "테스트3", "ㅈㅈ", "11:11", "지용", 10000),
        DetailEntity(230213, 2, "테스트2", "ㅈㅈ", "13:39", "지용", 10000),
        DetailEntity(230213, 3, "테스트2", "ㅈㅈ", "20:15", "지용", 10000),
        DetailEntity(230213, 4, "테스트3", "ㅈㅈ", "20:01", "지용", 10000),
        DetailEntity(230214, 1, "테스트3", "ㅈㅈ", "09:11", "지용", 10000),
        DetailEntity(230214, 2, "테스트3", "ㅈㅈ", "15:51", "지용", 10000),
        DetailEntity(230216, 1, "테스트1", "ㅈㅈ", "13:24", "지용", 10000),
        DetailEntity(230216, 2, "테스트1", "ㅈㅈ", "15:51", "지용", 10000),
        DetailEntity(230220, 1, "테스트4", "ㅈㅈ", "13:37", "지용", 10000),
        DetailEntity(230221, 1, "테스트5", "ㅈㅈ", "15:49", "지용", 10000),
        DetailEntity(230221, 2, "테스트6", "ㅈㅈ", "19:22", "지용", 10000),
        DetailEntity(230222, 1, "테스트1", "ㅈㅈ", "20:40", "지용", 10000),
        DetailEntity(230224, 1, "테스트2", "ㅈㅈ", "10:05", "지용", 10000),
        DetailEntity(230224, 2, "테스트3", "ㅈㅈ", "13:14", "지용", 10000),
        DetailEntity(230224, 3, "테스트4", "ㅈㅈ", "15:51", "지용", 10000),
        DetailEntity(230224, 4, "테스트1", "ㅈㅈ", "20:27", "지용", 10000)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        db = DB.getDatabase(binding.root.context)!!
        dateAdapter = AccountDateAdapter(this@AccountFragment)
        accountViewModel = ViewModelProvider(this@AccountFragment)[AccountMainViewModel::class.java]
        accountViewModel.selectedMonth.observe(viewLifecycleOwner, Observer {
            Log.d("LOG_CHECK", "AccountFragment :: onCreateView() called1111111111111111111111111111")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTest.setOnClickListener {
//            for(record in DB_TEST_INPUT_DATA_DATE){
//                db.dateDao().insertDate(record)
//            }
//            for(record in DB_TEST_INPUT_DATA_DETAIL){
//                db.detailDao().insertDetail(record)
//            }
            val test = db.dateDao().getAllDate()
            Log.d("LOG_CHECK", "date: $test")
        }
        binding.btnAddRecord.setOnClickListener {
            val intent = Intent(activity, AddNewRecord::class.java)
            startActivity(intent)
        }
    }

    fun changeWeek(week:Int):String{
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
}
