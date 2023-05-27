package com.example.tiki_taka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiki_taka.databinding.ActivityNewMatchingBinding

class NewMatchingActivity : AppCompatActivity() {
    lateinit var binding : ActivityNewMatchingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewMatchingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_matching)
        init()
    }
    private fun init() {
        binding.btnNewMatchingSubmit.setOnClickListener(){
            //submit누르면 유효성 매칭 정보 확인 후 매칭 다이얼로그 띄우기
        }
    }
}