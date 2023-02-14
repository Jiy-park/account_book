package com.example.account_book.main_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.account_book.accountItem.AccountAdapter
import com.example.account_book.accountItem.DayRecord
import com.example.account_book.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
    lateinit var binding: FragmentAccountBinding
    val adapter by lazy { AccountAdapter(this.activity,dayList, recordList) }
    var dayList = mutableListOf<Byte>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25)
    var recordList = mutableListOf<DayRecord>(
        DayRecord("분류1","테스트입니다.",System.currentTimeMillis(),"지용뱅크",10_000),
        DayRecord("분류2","테스트입니다.",System.currentTimeMillis(),"지용뱅크",10_000),
        DayRecord("분류1","테스트입니다.",System.currentTimeMillis(),"지용뱅크",10_000),
        DayRecord("분류3","테스트입니다.",System.currentTimeMillis(),"지용뱅크",-10_000),
        DayRecord("분류1","테스트입니다.",System.currentTimeMillis(),"지용뱅크",10_000),
        DayRecord("분류1","테스트입니다.",System.currentTimeMillis(),"지용뱅크",-10_000)

    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.activity)
    }
}
