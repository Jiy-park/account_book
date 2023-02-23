package com.example.account_book.main_menu

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.account_book.accountItem.AccountViewModel
import com.example.account_book.accountItem.AccountMonthAdapter
import com.example.account_book.databinding.FragmentAccountBinding
import com.example.account_book.room.Record
import com.example.account_book.room.RecordDatabase
import java.text.SimpleDateFormat

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private lateinit var accountViewModel: AccountViewModel
    lateinit var db:RecordDatabase
    lateinit var recordList: List<Record>
    lateinit var monthAdapter:AccountMonthAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        accountViewModel = ViewModelProvider(this@AccountFragment)[AccountViewModel::class.java]
        db = Room.databaseBuilder(binding.root.context,RecordDatabase::class.java,"recordDB").allowMainThreadQueries().build()
        recordList = db.Dao().getAll()
        monthAdapter = AccountMonthAdapter(recordList)

        binding.viewModel = accountViewModel
        binding.lifecycleOwner = this@AccountFragment
        binding.recyclerView.adapter = monthAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@AccountFragment.context,LinearLayoutManager.VERTICAL,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddRecord.setOnClickListener {
            Log.d("LOG_CHECK", "AccountFragment :: onViewCreated() called")
            val recordeID = recordList.size + 1
            val date = Calendar.getInstance()
            db.Dao().insertRecord(Record(recordeID,
                "테스트분류", "상세내용",
                date.get(Calendar.YEAR).toString(),
                (date.get(Calendar.MONTH)+1).toString(),
                date.get(Calendar.DAY_OF_MONTH).toString(),
                changeWeek(date.get(Calendar.DAY_OF_WEEK)),
                SimpleDateFormat("HH:mm").format(date.time.time),
                "지용뱅크", 10000)
            )
            binding.recyclerView.adapter!!.notifyDataSetChanged()
            Log.d("LOG_CHECK", "AccountFragment :: onViewCreated() called ${recordList.size}")
            Toast.makeText(binding.root.context, "success insert!", Toast.LENGTH_SHORT).show()
        }
        binding.btnTest.setOnClickListener {
            Log.d("LOG_CHECK", "${recordList[0]}")

        }
    }

    fun changeWeek(week:Int):String{
        var res:String = ""
        return when(week) {
            1 -> "일요일"
            2 -> "월요일"
            3 -> "화요일"
            4 -> "수요일"
            5 -> "목요일"
            6 -> "금요일"
            7 -> "토요일"
            else -> "Error"
        }
    }
}
