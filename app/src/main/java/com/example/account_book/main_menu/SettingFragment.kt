package com.example.account_book.main_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.account_book.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    lateinit var binding:FragmentSettingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingBinding.inflate(inflater,container,false)
        return binding.root
    }
}