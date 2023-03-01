package com.example.account_book

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.account_book.databinding.ActivityMainBinding
import com.example.account_book.main_menu.AccountFragment
import com.example.account_book.main_menu.ChartFragment
import com.example.account_book.main_menu.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main, AccountFragment())
            .commit()

        // 하단 네비게이션 바 클릭 이벤트 설정
        binding.bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.tabAccount -> {
                    replaceFragment(AccountFragment())
                    true
                }
                R.id.tabChart -> {
                    replaceFragment(ChartFragment())
                    true
                }
                R.id.tabSetting -> {
                    replaceFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
    }

    // 화면 전환 구현 메소드
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main, fragment)
            .commit()
    }
}